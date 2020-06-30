package com.haiyu.manager.nbpojo;

/**
 * 返回的设备信息列表
 */
public class CreateProductResponse {

    private Integer code;
    private String msg;
    private CreateProductResult result;

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

    public CreateProductResult getResult() {
        return result;
    }

    public void setResult(CreateProductResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CreateProductResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
