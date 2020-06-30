package com.haiyu.manager.nbpojo;

public class QuerySubscriptionList {
    private String subId;
    private String productId;
    private String tenantId;
    private String deviceId;
    private String subType;
    private String subLevel;
    private String subUrl;
    private String createTime;
    private String createBy;
    private String updateTime;
    private String updateBy;
    private String isSub;
    private String isDel;
    private String isVpn;

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubLevel() {
        return subLevel;
    }

    public void setSubLevel(String subLevel) {
        this.subLevel = subLevel;
    }

    public String getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(String subUrl) {
        this.subUrl = subUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getIsSub() {
        return isSub;
    }

    public void setIsSub(String isSub) {
        this.isSub = isSub;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getIsVpn() {
        return isVpn;
    }

    public void setIsVpn(String isVpn) {
        this.isVpn = isVpn;
    }

    @Override
    public String toString() {
        return "QuerySubscriptionResult{" +
                "subId='" + subId + '\'' +
                ", productId='" + productId + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", subType='" + subType + '\'' +
                ", subLevel='" + subLevel + '\'' +
                ", subUrl='" + subUrl + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", isSub='" + isSub + '\'' +
                ", isDel='" + isDel + '\'' +
                ", isVpn='" + isVpn + '\'' +
                '}';
    }
}
