package com.example.sampleschool.ui.assignments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.AssignmentModel;

import java.util.List;

public class AssignmentsViewModel extends ViewModel {
    private MutableLiveData<List<AssignmentModel>> mutableLiveData;

    public AssignmentsViewModel() {
    }

    public MutableLiveData<List<AssignmentModel>> getMutableLiveData() {
        if (mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(Common.myAssignments);
        return mutableLiveData;
    }
}
