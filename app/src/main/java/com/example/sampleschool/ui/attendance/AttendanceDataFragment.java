package com.example.sampleschool.ui.attendance;

import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

import com.example.sampleschool.Adapter.AttendanceDataAdapter;
import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.AddAttendanceModel;
import com.example.sampleschool.R;
import com.example.sampleschool.databinding.FragmentAttendanceDataBinding;

import java.util.List;
import java.util.Objects;


public class AttendanceDataFragment extends Fragment {

    private FragmentAttendanceDataBinding binding;

    private AttendanceDataViewModel attendanceDataViewModel;

    RecyclerView recycler_attendance_data;
    TextView txt_no_attendance_data;
    AttendanceDataAdapter adapter;
    private String subjectName;
    int position;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        attendanceDataViewModel = new ViewModelProvider(this).get(AttendanceDataViewModel.class);
        binding = FragmentAttendanceDataBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        recycler_attendance_data = binding.recyclerAttendanceData;
        txt_no_attendance_data = binding.txtNoAttendanceData;

        if (getArguments() != null){
            subjectName = getArguments().getString("subjectName");
            position = getArguments().getInt("position");
        }
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(new StringBuilder(subjectName+" Attendance"));

        attendanceDataViewModel.getMutableLiveData(position).observe(getViewLifecycleOwner(), addAttendanceModels -> {
            if (addAttendanceModels != null && addAttendanceModels.size()>0)
            {
                txt_no_attendance_data.setVisibility(View.GONE);
                adapter = new AttendanceDataAdapter(getContext(), addAttendanceModels);
                recycler_attendance_data.setAdapter(adapter);
            }
        });

        recycler_attendance_data.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}