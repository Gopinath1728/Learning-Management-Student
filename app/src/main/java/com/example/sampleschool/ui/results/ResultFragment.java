package com.example.sampleschool.ui.results;

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

import com.example.sampleschool.Adapter.ResultViewAdapter;
import com.example.sampleschool.Model.ResultModel;
import com.example.sampleschool.R;
import com.example.sampleschool.databinding.FragmentResultBinding;

import java.util.List;

public class ResultFragment extends Fragment {

    private ResultViewModel resultViewModel;

    private FragmentResultBinding binding;

    ResultViewAdapter adapter;

    RecyclerView recycler_results;
    TextView txt_no_result;

    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        resultViewModel = new ViewModelProvider(this).get(ResultViewModel.class);
        binding = FragmentResultBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        recycler_results = binding.recyclerResults;
        txt_no_result = binding.txtNoResult;

        resultViewModel.getResultMutableLiveData().observe(getViewLifecycleOwner(), resultModelList -> {
            if (resultModelList != null && resultModelList.size()>0)
            {
                txt_no_result.setVisibility(View.GONE);
                adapter = new ResultViewAdapter(getContext(),resultModelList);
                recycler_results.setAdapter(adapter);
            }
        });
        recycler_results.setHasFixedSize(true);
        recycler_results.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}