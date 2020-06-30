package com.haiyu.manager.nbpojo;

import java.util.List;

public class QueryOrderListResult {

    private String pageNum;
    private String pageSize;
    private String total;
    private List<QueryOrderListList> list;

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

    public List<QueryOrderListList> getList() {
        return list;
    }

    public void setList(List<QueryOrderListList> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "QueryOrderListResult{" +
                "pageNum='" + pageNum + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", total='" + total + '\'' +
                ", list=" + list +
                '}';
    }
}
