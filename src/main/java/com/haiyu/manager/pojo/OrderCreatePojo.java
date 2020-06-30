package com.haiyu.manager.pojo;

public class OrderCreatePojo {

    private String productId;
    private String deviceId;
    private String serviceIdentifier;
    private String orderInfo;
    private String orderValue;

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

    public String getServiceIdentifier() {
        return serviceIdentifier;
    }

    public void setServiceIdentifier(String serviceIdentifier) {
        this.serviceIdentifier = serviceIdentifier;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(String orderValue) {
        this.orderValue = orderValue;
    }

    @Override
    public String toString() {
        return "OrderCreatePojo{" +
                "productId='" + productId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", serviceIdentifier='" + serviceIdentifier + '\'' +
                ", orderInfo='" + orderInfo + '\'' +
                ", orderValue='" + orderValue + '\'' +
                '}';
    }
}
