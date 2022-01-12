package com.example.sampleschool.Callback;




import com.example.sampleschool.Model.ClassModel;

import java.util.List;

public interface IViewClassesCallbackListener {
    void onClassesLoadSuccess(ClassModel classModel);
    void onClassesLoadFailed(String error);
}
