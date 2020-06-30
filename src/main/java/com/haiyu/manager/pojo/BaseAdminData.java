package com.haiyu.manager.pojo;

import javax.persistence.*;

@Table(name = "data")
public class BaseAdminData {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 数据名称
     */
    @Column(name = "datasetId")
    private String datasetId;

    /**
     * 数据ID
     */
    @Column(name = "dataId")
    private Integer dataId;

    /**
     * 角色
     */
    @Column(name = "dataValue")
    private String dataValue;

    /**
     * 设备ID
     */
    @Column(name = "deviceId")
    private String deviceId;

    /**
     * 登记时间
     */
    @Column(name = "reg_time")
    private String regTime;

    /*设备状态*/
    @Column(name = "status")
    private Integer dataStatus;


    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
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

    @Override
    public String toString() {
        return "BaseAdminData{" +
                "id=" + id +
                ", datasetId='" + datasetId + '\'' +
                ", dataId=" + dataId +
                ", dataValue='" + dataValue + '\'' +
                ", deviceId=" + deviceId +
                ", regTime='" + regTime + '\'' +
                ", dataStatus='" + dataStatus + '\'' +
                '}';
    }
}
