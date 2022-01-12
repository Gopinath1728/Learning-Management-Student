package com.example.sampleschool.Model;

public class QuizResultModel {
    private String studentUid;
    private float score;

    public QuizResultModel() {
    }

    public QuizResultModel(String studentUid, float score) {
        this.studentUid = studentUid;
        this.score = score;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getStudentUid() {
        return studentUid;
    }

    public void setStudentUid(String studentUid) {
        this.studentUid = studentUid;
    }
}
