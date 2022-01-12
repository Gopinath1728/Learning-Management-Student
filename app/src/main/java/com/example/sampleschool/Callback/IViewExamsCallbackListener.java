package com.example.sampleschool.Callback;



import com.example.sampleschool.Model.ExaminationModel;

import java.util.List;

public interface IViewExamsCallbackListener {
    void onExamsLoadSuccess(List<ExaminationModel> examinationModels);
    void onExamsLoadFailed(String examLoadError);
}
