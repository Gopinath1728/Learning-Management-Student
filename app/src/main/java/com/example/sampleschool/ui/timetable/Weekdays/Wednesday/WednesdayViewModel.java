package com.example.sampleschool.ui.timetable.Weekdays.Wednesday;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.TimeModel;

import java.util.List;

public class WednesdayViewModel extends ViewModel {
    private MutableLiveData<List<TimeModel>> mutableLiveData;

    public WednesdayViewModel() {
    }

    public MutableLiveData<List<TimeModel>> getMutableLiveData() {
        if (mutableLiveData==null)
            mutableLiveData=new MutableLiveData<>();
        mutableLiveData.setValue(Common.classModel.getValue().getTimetable().get(2).getTime());
        return mutableLiveData;
    }
}