package com.haiyu.manager.nbpojo;

/**
 * 返回的设备信息列表
 */
public class DeviceResponse {

    private Integer code;
    private String msg;
    private DeviceResult result;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DeviceResult getResult() {
        return result;
    }

    public void setResult(DeviceResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DeviceResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
