package com.haiyu.manager.dto;


import lombok.Data;

/**
 * @Title: UserSearchDTO
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/11/21 11:19
 */
@Data
public class SubscriptionSearchDTO {
    private String subId;

    private String subLevel;

    private String startTime;

    private String endTime;

}
