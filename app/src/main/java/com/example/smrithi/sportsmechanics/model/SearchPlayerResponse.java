package com.example.smrithi.sportsmechanics.model;

import com.google.gson.Gson;

import java.util.List;

public class SearchPlayerResponse {


    private DataBean data;

    public static SearchPlayerResponse objectFromData(String str) {

        return new Gson().fromJson(str, SearchPlayerResponse.class);
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int total_count;
        private List<String> names;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public List<String> getNames() {
            return names;
        }

        public void setNames(List<String> names) {
            this.names = names;
        }
    }
}
