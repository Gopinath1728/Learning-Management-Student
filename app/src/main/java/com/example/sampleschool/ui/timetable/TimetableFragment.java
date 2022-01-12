package com.example.sampleschool.ui.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sampleschool.Adapter.PagerSelectAdapter;
import com.example.sampleschool.Common.Common;
import com.example.sampleschool.R;
import com.example.sampleschool.databinding.FragmentGalleryBinding;
import com.example.sampleschool.ui.timetable.Weekdays.Friday.FridayFragment;
import com.example.sampleschool.ui.timetable.Weekdays.Monday.MondayFragment;
import com.example.sampleschool.ui.timetable.Weekdays.Saturday.SaturdayFragment;
import com.example.sampleschool.ui.timetable.Weekdays.Thursday.ThursdayFragment;
import com.example.sampleschool.ui.timetable.Weekdays.Tuesday.TuesdayFragment;
import com.example.sampleschool.ui.timetable.Weekdays.Wednesday.WednesdayFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TimetableFragment extends Fragment {


    private FragmentGalleryBinding binding;

    TabLayout tabLayout;
    ViewPager2 viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        viewPager = (ViewPager2) root.findViewById(R.id.viewPager);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViewPager(viewPager);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(Common.classModel.getValue().getTimetable().get(position).getDayName())).attach();

    }

    private void setUpViewPager(ViewPager2 viewPager) {
        PagerSelectAdapter adapter = new PagerSelectAdapter(getChildFragmentManager(), getLifecycle());
        adapter.addFragment(new MondayFragment());
        adapter.addFragment(new TuesdayFragment());
        adapter.addFragment(new WednesdayFragment());
        adapter.addFragment(new ThursdayFragment());
        adapter.addFragment(new FridayFragment());
        adapter.addFragment(new SaturdayFragment());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}