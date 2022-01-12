package com.example.sampleschool.Callback;

import com.example.sampleschool.Model.QuizListModel;

import java.util.List;

public interface IQuizViewCallback {
    void onQuizLoadSuccess(List<QuizListModel> quizListModelList);
    void onQuizLoadFailed(String quizLoadError);
}
