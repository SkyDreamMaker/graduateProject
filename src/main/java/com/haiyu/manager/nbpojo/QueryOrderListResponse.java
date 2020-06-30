package com.haiyu.manager.nbpojo;

public class QueryOrderListResponse {

    private String code;
    private String msg;
    private QueryOrderListResult result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public QueryOrderListResult getResult() {
        return result;
    }

    public void setResult(QueryOrderListResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "QueryOrderListResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
