package com.haiyu.manager.nbpojo;

import java.util.List;

/**
 * 设备的返回内置对象
 */
public class QueryProductListResult {


    private String pageNum;
    private String pageSize;
    private String total;
    private List<QueryProductResult> list;

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<QueryProductResult> getList() {
        return list;
    }

    public void setList(List<QueryProductResult> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "QueryProductListResult{" +
                "pageNum='" + pageNum + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", total='" + total + '\'' +
                ", list=" + list +
                '}';
    }
}
