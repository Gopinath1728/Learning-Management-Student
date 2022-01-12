package com.example.sampleschool.ui.results;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.ResultModel;

import java.util.List;

public class ResultViewModel extends ViewModel {
    private MutableLiveData<List<ResultModel>> resultMutableLiveData;

    public ResultViewModel() {
    }

    public MutableLiveData<List<ResultModel>> getResultMutableLiveData() {
        if (resultMutableLiveData == null)
            resultMutableLiveData = new MutableLiveData<>();
        resultMutableLiveData.setValue(Common.myResult);
        return resultMutableLiveData;
    }
}