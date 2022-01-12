package com.example.sampleschool.Model;

public class SubjectModel {
    private String materialEdtLink,subjectName,subjTeacher,subjTchrImg;

    public SubjectModel() {
    }

    public SubjectModel(String materialEdtLink, String subjectName, String subjTeacher, String subjTchrImg) {
        this.materialEdtLink = materialEdtLink;
        this.subjectName = subjectName;
        this.subjTeacher = subjTeacher;
        this.subjTchrImg = subjTchrImg;
    }

    public String getMaterialEdtLink() {
        return materialEdtLink;
    }

    public void setMaterialEdtLink(String materialEdtLink) {
        this.materialEdtLink = materialEdtLink;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjTeacher() {
        return subjTeacher;
    }

    public void setSubjTeacher(String subjTeacher) {
        this.subjTeacher = subjTeacher;
    }

    public String getSubjTchrImg() {
        return subjTchrImg;
    }

    public void setSubjTchrImg(String subjTchrImg) {
        this.subjTchrImg = subjTchrImg;
    }
}
