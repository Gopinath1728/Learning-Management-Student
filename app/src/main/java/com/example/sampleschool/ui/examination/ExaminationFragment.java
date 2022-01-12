package com.example.sampleschool.ui.examination;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sampleschool.Adapter.ExamsViewAdapter;
import com.example.sampleschool.Model.ExaminationModel;
import com.example.sampleschool.R;
import com.example.sampleschool.databinding.FragmentExaminationBinding;

import java.util.List;

public class ExaminationFragment extends Fragment {

    private ExaminationViewModel examinationViewModel;

    private FragmentExaminationBinding binding;

    RecyclerView recycler_examinations;
    TextView txt_no_exams;
    ExamsViewAdapter adapter;

    public static ExaminationFragment newInstance() {
        return new ExaminationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        examinationViewModel = new ViewModelProvider(this).get(ExaminationViewModel.class);
        binding = FragmentExaminationBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        recycler_examinations = binding.recyclerExaminations;
        txt_no_exams = binding.txtNoExams;

        examinationViewModel.getExamMutableLiveData().observe(getViewLifecycleOwner(), examinationModels -> {
            if (examinationModels != null && examinationModels.size()>0)
            {
                txt_no_exams.setVisibility(View.GONE);
                adapter = new ExamsViewAdapter(getContext(),examinationModels);
                recycler_examinations.setAdapter(adapter);
            }
        });
        recycler_examinations.setHasFixedSize(true);
        recycler_examinations.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}