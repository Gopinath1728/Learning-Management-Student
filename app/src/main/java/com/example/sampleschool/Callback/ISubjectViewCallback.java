package com.example.sampleschool.Callback;


import com.example.sampleschool.Model.SubjectModel;

import java.util.List;

public interface ISubjectViewCallback {
    void onWeekdayLoadSuccess(List<SubjectModel> subjectModelList);
    void onWeekdayLoadFailed(String message);
}
