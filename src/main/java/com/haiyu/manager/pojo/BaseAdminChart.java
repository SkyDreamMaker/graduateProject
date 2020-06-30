package com.haiyu.manager.pojo;

import javax.persistence.*;

@Table(name = "chart")
public class BaseAdminChart {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "deviceName")
    private String deviceName;

    /**
     * 数据名称
     */
    @Column(name = "temperature")
    private String temperature;

    /**
     * 数据ID
     */
    @Column(name = "humilevel")
    private String humilevel;


    /**
     * 设备ID
     */
    @Column(name = "deviceId")
    private String deviceId;

    /**
     * 附加信息
     */
    @Column(name = "message")
    private String message;

    /**
     * 电量信息
     */
    @Column(name = "battery")
    private String battery;

    /**
     * 信号强度
     */
    @Column(name = "signalStrength")
    private String signalStrength;

    /**
     * 登记时间
     */
    @Column(name = "reg_time")
    private String regTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumilevel() {
        return humilevel;
    }

    public void setHumilevel(String humilevel) {
        this.humilevel = humilevel;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(String signalStrength) {
        this.signalStrength = signalStrength;
    }

    @Override
    public String toString() {
        return "BaseAdminChart{" +
                "id=" + id +
                ", deviceName='" + deviceName + '\'' +
                ", temperature='" + temperature + '\'' +
                ", humilevel='" + humilevel + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", message='" + message + '\'' +
                ", battery='" + battery + '\'' +
                ", signalStrength='" + signalStrength + '\'' +
                ", regTime='" + regTime + '\'' +
                '}';
    }
}
