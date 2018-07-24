package com.example.smrithi.sportsmechanics.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchResponse {

    private int id;
    private int sno;
    private String match_year;
    private String match_name;
    private String competition_name;
    private String match_type_name;
    private String striker_name;
    private String bowler_name;
    private String fielder_name;
    private int runs;
    private int wicket;
    private String video_location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getMatch_year() {
        return match_year;
    }

    public void setMatch_year(String match_year) {
        this.match_year = match_year;
    }

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }

    public String getCompetition_name() {
        return competition_name;
    }

    public void setCompetition_name(String competition_name) {
        this.competition_name = competition_name;
    }

    public String getMatch_type_name() {
        return match_type_name;
    }

    public void setMatch_type_name(String match_type_name) {
        this.match_type_name = match_type_name;
    }

    public String getStriker_name() {
        return striker_name;
    }

    public void setStriker_name(String striker_name) {
        this.striker_name = striker_name;
    }

    public String getBowler_name() {
        return bowler_name;
    }

    public void setBowler_name(String bowler_name) {
        this.bowler_name = bowler_name;
    }

    public String getFielder_name() {
        return fielder_name;
    }

    public void setFielder_name(String fielder_name) {
        this.fielder_name = fielder_name;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getWicket() {
        return wicket;
    }

    public void setWicket(int wicket) {
        this.wicket = wicket;
    }

    public String getVideo_location() {
        return video_location;
    }

    public void setVideo_location(String video_location) {
        this.video_location = video_location;
    }
}

