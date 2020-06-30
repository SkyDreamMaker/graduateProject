package com.haiyu.manager.service;

import com.haiyu.manager.dto.OrderSearchDTO;
import com.haiyu.manager.dto.SubscriptionSearchDTO;
import com.haiyu.manager.pojo.BaseAdminOrder;
import com.haiyu.manager.pojo.BaseAdminSubscription;
import com.haiyu.manager.pojo.OrderCreatePojo;
import com.haiyu.manager.response.PageDataResult;

import java.util.Map;


/**
 * @Title: AdminUserService
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/11/21 11:04
 */
public interface AdminOrderService {

    PageDataResult getOrderList(OrderSearchDTO orderSearchDTO, Integer pageNum, Integer pageSize);
    //完全删除订阅
    Map<String, Object> cancelOrder(BaseAdminOrder order);
    //创建订阅
    Map<String, Object> createOrder(OrderCreatePojo order);

}
