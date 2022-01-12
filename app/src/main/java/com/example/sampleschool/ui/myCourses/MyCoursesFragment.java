package com.example.sampleschool.ui.myCourses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschool.Adapter.MyCoursesAdapter;
import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Common.SpacesItemDecoration;
import com.example.sampleschool.Model.SubjectModel;
import com.example.sampleschool.R;
import com.example.sampleschool.databinding.FragmentMyCoursesBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MyCoursesFragment extends Fragment {

    private MyCoursesViewModel myCoursesViewModel;
    private FragmentMyCoursesBinding binding;

    RecyclerView recycler_myCourses;
    TextView txt_no_courses;
    MyCoursesAdapter adapter;
    LayoutAnimationController layoutAnimationController;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myCoursesViewModel =
                new ViewModelProvider(this).get(MyCoursesViewModel.class);

        binding = FragmentMyCoursesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recycler_myCourses = binding.recyclerMyCourses;
        txt_no_courses = binding.txtNoCourses;

        myCoursesViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), subjectModelList -> {
            if (subjectModelList != null && subjectModelList.size()>0)
            {
                txt_no_courses.setVisibility(View.GONE);
                adapter = new MyCoursesAdapter(getContext(), subjectModelList);
                recycler_myCourses.setAdapter(adapter);
            }
        });
        myCoursesViewModel.getErrorMutable().observe(getViewLifecycleOwner(), s -> Snackbar.make(root,""+s,Snackbar.LENGTH_LONG).show());

        recycler_myCourses.setLayoutAnimation(layoutAnimationController);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter != null)
                {
                    switch (adapter.getItemViewType(position))
                    {
                        case 0: return 1;
                        case 1: return 2;
                        default:return -1;
                    }
                }
                return -1;
            }
        });
        recycler_myCourses.setLayoutManager(layoutManager);
        recycler_myCourses.addItemDecoration(new SpacesItemDecoration(8));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}