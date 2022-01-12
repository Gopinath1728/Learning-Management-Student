package com.example.sampleschool.ui.timetable.Weekdays.Friday;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sampleschool.Adapter.WeekdayAdapter;
import com.example.sampleschool.Common.SpacesItemDecoration;
import com.example.sampleschool.R;

public class FridayFragment extends Fragment {

    private FridayViewModel mViewModel;
    RecyclerView friday_recycler;
    WeekdayAdapter adapter;

    public static FridayFragment newInstance() {
        return new FridayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(FridayViewModel.class);
        View root = inflater.inflate(R.layout.friday_fragment, container, false);

        friday_recycler = (RecyclerView) root.findViewById(R.id.friday_recycler);

        mViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), timeModels -> {
            adapter = new WeekdayAdapter(getContext(),timeModels);
            friday_recycler.setAdapter(adapter);
        });

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
        friday_recycler.setLayoutManager(layoutManager);
        friday_recycler.addItemDecoration(new SpacesItemDecoration(8));

        return  root;
    }


}