package com.haiyu.manager.dto;


import lombok.Data;

/**
 * @Title: UserSearchDTO
 */
@Data
public class DataSearchDTO {
    private String deviceId;

    private String datasetId;

    private String dataId;

    private String startTime;

    private String endTime;

}
