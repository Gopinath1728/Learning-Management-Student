package com.example.sampleschool.Model;

import java.util.List;

public class WeekdayModel {
    private String dayName;
    List<TimeModel> time;

    public WeekdayModel() {
    }

    public WeekdayModel(String dayName, List<TimeModel> time) {
        this.dayName = dayName;
        this.time = time;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public List<TimeModel> getTime() {
        return time;
    }

    public void setTime(List<TimeModel> time) {
        this.time = time;
    }
}
