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
public class AdminDeviceDTO {

    private Integer id;

    private String sysUserName;

    private String deviceName;

    private String deviceId;


    private String regTime;


    private Integer deviceStatus;

}
