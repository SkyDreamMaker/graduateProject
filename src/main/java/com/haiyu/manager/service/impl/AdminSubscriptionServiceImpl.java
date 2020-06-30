package com.haiyu.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haiyu.manager.common.utils.DateUtils;
import com.haiyu.manager.common.utils.NBIoTUtils;
import com.haiyu.manager.dao.BaseAdminDeviceMapper;
import com.haiyu.manager.dao.BaseAdminProductMapper;
import com.haiyu.manager.dao.BaseAdminSubscriptionMapper;
import com.haiyu.manager.dto.AdminDeviceDTO;
import com.haiyu.manager.dto.AdminSubscriptionDTO;
import com.haiyu.manager.dto.DeviceSearchDTO;
import com.haiyu.manager.dto.SubscriptionSearchDTO;
import com.haiyu.manager.nbpojo.CreateDeviceResult;
import com.haiyu.manager.nbpojo.QueryProductResult;
import com.haiyu.manager.pojo.BaseAdminDevice;
import com.haiyu.manager.pojo.BaseAdminProduct;
import com.haiyu.manager.pojo.BaseAdminSubscription;
import com.haiyu.manager.pojo.BaseAdminUser;
import com.haiyu.manager.response.PageDataResult;
import com.haiyu.manager.service.AdminDeviceService;
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
public class AdminSubscriptionServiceImpl implements AdminSubscriptionService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String APP_KEY = "OIUJOR846L4";
    private String APP_SECRET = "HJm58BlJgM";
    private String MasterKey = "c24ccca89ea74111a360f925c95d3af2";
    @Autowired
    private BaseAdminSubscriptionMapper baseAdminSubscriptionMapper;


    @Override
    public PageDataResult getSubscriptionList(SubscriptionSearchDTO subscriptionSearch, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        List<AdminSubscriptionDTO> baseAdminSubscription = baseAdminSubscriptionMapper.getSubscriptionList(subscriptionSearch);
        PageHelper.startPage(pageNum, pageSize);
        if(baseAdminSubscription.size() != 0){
            PageInfo<AdminSubscriptionDTO> pageInfo = new PageInfo<>(baseAdminSubscription);
            pageDataResult.setList(baseAdminSubscription);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    @Override
    public Map<String, Object> deleteSubscription(Integer id) {
        Map<String, Object> data = new HashMap<>();
        try {
            //根据id获取要删除的订阅信息
            BaseAdminSubscription subscription = baseAdminSubscriptionMapper.getSubscriptionById(id);
            String subId = subscription.getSubId();
            String deviceId = subscription.getDeviceId();
            String productId = subscription.getProductId();
            String subLevelFormat = subscription.getSubLevel();
            String subLevel = "";
            if (subLevelFormat.equals("设备级")) {
                subLevel = "2";
            }else if (subLevelFormat.equals("产品级")) {
                subLevel = "1";
            }
            logger.info("开始删除云端订阅");
            logger.info("即将删除的云端订阅为"+subscription);
            //删除云端订阅
            String result = NBIoTUtils.DeleteSubscription(APP_KEY,APP_SECRET,deviceId,MasterKey,productId,subLevel,subId);
            if(result.equals("false")){
                data.put("code",0);
                data.put("msg","删除订阅失败");
                logger.error("删除订阅失败");
                return data;
            }
            data.put("code",1);
            data.put("msg","删除订阅成功");
            logger.info("删除订阅成功");
            //删除数据库订阅
            baseAdminSubscriptionMapper.deleteSubscription(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除订阅异常！", e);
        }
        return data;
    }

    @Override
    public Map<String, Object> createSubscription(BaseAdminSubscription subscription) {
        Map<String,Object> data = new HashMap();
        try {
            String deviceId = subscription.getDeviceId();
            String productId = subscription.getProductId();
            String subLevel = subscription.getSubLevel();
            String subUrl = subscription.getSubUrl();
            int[] subTypes = {Integer.valueOf(subscription.getSubType())};
            //向云端申请创建订阅
            String result = NBIoTUtils.CreateSubscription(APP_KEY,APP_SECRET,deviceId,MasterKey,productId,subLevel,subUrl,subTypes);
            if(result.equals("false")){
                data.put("code",0);
                data.put("msg","新增失败！");
                logger.error("订阅[新增]，结果=新增失败！");
                return data;
            }
            data.put("code",1);
            data.put("msg","新增成功！");
            logger.info("订阅[新增]，结果=新增成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("订阅[新增]异常！", e);
            return data;
        }
        return data;
    }

}
