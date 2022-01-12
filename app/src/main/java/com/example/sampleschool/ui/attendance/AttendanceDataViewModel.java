package com.example.sampleschool.ui.attendance;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.AddAttendanceModel;

import java.util.List;

public class AttendanceDataViewModel extends ViewModel {
    private MutableLiveData<List<AddAttendanceModel>> mutableLiveData;

    public AttendanceDataViewModel() {
    }

    public MutableLiveData<List<AddAttendanceModel>> getMutableLiveData(int pos) {
        if (mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(Common.myAttendanceList.get(pos).getAttendance());
        return mutableLiveData;
    }
}
