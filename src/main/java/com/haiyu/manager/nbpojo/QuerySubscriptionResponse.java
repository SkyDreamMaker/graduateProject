package com.haiyu.manager.nbpojo;

import java.util.List;

public class QuerySubscriptionResponse
{
    private String code;
    private String msg;
    private QuerySubscriptionResult result;

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

    public QuerySubscriptionResult getResult() {
        return result;
    }

    public void setResult(QuerySubscriptionResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "QuerySubscriptionResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
