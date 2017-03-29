package com.sxq.mall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SXQ on 2017/3/5.
 */

public class Page<T> implements Serializable {

    private int totalCount ;

    private int currentPage ;

    private int totalPage ;

    private int pageSize ;

    private List<T> list ;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


    public String toString(){
        return "totalCount = " + totalCount
                + "\tcurrentPage = " + currentPage
                +  "\ttotalPage = " + totalPage
                + "\tpageSize = " + pageSize
                + "\nlist = " + list ;
    }

}
