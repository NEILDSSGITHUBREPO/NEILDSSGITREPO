package com.dss.rest.dto;

import java.util.Collection;

public class PageResult<D extends Collection> {

    int page;
    int totalPage;
    int size;
    D content;

    public PageResult(int page, int totalPage, int size, D content) {
        this.page = page;
        this.totalPage = totalPage;
        this.size = size;
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public D getContent() {
        return content;
    }

    public void setContent(D content) {
        this.content = content;
    }
}
