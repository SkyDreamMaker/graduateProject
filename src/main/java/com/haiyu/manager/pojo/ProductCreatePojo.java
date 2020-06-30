package com.haiyu.manager.pojo;

public class ProductCreatePojo {
    private String productsName;
    private String productType;
    private String secondType;
    private String thirdType;

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSecondType() {
        return secondType;
    }

    public void setSecondType(String secondType) {
        this.secondType = secondType;
    }

    public String getThirdType() {
        return thirdType;
    }

    public void setThirdType(String thirdType) {
        this.thirdType = thirdType;
    }

    @Override
    public String toString() {
        return "ProductCreatePojo{" +
                "productsName='" + productsName + '\'' +
                ", productType='" + productType + '\'' +
                ", secondType='" + secondType + '\'' +
                ", thirdType='" + thirdType + '\'' +
                '}';
    }
}
