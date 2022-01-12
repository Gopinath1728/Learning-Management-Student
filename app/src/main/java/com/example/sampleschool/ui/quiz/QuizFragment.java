package com.example.sampleschool.ui.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sampleschool.Adapter.QuizViewAdapter;
import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.QuizListModel;
import com.example.sampleschool.R;
import com.example.sampleschool.databinding.FragmentQuizBinding;
import com.example.sampleschool.ui.timetable.Weekdays.Friday.FridayViewModel;

import java.util.List;

public class QuizFragment extends Fragment {

    private FragmentQuizBinding binding;

    RecyclerView recycler_quiz;
    TextView txt_no_quiz;

    QuizViewAdapter adapter;

    private QuizViewModel quizViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        binding = FragmentQuizBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        recycler_quiz = binding.recyclerQuiz;
        txt_no_quiz = binding.txtNoQuiz;

        quizViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), quizListModelList -> {
            if (quizListModelList != null && quizListModelList.size()>0)
            {
                txt_no_quiz.setVisibility(View.GONE);
                adapter = new QuizViewAdapter(getContext(), quizListModelList);
                recycler_quiz.setAdapter(adapter);
            }
        });
        recycler_quiz.setHasFixedSize(true);
        recycler_quiz.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}