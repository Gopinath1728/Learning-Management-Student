package com.example.sampleschool.Model;

public class ResultModel {
    private String subjectName,marksObtained,totalMarks,examType;

    public ResultModel() {
    }

    public ResultModel(String subjectName, String marksObtained, String totalMarks, String examType) {
        this.subjectName = subjectName;
        this.marksObtained = marksObtained;
        this.totalMarks = totalMarks;
        this.examType = examType;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(String marksObtained) {
        this.marksObtained = marksObtained;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }
}
