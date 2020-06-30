package com.haiyu.manager.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haiyu.manager.common.utils.DateUtils;
import com.haiyu.manager.common.utils.NBIoTUtils;
import com.haiyu.manager.common.utils.TimeStamp;
import com.haiyu.manager.dao.BaseAdminOrderMapper;
import com.haiyu.manager.dao.BaseAdminSubscriptionMapper;
import com.haiyu.manager.dto.AdminOrderDTO;
import com.haiyu.manager.dto.AdminSubscriptionDTO;
import com.haiyu.manager.dto.OrderSearchDTO;
import com.haiyu.manager.dto.SubscriptionSearchDTO;
import com.haiyu.manager.nbpojo.QueryOrderListList;
import com.haiyu.manager.pojo.BaseAdminOrder;
import com.haiyu.manager.pojo.BaseAdminSubscription;
import com.haiyu.manager.pojo.OrderCreatePojo;
import com.haiyu.manager.response.PageDataResult;
import com.haiyu.manager.service.AdminOrderService;
import com.haiyu.manager.service.AdminSubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: AdminDeviceServiceImpl
 */
@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    @Autowired
    private BaseAdminOrderMapper baseAdminOrderMapper;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String APP_KEY = "OIUJOR846L4";
    private String APP_SECRET = "HJm58BlJgM";
    private String MasterKey = "c24ccca89ea74111a360f925c95d3af2";


    /**
     * 获取命令日志
     * @param orderSearchDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getOrderList(OrderSearchDTO orderSearchDTO, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        String deviceId = orderSearchDTO.getDeviceId();
        String productId = orderSearchDTO.getProductId();
        //向云端请求命令下发日志
        try{
            List<QueryOrderListList> resultList = NBIoTUtils.QueryOrderList(APP_KEY,APP_SECRET,deviceId,MasterKey,productId);
            //将数据保存到数据库中
            for (int i=0; i<resultList.size();i++) {
                BaseAdminOrder order = new BaseAdminOrder();
                QueryOrderListList list = resultList.get(i);
                //去重
                BaseAdminOrder obj = baseAdminOrderMapper.getOrderByCommandId(list.getCommandId());
                if (obj == null) {
                    order.setCommandId(list.getCommandId());
                    order.setDeviceId(list.getDeviceId());
                    order.setProductId(list.getProductId());
                    order.setImei(list.getImei());
                    order.setCommandStatus(list.getCommandStatus());
                    order.setCommandId(list.getCommandId());
                    order.setCreateTime(TimeStamp.stampToDate(list.getCreateTime()));
                    baseAdminOrderMapper.insert(order);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        List<AdminOrderDTO> baseAdminOrder = baseAdminOrderMapper.getOrderList(orderSearchDTO);
        PageHelper.startPage(pageNum, pageSize);
        if(baseAdminOrder.size() != 0){
            PageInfo<AdminOrderDTO> pageInfo = new PageInfo<>(baseAdminOrder);
            pageDataResult.setList(baseAdminOrder);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    @Override
    public Map<String, Object> cancelOrder(BaseAdminOrder order) {
        Map<String,Object> data = new HashMap();
        try {
            String deviceId = order.getDeviceId();
            String productId = order.getProductId();
            String commandId = order.getCommandId();
            //向云端发送数据
            String s = NBIoTUtils.CancelCommand(APP_KEY,APP_SECRET,deviceId,MasterKey,productId,commandId);
            if (s.equals("success")) {
                //更改数据库中数据
                int a = baseAdminOrderMapper.updateStatusByCommandId(commandId,"指令已取消");
                if (a != 0) {
                    data.put("code",1);
                    data.put("msg","指令取消成功！");
                    return data;
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        data.put("code",0);
        data.put("msg","出现异常！");
        return data;
    }

    @Override
    public Map<String, Object> createOrder(OrderCreatePojo order) {
        Map<String,Object> data = new HashMap();
        try {
            String deviceId = order.getDeviceId();
            String productId = order.getProductId();
            String orderInfo = order.getOrderInfo();
            String orderValue = order.getOrderValue();
            String serviceIdentifier = order.getServiceIdentifier();
            //判别服务标识
            if (!serviceIdentifier.equals("order")) {
                data.put("code",0);
                data.put("msg","服务出现问题，请稍后重试！");
            }
            //向云端发送数据
            String s = NBIoTUtils.CreateCommand(APP_KEY,APP_SECRET,deviceId,MasterKey,productId,orderInfo,orderValue);
            if (s.equals("success")) {
                data.put("code",1);
                data.put("msg","指令下发成功！");
                return data;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        data.put("code",0);
        data.put("msg","出现异常！");
        return data;
    }


}
