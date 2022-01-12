package com.example.sampleschool.ui.announcements;

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

import com.example.sampleschool.Adapter.AnnouncementAdapter;
import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.AnnouncementModel;
import com.example.sampleschool.R;
import com.example.sampleschool.databinding.FragmentAnnouncementsBinding;

import java.util.List;


public class AnnouncementsFragment extends Fragment {

    private FragmentAnnouncementsBinding binding;

    private AnnouncementsViewModel announcementsViewModel;

    RecyclerView recycler_announcements;
    AnnouncementAdapter adapter;
    TextView txt_no_announcements;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        announcementsViewModel = new ViewModelProvider(this).get(AnnouncementsViewModel.class);
        binding = FragmentAnnouncementsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        txt_no_announcements = binding.txtNoAnnouncements;
        recycler_announcements = binding.recyclerAnnouncements;

        if (Common.announcementList.size()>0)
            txt_no_announcements.setVisibility(View.GONE);

        announcementsViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), announcementModelList -> {
            if (announcementModelList != null && announcementModelList.size()>0)
            {
                adapter = new AnnouncementAdapter(getContext(),announcementModelList );
                recycler_announcements.setAdapter(adapter);
            }
        });

        recycler_announcements.setHasFixedSize(true);
        recycler_announcements.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}