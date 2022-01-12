package com.example.sampleschool.ui.timetable.Weekdays.Friday;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.TimeModel;

import java.util.List;

public class FridayViewModel extends ViewModel {
    private MutableLiveData<List<TimeModel>> mutableLiveData;

    public FridayViewModel() {
    }

    public MutableLiveData<List<TimeModel>> getMutableLiveData() {
        if (mutableLiveData==null)
            mutableLiveData=new MutableLiveData<>();
        mutableLiveData.setValue(Common.classModel.getValue().getTimetable().get(4).getTime());
        return mutableLiveData;
    }
}