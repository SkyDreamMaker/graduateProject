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
public class AdminChartDTO {

    private Integer id;

    private String deviceName;

    private String deviceId;

    private Integer temperature;

    private String regTime;

    private String  humilevel;

}
