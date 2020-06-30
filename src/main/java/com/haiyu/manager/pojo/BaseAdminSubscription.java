package com.haiyu.manager.pojo;

import javax.persistence.*;

@Table(name = "subscription")
public class BaseAdminSubscription {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 系统用户名称
     */
    @Column(name = "subId")
    private String subId;

    /**
     * 系统用户名
     */
    @Column(name = "productId")
    private String productId;

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
    @Column(name = "subType")
    private String subType;

    /**
     * 状态（0：无效；1：有效）
     */
    @Column(name = "subUrl")
    private String subUrl;

    @Column(name = "subLevel")
    private String subLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(String subUrl) {
        this.subUrl = subUrl;
    }

    public String getSubLevel() {
        return subLevel;
    }

    public void setSubLevel(String subLevel) {
        this.subLevel = subLevel;
    }

    @Override
    public String toString() {
        return "BaseAdminSubscription{" +
                "id=" + id +
                ", subId='" + subId + '\'' +
                ", productId='" + productId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", regTime='" + regTime + '\'' +
                ", subType=" + subType +
                ", subUrl='" + subUrl + '\'' +
                ", subLevel='" + subLevel + '\'' +
                '}';
    }

    public BaseAdminSubscription(String productId, String deviceId, String subType, String subUrl, String subLevel) {
        this.productId = productId;
        this.deviceId = deviceId;
        this.subType = subType;
        this.subUrl = subUrl;
        this.subLevel = subLevel;
    }

    public BaseAdminSubscription() {
    }
}
