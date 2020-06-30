package com.haiyu.manager.nbpojo;

/**
 * 解析云平台传输过来的数据
 */
public class JsonSingleResponse {

    private Integer code;
    private IotData deviceStatus;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public IotData getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(IotData deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    @Override
    public String toString() {
        return "JsonSingleResponse{" +
                "code=" + code +
                ", deviceStatusList=" + deviceStatus +
                '}';
    }
}
