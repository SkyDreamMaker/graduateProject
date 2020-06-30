package com.haiyu.manager.nbpojo;

import java.util.List;

/**
 * 解析云平台传输过来的数据
 */
public class JsonResponse {

    private Integer code;
    private List<IotData> deviceStatusList;

    public JsonResponse() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<IotData> getDeviceStatusList() {
        return deviceStatusList;
    }

    public void setDeviceStatusList(List<IotData> deviceStatusList) {
        this.deviceStatusList = deviceStatusList;
    }

    @Override
    public String toString() {
        return "User{" +
                "code=" + code +
                ", deviceStatusList='" + deviceStatusList + '\'' +
                '}';
    }
}
