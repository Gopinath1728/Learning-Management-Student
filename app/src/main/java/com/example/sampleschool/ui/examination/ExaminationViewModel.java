package com.example.sampleschool.ui.examination;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.ExaminationModel;

import java.util.List;

public class ExaminationViewModel extends ViewModel {
    private MutableLiveData<List<ExaminationModel>> ExamMutableLiveData;

    public ExaminationViewModel() {
    }

    public MutableLiveData<List<ExaminationModel>> getExamMutableLiveData() {
        if (ExamMutableLiveData == null)
            ExamMutableLiveData = new MutableLiveData<>();
        ExamMutableLiveData.setValue(Common.myExamsList);
        return ExamMutableLiveData;
    }
}