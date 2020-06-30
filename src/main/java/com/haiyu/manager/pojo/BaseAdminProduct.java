package com.haiyu.manager.pojo;

import javax.persistence.*;

@Table(name = "product")
public class BaseAdminProduct {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 数据名称
     */
    @Column(name = "productId")
    private String productId;

    /**
     * 数据ID
     */
    @Column(name = "productName")
    private String productName;


    /**
     * 设备ID
     */
    @Column(name = "apiKey")
    private String apiKey;

    /**
     * 设备ID
     */
    @Column(name = "productType")
    private String productType;

    /**
     * 登记时间
     */
    @Column(name = "reg_time")
    private String regTime;

    @Column(name = "status")
    private Integer productStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    @Override
    public String toString() {
        return "BaseAdminProduct{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", productType='" + productType + '\'' +
                ", regTime='" + regTime + '\'' +
                ", productStatus=" + productStatus +
                '}';
    }
}
