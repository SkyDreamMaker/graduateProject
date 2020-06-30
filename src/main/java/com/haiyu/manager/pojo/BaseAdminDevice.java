package com.haiyu.manager.pojo;

import javax.persistence.*;

@Table(name = "device")
public class BaseAdminDevice {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 系统用户名称
     */
    @Column(name = "userName")
    private String sysUserName;

    /**
     * 系统用户名
     */
    @Column(name = "deviceName")
    private String deviceName;

    /**
     * 角色
     */
    @Column(name = "deviceId")
    private String deviceId;

    /**
     * 登记时间
     */
    @Column(name = "reg_time")
    private String regTime;

    /**
     * 状态（0：无效；1：有效）
     */
    @Column(name = "status")
    private Integer deviceStatus;

    /**
     * 状态（0：无效；1：有效）
     */
    @Column(name = "imei")
    private String imei;

    @Column(name = "productId")
    private String productId;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    @Override
    public String toString() {
        return "BaseAdminDevice{" +
                "id=" + id +
                ", sysUserName='" + sysUserName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", regTime='" + regTime + '\'' +
                ", deviceStatus=" + deviceStatus +
                ", imei='" + imei + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
