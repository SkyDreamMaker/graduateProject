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
public class AdminOrderDTO {

    private Integer id;

    private String commandId;

    private String productId;

    private String deviceId;

    private String createTime;

    private String commandStatus;

    private String imei;

}
