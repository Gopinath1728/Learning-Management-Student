package com.example.sampleschool.ui.attendance;

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

import com.example.sampleschool.Adapter.AttendanceListAdapter;
import com.example.sampleschool.Common.Common;
import com.example.sampleschool.R;
import com.example.sampleschool.databinding.FragmentAttendanceListBinding;

import java.util.List;
import java.util.Map;

public class AttendanceListFragment extends Fragment {

    private FragmentAttendanceListBinding binding;

    private AttendanceListViewModel attendanceListViewModel;

    RecyclerView recycler_attendance_list;
    AttendanceListAdapter adapter;
    TextView txt_no_attendance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        attendanceListViewModel = new ViewModelProvider(this).get(AttendanceListViewModel.class);
        binding = FragmentAttendanceListBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        recycler_attendance_list = binding.recyclerAttendanceList;
        txt_no_attendance = binding.txtNoAttendance;

        attendanceListViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), stringListMap -> {
            if (stringListMap != null && stringListMap.size()>0)
            {
                txt_no_attendance.setVisibility(View.GONE);
                adapter = new AttendanceListAdapter(getContext(), stringListMap);
                recycler_attendance_list.setAdapter(adapter);
            }
        });
        recycler_attendance_list.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}