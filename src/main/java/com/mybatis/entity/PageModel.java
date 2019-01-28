package com.mybatis.entity;

public class PageModel {

    private Integer page = 1;

    private Integer pageSize = 10;

    private Integer startIndex;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartIndex() {
        this.startIndex = (this.page - 1) * this.pageSize;
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }
}
