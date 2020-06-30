package com.haiyu.manager.nbpojo;

/**
 * 设备的返回内置对象
 */
public class QueryProductResult {

    private String productId;
    private String productName;
    //租户ID
    private String tenantId;
    //产品ID
    private String productDesc;
    //IMEI号
    private String productType;
    //imsi
    private String secondaryType;
    //
    private String thirdType;
    //设备状态
    private Integer productProtocol;
    //是否订阅 0否 1是
    private Integer authType;
    //创建时间
    private String payloadFormat;
    //创建者
    private String createTime;
    //
    private String updateTime;
    //
    private String networkType;
    //设备在线状态 1：在线 2：不在
    private String endpointFormat;
    //设备最后上线时间
    private String powerModel;
    //产品的MasterKey
    private String apiKey;
    //南向云需要 设备编号，非Nb设备返回
    private String createBy;
    private String updateBy;
    private String onlineDeviceCount;
    private String deviceCount;
    private String productTypeValue;
    private String secondaryTypeValue;
    private String thirdTypeValue;
    private String rootCert;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(String secondaryType) {
        this.secondaryType = secondaryType;
    }

    public String getThirdType() {
        return thirdType;
    }

    public void setThirdType(String thirdType) {
        this.thirdType = thirdType;
    }

    public Integer getProductProtocol() {
        return productProtocol;
    }

    public void setProductProtocol(Integer productProtocol) {
        this.productProtocol = productProtocol;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public String getPayloadFormat() {
        return payloadFormat;
    }

    public void setPayloadFormat(String payloadFormat) {
        this.payloadFormat = payloadFormat;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getEndpointFormat() {
        return endpointFormat;
    }

    public void setEndpointFormat(String endpointFormat) {
        this.endpointFormat = endpointFormat;
    }

    public String getPowerModel() {
        return powerModel;
    }

    public void setPowerModel(String powerModel) {
        this.powerModel = powerModel;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getOnlineDeviceCount() {
        return onlineDeviceCount;
    }

    public void setOnlineDeviceCount(String onlineDeviceCount) {
        this.onlineDeviceCount = onlineDeviceCount;
    }

    public String getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(String deviceCount) {
        this.deviceCount = deviceCount;
    }

    public String getProductTypeValue() {
        return productTypeValue;
    }

    public void setProductTypeValue(String productTypeValue) {
        this.productTypeValue = productTypeValue;
    }

    public String getSecondaryTypeValue() {
        return secondaryTypeValue;
    }

    public void setSecondaryTypeValue(String secondaryTypeValue) {
        this.secondaryTypeValue = secondaryTypeValue;
    }

    public String getThirdTypeValue() {
        return thirdTypeValue;
    }

    public void setThirdTypeValue(String thirdTypeValue) {
        this.thirdTypeValue = thirdTypeValue;
    }

    public String getRootCert() {
        return rootCert;
    }

    public void setRootCert(String rootCert) {
        this.rootCert = rootCert;
    }

    @Override
    public String toString() {
        return "QueryProductResult{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", productType='" + productType + '\'' +
                ", secondaryType='" + secondaryType + '\'' +
                ", thirdType='" + thirdType + '\'' +
                ", productProtocol=" + productProtocol +
                ", authType=" + authType +
                ", payloadFormat='" + payloadFormat + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", networkType='" + networkType + '\'' +
                ", endpointFormat='" + endpointFormat + '\'' +
                ", powerModel='" + powerModel + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", onlineDeviceCount='" + onlineDeviceCount + '\'' +
                ", deviceCount='" + deviceCount + '\'' +
                ", productTypeValue='" + productTypeValue + '\'' +
                ", secondaryTypeValue='" + secondaryTypeValue + '\'' +
                ", thirdTypeValue='" + thirdTypeValue + '\'' +
                ", rootCert='" + rootCert + '\'' +
                '}';
    }
}
