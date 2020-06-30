package com.haiyu.manager.nbpojo;

/**
 * 设备的返回内置对象
 */
public class CreateDeviceResult {
    private String deviceId;
    private String deviceName;
    //租户ID
    private String tenantId;
    private String dmpId;
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
    private String token;
    private String secret;
    private String lastTime;
    private String apn;
    private String iotVer;
    private String updateTime;
    private String mvendor;
    private String mver;
    private String tupVendorId;
    private String tupDeviceModel;
    private String tupDeviceType;
    private String tupIsProfile;
    private String psk;
    private String sn;
    private String certData;
    private String taskState;
    private String nbDeviceResourceInfos;
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
    private String deviceStatusStr;

    public String getDeviceStatusStr() {
        return deviceStatusStr;
    }

    public void setDeviceStatusStr(String deviceStatusStr) {
        this.deviceStatusStr = deviceStatusStr;
    }

    public String getDmpId() {
        return dmpId;
    }

    public void setDmpId(String dmpId) {
        this.dmpId = dmpId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getApn() {
        return apn;
    }

    public void setApn(String apn) {
        this.apn = apn;
    }

    public String getIotVer() {
        return iotVer;
    }

    public void setIotVer(String iotVer) {
        this.iotVer = iotVer;
    }

    public String getMvendor() {
        return mvendor;
    }

    public void setMvendor(String mvendor) {
        this.mvendor = mvendor;
    }

    public String getMver() {
        return mver;
    }

    public void setMver(String mver) {
        this.mver = mver;
    }

    public String getTupVendorId() {
        return tupVendorId;
    }

    public void setTupVendorId(String tupVendorId) {
        this.tupVendorId = tupVendorId;
    }

    public String getTupDeviceModel() {
        return tupDeviceModel;
    }

    public void setTupDeviceModel(String tupDeviceModel) {
        this.tupDeviceModel = tupDeviceModel;
    }

    public String getTupDeviceType() {
        return tupDeviceType;
    }

    public void setTupDeviceType(String tupDeviceType) {
        this.tupDeviceType = tupDeviceType;
    }

    public String getTupIsProfile() {
        return tupIsProfile;
    }

    public void setTupIsProfile(String tupIsProfile) {
        this.tupIsProfile = tupIsProfile;
    }

    public String getPsk() {
        return psk;
    }

    public void setPsk(String psk) {
        this.psk = psk;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCertData() {
        return certData;
    }

    public void setCertData(String certData) {
        this.certData = certData;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getNbDeviceResourceInfos() {
        return nbDeviceResourceInfos;
    }

    public void setNbDeviceResourceInfos(String nbDeviceResourceInfos) {
        this.nbDeviceResourceInfos = nbDeviceResourceInfos;
    }

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
        return "CreateDeviceResult{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", dmpId='" + dmpId + '\'' +
                ", productId='" + productId + '\'' +
                ", imei='" + imei + '\'' +
                ", imsi='" + imsi + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", deviceStatus=" + deviceStatus +
                ", autoObserver=" + autoObserver +
                ", createTime='" + createTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", token='" + token + '\'' +
                ", secret='" + secret + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", apn='" + apn + '\'' +
                ", iotVer='" + iotVer + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", mvendor='" + mvendor + '\'' +
                ", mver='" + mver + '\'' +
                ", tupVendorId='" + tupVendorId + '\'' +
                ", tupDeviceModel='" + tupDeviceModel + '\'' +
                ", tupDeviceType='" + tupDeviceType + '\'' +
                ", tupIsProfile='" + tupIsProfile + '\'' +
                ", psk='" + psk + '\'' +
                ", sn='" + sn + '\'' +
                ", certData='" + certData + '\'' +
                ", taskState='" + taskState + '\'' +
                ", nbDeviceResourceInfos='" + nbDeviceResourceInfos + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", netStatus='" + netStatus + '\'' +
                ", onlineAt='" + onlineAt + '\'' +
                ", offlineAt='" + offlineAt + '\'' +
                ", deviceSn='" + deviceSn + '\'' +
                ", deviceStatusStr='" + deviceStatusStr + '\'' +
                '}';
    }
}
