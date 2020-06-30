package com.haiyu.manager.dto;

import lombok.Data;

;

/**
 * @Title: AdminUserDTO
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/3 12:13
 */
@Data
public class AdminSubscriptionDTO {

    private Integer id;

    private String subId;

    private String subLevel;

    private String subType;

    private String regTime;

    private String productId;

    private String subUrl;

    private String deviceId;

}
