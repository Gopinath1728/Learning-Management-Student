package com.example.sampleschool.ui.quiz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.QuizListModel;

import java.util.List;

public class QuizViewModel extends ViewModel {
    private MutableLiveData<List<QuizListModel>> mutableLiveData;

    public QuizViewModel() {
    }

    public MutableLiveData<List<QuizListModel>> getMutableLiveData() {
        if (mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(Common.myQuizList);
        return mutableLiveData;
    }
}
