package com.haiyu.manager.dao;


import com.haiyu.manager.dto.AdminOrderDTO;
import com.haiyu.manager.dto.AdminSubscriptionDTO;
import com.haiyu.manager.dto.OrderSearchDTO;
import com.haiyu.manager.dto.SubscriptionSearchDTO;
import com.haiyu.manager.pojo.BaseAdminOrder;
import com.haiyu.manager.pojo.BaseAdminSubscription;
import com.haiyu.manager.pojo.BaseAdminUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mapper.MyMapper;

import java.util.List;

@Repository
public interface BaseAdminOrderMapper extends MyMapper<BaseAdminOrder> {

    /*查询列表*/
    List<AdminOrderDTO> getOrderList(OrderSearchDTO orderSearchDTO);
    int cancelOrder(@Param("id") Integer id);
    BaseAdminOrder getOrderById(@Param("id") Integer id);
    BaseAdminOrder getOrderByCommandId(@Param("commandId") String commandId);
    int updateStatusByCommandId(@Param("commandId") String commandId,@Param("commandStatus") String commmandStatus);
    int updateOrder(BaseAdminOrder order);
}
