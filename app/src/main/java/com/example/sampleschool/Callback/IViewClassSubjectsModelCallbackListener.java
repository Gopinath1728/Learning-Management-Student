package com.example.sampleschool.Callback;



import com.example.sampleschool.Model.SubjectModel;

import java.util.List;

public interface IViewClassSubjectsModelCallbackListener {
    void onSubjectLoadSuccess(List<SubjectModel> subjectModelList);
    void onSubjectLoadFailed(String subjectLoadError);
}
