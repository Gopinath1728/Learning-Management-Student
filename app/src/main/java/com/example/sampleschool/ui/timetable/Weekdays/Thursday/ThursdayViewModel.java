package com.example.sampleschool.ui.timetable.Weekdays.Thursday;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.TimeModel;

import java.util.List;

public class ThursdayViewModel extends ViewModel {
    private MutableLiveData<List<TimeModel>> mutableLiveData;

    public ThursdayViewModel() {
    }

    public MutableLiveData<List<TimeModel>> getMutableLiveData() {
        if (mutableLiveData==null)
            mutableLiveData=new MutableLiveData<>();
        mutableLiveData.setValue(Common.classModel.getValue().getTimetable().get(3).getTime());
        return mutableLiveData;
    }
}