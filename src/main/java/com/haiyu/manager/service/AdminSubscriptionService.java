package com.haiyu.manager.service;

import com.haiyu.manager.dto.DeviceSearchDTO;
import com.haiyu.manager.dto.SubscriptionSearchDTO;
import com.haiyu.manager.pojo.BaseAdminDevice;
import com.haiyu.manager.pojo.BaseAdminSubscription;
import com.haiyu.manager.pojo.BaseAdminUser;
import com.haiyu.manager.response.PageDataResult;

import java.util.Map;


/**
 * @Title: AdminUserService
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/11/21 11:04
 */
public interface AdminSubscriptionService {

    PageDataResult getSubscriptionList(SubscriptionSearchDTO subscriptionSearch, Integer pageNum, Integer pageSize);
    //完全删除订阅
    Map<String, Object> deleteSubscription(Integer id);
    //创建订阅
    Map<String, Object> createSubscription(BaseAdminSubscription subscription);

}
