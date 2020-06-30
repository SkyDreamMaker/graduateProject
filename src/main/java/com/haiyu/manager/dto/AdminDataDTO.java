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
public class AdminDataDTO {

    private Integer id;

    private String datasetId;

    private String deviceId;

    private Integer dataId;

    private String regTime;

    private String  dataValue;

    private Integer dataStatus;

}
