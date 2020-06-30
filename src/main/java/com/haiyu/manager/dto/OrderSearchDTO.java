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
public class OrderSearchDTO {
    private String productId;

    private String deviceId;

    private String startTime;

    private String endTime;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
