package com.example.sampleschool.Callback;

import com.example.sampleschool.Model.ResultModel;

import java.util.List;

public interface IResultViewCallbackListener {
    void onResultLoadSuccess(List<ResultModel> resultModelList);
    void onResultLoadFailed(String resultLoadError);
}
