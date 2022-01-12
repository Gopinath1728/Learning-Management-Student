package com.example.sampleschool.ui.myCourses;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.sampleschool.Callback.ISubjectViewCallback;
import com.example.sampleschool.Callback.IViewClassSubjectsModelCallbackListener;
import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.SubjectModel;
import com.example.sampleschool.Model.WeekdayModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyCoursesViewModel extends ViewModel implements IViewClassSubjectsModelCallbackListener {
    private MutableLiveData<List<SubjectModel>> listMutableLiveData;
    private MutableLiveData<String> errorMutable = new MutableLiveData<>();
    private IViewClassSubjectsModelCallbackListener iViewClassSubjectsModelCallbackListener;

    public MyCoursesViewModel() {
        iViewClassSubjectsModelCallbackListener=this;
    }

    public MutableLiveData<List<SubjectModel>> getListMutableLiveData() {
        if (listMutableLiveData == null) {
            listMutableLiveData = new MutableLiveData<>();
            errorMutable = new MutableLiveData<>();
            loadSubjects();
        }
        return listMutableLiveData;
    }

    public MutableLiveData<String> getErrorMutable() {
        return errorMutable;
    }

    private void loadSubjects() {
        List<SubjectModel> tempList = new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Classes")
                .document(Common.classModel.getValue().getDocId())
                .collection("Subjects")
                .orderBy("subjectName")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.d("Classes Listen Failed", "" + error);
                        iViewClassSubjectsModelCallbackListener.onSubjectLoadFailed(error.getMessage());
                        return;
                    }
                    if (value != null && !value.isEmpty()) {
                        tempList.clear();
                        for (QueryDocumentSnapshot snapshot : value) {
                            SubjectModel subjectModel = snapshot.toObject(SubjectModel.class);
                            tempList.add(subjectModel);
                        }
                        iViewClassSubjectsModelCallbackListener.onSubjectLoadSuccess(tempList);
                    }
                });
    }

    @Override
    public void onSubjectLoadSuccess(List<SubjectModel> subjectModelList) {
        listMutableLiveData.setValue(subjectModelList);
    }

    @Override
    public void onSubjectLoadFailed(String subjectLoadError) {
        errorMutable.setValue(subjectLoadError);
    }
}