package com.example.sampleschool.Callback;

import com.example.sampleschool.Model.AssignmentModel;

import java.util.List;

public interface IAssignmentLoadCallbackListener {
    void onAssignmentLoadSuccess(List<AssignmentModel> assignmentModelList);
    void onAssignmentLoadFailed(String error);
}
