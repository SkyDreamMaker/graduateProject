package com.haiyu.manager.nbpojo;

import java.util.Arrays;
import java.util.List;

public class QuerySubscriptionResult {
    private String pageNum;
    private String pageSize;
    private String size;
    private String startRow;
    private String endRow;
    private String total;
    private String pages;
    private String firstPage;
    private String prePage;
    private String nextPage;
    private String lastPage;
    private String isFirstPage;
    private String isLastPage;
    private String hasPreviousPage;
    private String hasNextPage;
    private String navigatePages;
    private int navigatepageNums[];
    //private String navigatepageNums;
    private List<QuerySubscriptionList> list;

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStartRow() {
        return startRow;
    }

    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }

    public String getEndRow() {
        return endRow;
    }

    public void setEndRow(String endRow) {
        this.endRow = endRow;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public List<QuerySubscriptionList> getList() {
        return list;
    }

    public void setList(List<QuerySubscriptionList> list) {
        this.list = list;
    }

    public String getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(String firstPage) {
        this.firstPage = firstPage;
    }

    public String getPrePage() {
        return prePage;
    }

    public void setPrePage(String prePage) {
        this.prePage = prePage;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getLastPage() {
        return lastPage;
    }

    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }

    public String getIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(String isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public String getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(String isLastPage) {
        this.isLastPage = isLastPage;
    }

    public String getHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(String hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public String getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(String hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public String getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(String navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int[] getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    @Override
    public String toString() {
        return "QuerySubscriptionResult{" +
                "pageNum='" + pageNum + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", size='" + size + '\'' +
                ", startRow='" + startRow + '\'' +
                ", endRow='" + endRow + '\'' +
                ", total='" + total + '\'' +
                ", pages='" + pages + '\'' +
                ", firstPage='" + firstPage + '\'' +
                ", prePage='" + prePage + '\'' +
                ", nextPage='" + nextPage + '\'' +
                ", lastPage='" + lastPage + '\'' +
                ", isFirstPage='" + isFirstPage + '\'' +
                ", isLastPage='" + isLastPage + '\'' +
                ", hasPreviousPage='" + hasPreviousPage + '\'' +
                ", hasNextPage='" + hasNextPage + '\'' +
                ", navigatePages='" + navigatePages + '\'' +
                ", navigatepageNums=" + Arrays.toString(navigatepageNums) +
                ", list=" + list +
                '}';
    }
}
