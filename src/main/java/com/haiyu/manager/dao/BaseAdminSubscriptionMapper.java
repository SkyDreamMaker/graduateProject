package com.haiyu.manager.dao;


import com.haiyu.manager.dto.AdminDeviceDTO;
import com.haiyu.manager.dto.AdminSubscriptionDTO;
import com.haiyu.manager.dto.DeviceSearchDTO;
import com.haiyu.manager.dto.SubscriptionSearchDTO;
import com.haiyu.manager.pojo.BaseAdminDevice;
import com.haiyu.manager.pojo.BaseAdminSubscription;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mapper.MyMapper;

import java.util.List;

@Repository
public interface BaseAdminSubscriptionMapper extends MyMapper<BaseAdminSubscription> {

    /*查询列表*/
    List<AdminSubscriptionDTO> getSubscriptionList(SubscriptionSearchDTO subscriptionSearchDTO);
    int deleteSubscription(@Param("id") Integer id);
    BaseAdminSubscription getSubscriptionById(@Param("id") Integer id);
    BaseAdminSubscription getSubscriptionBySubId(@Param("subId") String subId);
}
