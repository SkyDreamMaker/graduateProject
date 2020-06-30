package com.haiyu.manager.controller.system;

import com.haiyu.manager.common.utils.NBIoTUtils;
import com.haiyu.manager.dao.BaseAdminSubscriptionMapper;
import com.haiyu.manager.dto.DataSearchDTO;
import com.haiyu.manager.dto.DeviceSearchDTO;
import com.haiyu.manager.dto.SubscriptionSearchDTO;
import com.haiyu.manager.nbpojo.QuerySubscriptionList;
import com.haiyu.manager.pojo.BaseAdminDevice;
import com.haiyu.manager.pojo.BaseAdminSubscription;
import com.haiyu.manager.pojo.BaseAdminUser;
import com.haiyu.manager.response.PageDataResult;
import com.haiyu.manager.service.AdminDeviceService;
import com.haiyu.manager.service.AdminSubscriptionService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("subscription")
public class SubscriptionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminSubscriptionService adminSubscriptionService;
    @Autowired
    private BaseAdminSubscriptionMapper baseAdminSubscriptionMapper;
    /**
     *
     * 功能描述: 跳到订阅管理列表
     */
    @RequestMapping("/subscriptionManage")
    public String deviceManage() {
        return "/subscription/subscriptionManage";
    }

    /**
     *
     * 功能描述: 分页查询订阅列表
     */
    @RequestMapping(value = "/getSubscriptionList", method = RequestMethod.POST)
    @ResponseBody
    public PageDataResult getDeviceList(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize,/*@Valid PageRequest page,*/ SubscriptionSearchDTO subscriptionSearch) {
        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        String currentUserName = user.getSysUserName();
        logger.info("当前获取的用户名为:" + currentUserName);
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            // 获取用户列表
            pdr = adminSubscriptionService.getSubscriptionList(subscriptionSearch, pageNum ,pageSize);
            logger.info("设备列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            logger.error("设备列表查询异常！", e);
        }
        return pdr;
    }

    /**
     * 查询订阅
     * @return
     */
    @RequestMapping(value = "/getSubscription", method = RequestMethod.POST)
    @ResponseBody
    public String getSubscriptionList() {
        logger.info("进入到获取订阅信息页面");
        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        String currentUserName = user.getSysUserName();
        logger.info("当前获取的用户名为:" + currentUserName);
        //向电信云查询订阅信息
        String APP_KEY= "OIUJOR846L4";
        String APP_SECRET="HJm58BlJgM";
        String MasterKey="c24ccca89ea74111a360f925c95d3af2";
        String productId = "10045641";
        try {
            List<QuerySubscriptionList> resultList = NBIoTUtils.GetSubscriptionsList(APP_KEY,APP_SECRET,MasterKey,productId);
            QuerySubscriptionList list = new QuerySubscriptionList();
            for (int i=0;i<resultList.size();i++) {
                BaseAdminSubscription subscription = new BaseAdminSubscription();
                list = resultList.get(i);
                subscription.setSubId(list.getSubId());
                //1产品级，2设备级
                if (list.getSubLevel().equals("1")) {
                    subscription.setSubLevel("产品级");
                }else {
                    subscription.setSubLevel("设备级");
                }
                subscription.setDeviceId(list.getDeviceId());
                subscription.setProductId(list.getProductId());
                //订阅消息类型，1.设备数据变化 2.设备指令响应 3.设备事件上报 4.设备上下线通知 9.TUP合并数据上报
                if (list.getSubType().equals("1")) {
                    subscription.setSubType("设备数据变化");
                }else if (list.getSubType().equals("2")) {
                    subscription.setSubType("设备指令响应");
                }else if (list.getSubType().equals("3")) {
                    subscription.setSubType("设备事件上报");
                }else if (list.getSubType().equals("4")) {
                    subscription.setSubType("设备上下线通知");
                }else if (list.getSubType().equals("9")) {
                    subscription.setSubType("TUP合并数据上报");
                }
                subscription.setSubUrl(list.getSubUrl());
                String time = list.getCreateTime();
                subscription.setRegTime(time);
                //避免重复插入数据库操作
                String distinctSubId  = subscription.getSubId();
                //根据subId从数据库中查询数据
                BaseAdminSubscription subscriptionResult = baseAdminSubscriptionMapper.getSubscriptionBySubId(distinctSubId);
                if (subscriptionResult == null) {
                    baseAdminSubscriptionMapper.insert(subscription);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //将订阅信息插入到数据库
        return "success";
    }

    //彻底删除订阅（包括云端）
    /**
     * 功能描述: 删除/恢复设备
     */
    @RequestMapping(value = "/deleteSubscription", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteSubscription(@RequestParam("id") Integer id) {
        logger.info("删除订阅！id:" + id);
        Map<String, Object> data = new HashMap<>();
        //删除用户
        data = adminSubscriptionService.deleteSubscription(id);
        return data;
    }


    /**
     *
     * 功能描述: 新增和更新用户设备
     *
     */
    @RequestMapping(value = "/createSubscription", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> createSubscription(BaseAdminSubscription subscription) {
        logger.info("新增订阅！subscription:" + subscription);
        Map<String,Object> data = new HashMap();
        data = adminSubscriptionService.createSubscription(subscription);
        return data;
    }
}
