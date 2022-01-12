package com.example.sampleschool.ui.assignments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sampleschool.Adapter.AssignmentAdapter;
import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.AssignmentModel;
import com.example.sampleschool.R;
import com.example.sampleschool.databinding.FragmentAssignmentsBinding;

import java.util.List;


public class AssignmentsFragment extends Fragment {

    private FragmentAssignmentsBinding binding;

    private AssignmentsViewModel assignmentsViewModel;

    AssignmentAdapter adapter;
    RecyclerView recycler_assignments;
    TextView txt_no_assignments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        assignmentsViewModel = new ViewModelProvider(this).get(AssignmentsViewModel.class);
        binding = FragmentAssignmentsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        recycler_assignments = binding.recyclerAssignments;
        txt_no_assignments = binding.txtNoAssignments;

        assignmentsViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), assignmentModels -> {
            if (assignmentModels != null && assignmentModels.size()>0){
                txt_no_assignments.setVisibility(View.GONE);
                adapter = new AssignmentAdapter(getContext(), assignmentModels);
                recycler_assignments.setAdapter(adapter);
            }
        });

        recycler_assignments.setHasFixedSize(true);
        recycler_assignments.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}