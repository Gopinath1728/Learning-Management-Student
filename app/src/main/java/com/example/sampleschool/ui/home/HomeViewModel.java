package com.example.sampleschool.ui.home;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sampleschool.Adapter.HomeFragmentAdapter;
import com.example.sampleschool.Callback.IAnnouncementCallbackListener;
import com.example.sampleschool.Callback.IAssignmentLoadCallbackListener;
import com.example.sampleschool.Callback.IAttendanceViewCallback;
import com.example.sampleschool.Callback.IQuizViewCallback;
import com.example.sampleschool.Callback.IResultViewCallbackListener;
import com.example.sampleschool.Callback.IViewClassesCallbackListener;
import com.example.sampleschool.Callback.IViewExamsCallbackListener;
import com.example.sampleschool.Callback.IWeekdayCallbackListener;
import com.example.sampleschool.Common.Common;
import com.example.sampleschool.MainActivity;
import com.example.sampleschool.Model.AnnouncementModel;
import com.example.sampleschool.Model.AssignmentModel;
import com.example.sampleschool.Model.AttendanceModel;
import com.example.sampleschool.Model.ClassModel;
import com.example.sampleschool.Model.ExaminationModel;
import com.example.sampleschool.Model.QuizListModel;
import com.example.sampleschool.Model.ResultModel;
import com.example.sampleschool.Model.StudentModel;
import com.example.sampleschool.Model.WeekdayModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel implements IViewClassesCallbackListener, IAttendanceViewCallback, IQuizViewCallback, IAnnouncementCallbackListener, IAssignmentLoadCallbackListener, IViewExamsCallbackListener, IResultViewCallbackListener {

    private MutableLiveData<ClassModel> classModelMutableLiveData;
    private IViewClassesCallbackListener classesCallbackListener;
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private MutableLiveData<List<AttendanceModel>> attendanceMutableLiveData;
    private MutableLiveData<String> attendanceErrorMessage = new MutableLiveData<>();
    private IAttendanceViewCallback attendanceViewCallback;

    private MutableLiveData<List<QuizListModel>> quizListMutableLiveData;
    private MutableLiveData<String> quizErrorMessage = new MutableLiveData<>();
    private IQuizViewCallback quizViewCallback;

    private MutableLiveData<List<AnnouncementModel>> announcementListMutable;
    private MutableLiveData<String> announcementError = new MutableLiveData<>();
    private IAnnouncementCallbackListener iAnnouncementCallbackListener;

    private MutableLiveData<List<AssignmentModel>> assignmentListMutable;
    private MutableLiveData<String> assignmentError = new MutableLiveData<>();
    private IAssignmentLoadCallbackListener iAssignmentLoadCallbackListener;

    private MutableLiveData<List<ExaminationModel>> examMutableLiveData;
    private MutableLiveData<String> examErrorMutable = new MutableLiveData<>();
    private IViewExamsCallbackListener iViewExamsCallbackListener;

    private MutableLiveData<List<ResultModel>> resultMutableLiveData;
    private MutableLiveData<String> resultErrorMutable = new MutableLiveData<>();
    private IResultViewCallbackListener resultViewCallbackListener;


    public HomeViewModel() {
        classesCallbackListener = this;
        attendanceViewCallback = this;
        quizViewCallback = this;
        iAnnouncementCallbackListener=this;
        iAssignmentLoadCallbackListener=this;
        iViewExamsCallbackListener=this;
        resultViewCallbackListener=this;
    }

    //Classes
    
    public MutableLiveData<ClassModel> getClassModelMutableLiveData() {
        if (classModelMutableLiveData == null) {
            classModelMutableLiveData = new MutableLiveData<>();
            errorMessage = new MutableLiveData<>();
            loadclass();
        }
        Common.classModel = classModelMutableLiveData;
        return classModelMutableLiveData;
    }

    private void loadclass() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Classes")
                .whereEqualTo("className", Common.currentStudent.getStudentClass())
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.d("Listen Error: ", "" + error);
                        classesCallbackListener.onClassesLoadFailed(error.getMessage());
                    }
                    if (value != null) {
                        List<ClassModel> classModelList = value.toObjects(ClassModel.class);
                        classesCallbackListener.onClassesLoadSuccess(classModelList.get(0));
                        classModelMutableLiveData.setValue(classModelList.get(0));
                    }
                });
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void onClassesLoadSuccess(ClassModel classModel) {
        classModelMutableLiveData.setValue(classModel);
    }

    @Override
    public void onClassesLoadFailed(String error) {
        errorMessage.setValue(error);
    }
    
    //Attendance

    public MutableLiveData<List<AttendanceModel>> getAttendanceMutableLiveData() {
        if (attendanceMutableLiveData == null) {
            attendanceMutableLiveData = new MutableLiveData<>();
            attendanceErrorMessage = new MutableLiveData<>();
            loadAttendance();
        }
        return attendanceMutableLiveData;
    }

    private void loadAttendance() {
        List<AttendanceModel> attendanceModelList = new ArrayList<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Students")
                .document(Common.currentStudent.getUid())
                .collection("attendance")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        attendanceViewCallback.onAttendanceLoadFailed(error.getMessage());
                    } else {
                        if (value != null) {
                            attendanceModelList.clear();
                            for (QueryDocumentSnapshot snapshot : value) {
                                AttendanceModel attendanceModel = snapshot.toObject(AttendanceModel.class);
                                attendanceModelList.add(attendanceModel);
                            }
                            if (attendanceModelList.size() > 0) {
                                Common.myAttendanceList = attendanceModelList;
                                attendanceViewCallback.onAttendanceLoadSuccess(attendanceModelList);
                            }
                        }
                    }
                });
    }

    public MutableLiveData<String> getAttendanceErrorMessage() {
        return attendanceErrorMessage;
    }

    @Override
    public void onAttendanceLoadSuccess(List<AttendanceModel> attendanceModelList) {
        attendanceMutableLiveData.setValue(attendanceModelList);
    }

    @Override
    public void onAttendanceLoadFailed(String attendanceLoadError) {
        attendanceErrorMessage.setValue(attendanceLoadError);
    }
    
    //Quiz

    public MutableLiveData<List<QuizListModel>> getQuizListMutableLiveData() {
        if (quizListMutableLiveData == null) {
            quizListMutableLiveData = new MutableLiveData<>();
            quizErrorMessage = new MutableLiveData<>();
            loadQuiz();
        }
        return quizListMutableLiveData;
    }

    private void loadQuiz() {
        List<QuizListModel> tempList = new ArrayList<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Classes")
                .document(Common.classModel.getValue().getDocId())
                .collection("Quiz")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        quizViewCallback.onQuizLoadFailed(error.getMessage());
                    } else {
                        if (value != null) {
                            tempList.clear();
                            for (QueryDocumentSnapshot snapshot : value) {
                                QuizListModel quizListModel = snapshot.toObject(QuizListModel.class);
                                tempList.add(quizListModel);
                            }
                            if (tempList.size() > 0) {
                                Common.myQuizList = tempList;
                                quizViewCallback.onQuizLoadSuccess(tempList);
                            }
                        }
                    }
                });

    }

    public MutableLiveData<String> getQuizErrorMessage() {
        return quizErrorMessage;
    }

    @Override
    public void onQuizLoadSuccess(List<QuizListModel> quizListModelList) {
        quizListMutableLiveData.setValue(quizListModelList);
    }

    @Override
    public void onQuizLoadFailed(String quizLoadError) {
        quizErrorMessage.setValue(quizLoadError);
    }
    
    //Announcement

    public MutableLiveData<List<AnnouncementModel>> getAnnouncementListMutable() {
        if (announcementListMutable == null) {
            announcementListMutable = new MutableLiveData<>();
            announcementError = new MutableLiveData<>();
            loadAnnouncements();
        }
        return announcementListMutable;
    }

    private void loadAnnouncements() {
        List<AnnouncementModel> announcementModelList = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("Announcements")
                .addSnapshotListener((value, error) -> {
                    if (error != null)
                    {
                        iAnnouncementCallbackListener.onAnnouncementLoadFailed(error.getMessage());
                    }
                    else {
                        if (value != null)
                        {
                            announcementModelList.clear();
                            for (QueryDocumentSnapshot snapshot : value)
                            {
                                AnnouncementModel announcementModel = snapshot.toObject(AnnouncementModel.class);
                                announcementModelList.add(announcementModel);
                            }
                            if (announcementModelList.size()>0)
                            {
                                Common.announcementList = announcementModelList;
                                iAnnouncementCallbackListener.onAnnouncementLoadSuccess(announcementModelList);
                            }
                        }
                    }
                });
    }

    public MutableLiveData<String> getAnnouncementError() {
        return announcementError;
    }
    
    @Override
    public void onAnnouncementLoadSuccess(List<AnnouncementModel> announcementModelList) {
        announcementListMutable.setValue(announcementModelList);
    }

    @Override
    public void onAnnouncementLoadFailed(String announcementLoadError) {
announcementError.setValue(announcementLoadError);
    }
    
    //Assignments

    public MutableLiveData<List<AssignmentModel>> getAssignmentListMutable() {
        if (assignmentListMutable == null) {
            assignmentListMutable = new MutableLiveData<>();
            assignmentError = new MutableLiveData<>();
            loadAssignments();
        }
        return assignmentListMutable;
    }

    private void loadAssignments() {
        List<AssignmentModel> tempList = new ArrayList<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Classes")
                .document(Common.classModel.getValue().getDocId())
                .collection("Assignments")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        iAssignmentLoadCallbackListener.onAssignmentLoadFailed(error.getMessage());
                    } else {
                        if (value != null) {
                            tempList.clear();
                            for (QueryDocumentSnapshot snapshot : value) {
                                AssignmentModel assignmentModel = snapshot.toObject(AssignmentModel.class);
                                tempList.add(assignmentModel);
                            }
                            if (tempList.size() > 0) {
                                Common.myAssignments = tempList;
                                iAssignmentLoadCallbackListener.onAssignmentLoadSuccess(tempList);
                            }
                        }
                    }
                });
    }

    public MutableLiveData<String> getAssignmentError() {
        return assignmentError;
    }

    @Override
    public void onAssignmentLoadSuccess(List<AssignmentModel> assignmentModelList) {
        assignmentListMutable.setValue(assignmentModelList);
    }

    @Override
    public void onAssignmentLoadFailed(String error) {
assignmentError.setValue(error);
    }

    //Exams


    public MutableLiveData<List<ExaminationModel>> getExamMutableLiveData() {
        if (examMutableLiveData == null) {
            examMutableLiveData = new MutableLiveData<>();
            examErrorMutable = new MutableLiveData<>();
            loadExams();
        }
        return examMutableLiveData;
    }

    private void loadExams() {
        List<ExaminationModel> tempList = new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Classes")
                .document(Common.classModel.getValue().getDocId())
                .collection("Examinations")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.d("Exams Listen Failed", "" + error);
                        iViewExamsCallbackListener.onExamsLoadFailed(error.getMessage());
                        return;
                    }
                    if (value != null && !value.isEmpty()) {
                        tempList.clear();
                        for (QueryDocumentSnapshot snapshot : value) {
                            ExaminationModel examinationModel = snapshot.toObject(ExaminationModel.class);
                            tempList.add(examinationModel);
                        }
                        if (tempList.size()>0)
                        {
                            Common.myExamsList = tempList;
                            iViewExamsCallbackListener.onExamsLoadSuccess(tempList);
                        }
                    }
                });
    }

    public MutableLiveData<String> getExamErrorMutable() {
        return examErrorMutable;
    }

    @Override
    public void onExamsLoadSuccess(List<ExaminationModel> examinationModels) {
        examMutableLiveData.setValue(examinationModels);
    }

    @Override
    public void onExamsLoadFailed(String examLoadError) {
        examErrorMutable.setValue(examLoadError);
    }

    //Result

    public MutableLiveData<List<ResultModel>> getResultMutableLiveData() {
        if (resultMutableLiveData == null) {
            resultMutableLiveData = new MutableLiveData<>();
            resultErrorMutable = new MutableLiveData<>();
            loadResult();
        }
        return resultMutableLiveData;
    }

    private void loadResult() {
        List<ResultModel> tempList = new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Students")
                .document(Common.currentStudent.getUid())
                .collection("Results")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.d("Result Listen Failed", "" + error);
                        resultViewCallbackListener.onResultLoadFailed(error.getMessage());
                        return;
                    }
                    if (value != null && !value.isEmpty()) {
                        tempList.clear();
                        for (QueryDocumentSnapshot snapshot : value) {
                            ResultModel resultModel = snapshot.toObject(ResultModel.class);
                            tempList.add(resultModel);
                        }
                        if (tempList.size()>0)
                        {
                            Common.myResult = tempList;
                            resultViewCallbackListener.onResultLoadSuccess(tempList);
                        }
                    }
                });
    }

    public MutableLiveData<String> getResultErrorMutable() {
        return resultErrorMutable;
    }

    @Override
    public void onResultLoadSuccess(List<ResultModel> resultModelList) {
        resultMutableLiveData.setValue(resultModelList);
    }

    @Override
    public void onResultLoadFailed(String resultLoadError) {
resultErrorMutable.setValue(resultLoadError);
    }
}