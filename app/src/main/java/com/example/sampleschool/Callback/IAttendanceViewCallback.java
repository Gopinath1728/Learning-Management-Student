package com.example.sampleschool.Callback;

import com.example.sampleschool.Model.AttendanceModel;

import java.util.List;

public interface IAttendanceViewCallback {
    void onAttendanceLoadSuccess(List<AttendanceModel> attendanceModelList);
    void onAttendanceLoadFailed(String attendanceLoadError);
}
