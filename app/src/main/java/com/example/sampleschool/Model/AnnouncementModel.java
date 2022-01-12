package com.example.sampleschool.Model;

public class AnnouncementModel {
    private String announcementTitle, announcementBody,date,owner;

    public AnnouncementModel() {
    }

    public AnnouncementModel(String announcementTitle, String announcementBody, String date, String owner) {
        this.announcementTitle = announcementTitle;
        this.announcementBody = announcementBody;
        this.date = date;
        this.owner = owner;
    }

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
    }

    public String getAnnouncementBody() {
        return announcementBody;
    }

    public void setAnnouncementBody(String announcementBody) {
        this.announcementBody = announcementBody;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
