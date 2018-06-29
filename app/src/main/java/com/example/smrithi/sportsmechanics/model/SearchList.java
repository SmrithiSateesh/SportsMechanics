package com.example.smrithi.sportsmechanics.model;

import java.util.List;

public class SearchList {


    private int total_count;
    private int page;
    private int total_pages;

    private List<SearchResponse> data;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<SearchResponse> getData() {
        return data;
    }

    public void setData(List<SearchResponse> data) {
        this.data = data;
    }
}
