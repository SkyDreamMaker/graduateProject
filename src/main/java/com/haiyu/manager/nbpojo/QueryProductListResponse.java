package com.haiyu.manager.nbpojo;

/**
 * 返回的设备信息列表
 */
public class QueryProductListResponse {

    private Integer code;
    private String msg;
    private QueryProductListResult result;

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

    public QueryProductListResult getResult() {
        return result;
    }

    public void setResult(QueryProductListResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CreateDeviceResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
