package com.example.smrithi.sportsmechanics.model;

public class SearchRequest {
    private int page;
    private int per_page;
    private VideoSearchBean video_search;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public VideoSearchBean getVideo_search() {
        return video_search;
    }

    public void setVideo_search(VideoSearchBean video_search) {
        this.video_search = video_search;
    }

    public static class VideoSearchBean {
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }


   /* public VideoSearchBean video_search;

    public VideoSearchBean getVideo_search() {
        return video_search;
    }

    public void setVideo_search(VideoSearchBean video_search) {
        this.video_search = video_search;
    }

    public static class VideoSearchBean {
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }*/



}
