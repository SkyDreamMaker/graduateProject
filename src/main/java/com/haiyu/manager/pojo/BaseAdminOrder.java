package com.haiyu.manager.pojo;

import javax.persistence.*;

@Table(name = "cmd")
public class BaseAdminOrder {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 系统用户名称
     */
    @Column(name = "commandId")
    private String commandId;

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
    @Column(name = "createTime")
    private String createTime;

    /**
     * 状态（0：无效；1：有效）
     */
    @Column(name = "commandStatus")
    private String commandStatus;

    /**
     * 状态（0：无效；1：有效）
     */
    @Column(name = "imei")
    private String imei;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCommandStatus() {
        return commandStatus;
    }

    public void setCommandStatus(String commandStatus) {
        this.commandStatus = commandStatus;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public String toString() {
        return "BaseAdminOrder{" +
                "id=" + id +
                ", commandId='" + commandId + '\'' +
                ", productId='" + productId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", commandStatus='" + commandStatus + '\'' +
                ", imei='" + imei + '\'' +
                '}';
    }
}
