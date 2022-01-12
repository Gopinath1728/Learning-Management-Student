package com.example.sampleschool.ui.timetable.Weekdays.Tuesday;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.TimeModel;

import java.util.List;

public class TuesdayViewModel extends ViewModel {
    private MutableLiveData<List<TimeModel>> mutableLiveData;

    public TuesdayViewModel() {
    }

    public MutableLiveData<List<TimeModel>> getMutableLiveData() {
        if (mutableLiveData==null)
            mutableLiveData=new MutableLiveData<>();
        mutableLiveData.setValue(Common.classModel.getValue().getTimetable().get(1).getTime());
        return mutableLiveData;
    }
}