package com.haiyu.manager.nbpojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@lombok.Data
public class IotData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //设置id自增
    private Integer id;
    private String datasetId;
    private String value;
    private Long timestamp;
    private Long deviceId;

    public IotData() {
    }

    public IotData(String datasetId, String value, Long timestamp) {
        this.datasetId = datasetId;
        this.value = value;
        this.timestamp = timestamp;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", datasetId='" + datasetId + '\'' +
                ", value='" + value + '\'' +
                ", timestamp=" + timestamp +
                ", deviceId=" + deviceId +
                '}';
    }
}
