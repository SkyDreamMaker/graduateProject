package com.haiyu.manager.nbpojo;

/**
 * 设备的返回内置对象
 */
public class DeviceResult {
    private String deviceId;
    private String deviceName;
    //租户ID
    private String tenantId;
    //产品ID
    private String productId;
    //IMEI号
    private String imei;
    //imsi
    private String imsi;
    //
    private String firmwareVersion;
    //设备状态
    private Integer deviceStatus;
    //是否订阅 0否 1是
    private Integer autoObserver;
    //创建时间
    private String createTime;
    //创建者
    private String createBy;
    //
    private String updateTime;
    //
    private String updateBy;
    //设备在线状态 1：在线 2：不在
    private String netStatus;
    //设备最后上线时间
    private String onlineAt;
    //设备最后下线时间
    private String offlineAt;
    //南向云需要 设备编号，非Nb设备返回
    private String deviceSn;

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public Integer getAutoObserver() {
        return autoObserver;
    }

    public void setAutoObserver(Integer autoObserver) {
        this.autoObserver = autoObserver;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
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

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

    public String getOnlineAt() {
        return onlineAt;
    }

    public void setOnlineAt(String onlineAt) {
        this.onlineAt = onlineAt;
    }

    public String getOfflineAt() {
        return offlineAt;
    }

    public void setOfflineAt(String offlineAt) {
        this.offlineAt = offlineAt;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DeviceResult{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", productId='" + productId + '\'' +
                ", imei='" + imei + '\'' +
                ", imsi='" + imsi + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", deviceStatus=" + deviceStatus +
                ", autoObserver=" + autoObserver +
                ", createTime='" + createTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", netStatus='" + netStatus + '\'' +
                ", onlineAt='" + onlineAt + '\'' +
                ", offlineAt='" + offlineAt + '\'' +
                ", deviceSn='" + deviceSn + '\'' +
                '}';
    }
}
