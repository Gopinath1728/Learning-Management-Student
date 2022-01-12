package com.example.sampleschool.Callback;

import com.example.sampleschool.Model.WeekdayModel;

import java.util.List;

public interface IWeekdayCallbackListener {
    void onWeekdayLoadSuccess(List<WeekdayModel> weekdayModelList);
    void onWeekdayLoadFailed(String message);
}
