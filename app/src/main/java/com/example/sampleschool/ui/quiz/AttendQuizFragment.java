package com.example.sampleschool.ui.quiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleschool.Adapter.QuizOptionsAdapter;
import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.QuizModel;
import com.example.sampleschool.Model.QuizResultModel;
import com.example.sampleschool.R;
import com.example.sampleschool.databinding.FragmentAttendQuizBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class AttendQuizFragment extends Fragment {

    private FragmentAttendQuizBinding binding;

    QuizOptionsAdapter adapter;
    TextView txt_quiz_question;
    RecyclerView recycler_quiz_options;
    Button btn_quiz_next;
    LinearProgressIndicator progress_quiz;

    List<QuizModel> quizModel;
    List<String> optionSelected = new ArrayList<>();

    Boolean attempt = false;
    int count = 1, quizPos, t = 0;
    float score = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAttendQuizBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        txt_quiz_question = binding.txtQuizQuestion;
        recycler_quiz_options = binding.recyclerQuizOptions;
        btn_quiz_next = binding.btnQuizNext;
        progress_quiz = binding.progressQuiz;

        if (getArguments() != null)
            quizPos = getArguments().getInt("quizPos");

        quizModel = Common.myQuizList.get(quizPos).getQuiz();


        float attemptScore = 0;

        if (Common.myQuizList.get(quizPos).getResult() != null) {
            List<QuizResultModel> quizResultModelList = Common.myQuizList.get(quizPos).getResult();

            for (int i = 0; i < quizResultModelList.size(); i++) {
                if (quizResultModelList.get(i).getStudentUid().equals(Common.currentStudent.getUid())) {
                    attempt = true;
                    attemptScore = quizResultModelList.get(i).getScore();
                }
            }

        }

        if (attempt) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.quiz_result_layout, null);
            CircularProgressIndicator progress_result = (CircularProgressIndicator) itemView.findViewById(R.id.progress_result);
            TextView text_view_result = (TextView) itemView.findViewById(R.id.text_view_result);
            Button btn_result_ok = (Button) itemView.findViewById(R.id.btn_result_ok);
            builder.setView(itemView);
            AlertDialog dialog = builder.create();
            dialog.show();

            float aggregate = 100 * (attemptScore / quizModel.size());
            progress_result.setProgress((int) Math.round(aggregate));

            if (aggregate > 0 && aggregate <= 25)
                progress_result.setIndicatorColor(ContextCompat.getColor(getContext(), R.color.dark_red));
            if (aggregate > 25 && aggregate <= 50)
                progress_result.setIndicatorColor(ContextCompat.getColor(getContext(), R.color.orange));
            if (aggregate > 50 && aggregate <= 75)
                progress_result.setIndicatorColor(ContextCompat.getColor(getContext(), R.color.dark_blue));
            if (aggregate > 75 && aggregate <= 100)
                progress_result.setIndicatorColor(ContextCompat.getColor(getContext(), R.color.light_green));

            text_view_result.setText(new StringBuilder("" + (int) attemptScore + "/" + quizModel.size()));
            getActivity().onBackPressed();
            btn_result_ok.setOnClickListener(v -> {
                dialog.dismiss();

            });
        } else {
            txt_quiz_question.setText(new StringBuilder(count + " " + quizModel.get(count - 1).getQuestion()));

            List<String> options = quizModel.get(count - 1).getOptions();
            options.add(quizModel.get(count - 1).getCrtOption());
            adapter = new QuizOptionsAdapter(getContext(), options);
            recycler_quiz_options.setAdapter(adapter);
            recycler_quiz_options.setHasFixedSize(true);
            recycler_quiz_options.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            startTime(quizModel.get(count - 1).getTimePerQ());

            btn_quiz_next.setOnClickListener(nextQuestion);

            if (Common.myQuizList.get(quizPos).getQuiz().size() == 1)
                btn_quiz_next.setText(new StringBuilder("Finish"));


        }



        return root;
    }

    private void startTime(String timePerQ) {
        int time = Integer.parseInt(timePerQ);
        CountDownTimer countDownTimer = new CountDownTimer(TimeUnit.MINUTES.toMillis(time), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                t++;
                if (getContext() != null)
                    progress_quiz.setIndicatorColor(ContextCompat.getColor(getContext(), R.color.dark_red));
                progress_quiz.setProgress((int) (t * 100 / (TimeUnit.MINUTES.toMillis(time) / 1000)));
            }

            @Override
            public void onFinish() {
                t = 0;
                progress_quiz.setProgress(0);
                btn_quiz_next.performClick();
            }
        }.start();
    }

    View.OnClickListener nextQuestion = v -> {
        t = 0;
        progress_quiz.setProgress(0);
        if (btn_quiz_next.getText().toString().equals("Finish")) {
            optionSelected.add(count - 1, adapter.getOptionSelected());
            finish();
        } else {
            optionSelected.add(count - 1, adapter.getOptionSelected());

            if ((quizModel.size() - count) == 1)
                btn_quiz_next.setText(new StringBuilder("Finish"));
            ++count;
            List<String> options = quizModel.get(count - 1).getOptions();
            options.add(quizModel.get(count - 1).getCrtOption());

            txt_quiz_question.setText(new StringBuilder(count + " " + quizModel.get(count - 1).getQuestion()));
            adapter = new QuizOptionsAdapter(getContext(), options);
            recycler_quiz_options.setAdapter(adapter);
            recycler_quiz_options.setHasFixedSize(true);
            recycler_quiz_options.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            startTime(quizModel.get(count - 1).getTimePerQ());
        }
    };


    private void finish() {

        for (int i = 0; i < quizModel.size(); i++) {
            if (quizModel.get(i).getCrtOption().equals(optionSelected.get(i)))
                ++score;
        }
        QuizResultModel quizResultModel = new QuizResultModel(Common.currentStudent.getUid(),score);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Classes")
                .document(Common.classModel.getValue().getDocId())
                .collection("Quiz")
                .document(Common.myQuizList.get(quizPos).getQuizDocId())
                .update("result", FieldValue.arrayUnion(quizResultModel))
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Result Upload Error: " + e.getMessage(), Toast.LENGTH_SHORT).show())
                .addOnSuccessListener(unused -> {
                    showResult(score);

                });
    }

    private void showResult(float scored) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.quiz_result_layout, null);
        CircularProgressIndicator progress_result = (CircularProgressIndicator) itemView.findViewById(R.id.progress_result);
        TextView text_view_result = (TextView) itemView.findViewById(R.id.text_view_result);
        Button btn_result_ok = (Button) itemView.findViewById(R.id.btn_result_ok);
        builder.setView(itemView);
        AlertDialog dialog = builder.create();
        dialog.show();

        float aggregate =  100 *(scored / quizModel.size());
        progress_result.setProgress((int)Math.round(aggregate));

        if (aggregate > 0 && aggregate <= 25)
            progress_result.setIndicatorColor(ContextCompat.getColor(getContext(), R.color.dark_red));
        if (aggregate > 25 && aggregate <= 50)
            progress_result.setIndicatorColor(ContextCompat.getColor(getContext(), R.color.orange));
        if (aggregate > 50 && aggregate <= 75)
            progress_result.setIndicatorColor(ContextCompat.getColor(getContext(), R.color.dark_blue));
        if (aggregate > 75 && aggregate <= 100)
            progress_result.setIndicatorColor(ContextCompat.getColor(getContext(), R.color.light_green));

        text_view_result.setText(new StringBuilder("" + (int)score + "/" + quizModel.size()));

        btn_result_ok.setOnClickListener(v -> {
            dialog.dismiss();
            getActivity().onBackPressed();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}