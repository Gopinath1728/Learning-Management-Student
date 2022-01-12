package com.example.sampleschool.ui.announcements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.R;
import com.example.sampleschool.databinding.FragmentAnnouncementDetailBinding;


public class AnnouncementDetailFragment extends Fragment {

    private FragmentAnnouncementDetailBinding binding;

    int position;

    TextView txt_announce_date,txt_announce_topic,txt_announce_body,txt_announce_owner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAnnouncementDetailBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        if (getArguments() != null)
        {
         position = getArguments().getInt("announcementPos");
        }

        txt_announce_date = binding.txtAnnounceDate;
        txt_announce_topic = binding.txtAnnounceTopic;
        txt_announce_body = binding.txtAnnounceBody;
        txt_announce_owner = binding.txtAnnounceOwner;

        txt_announce_date.setText(new StringBuilder(Common.announcementList.get(position).getDate()));
        txt_announce_topic.setText(new StringBuilder(Common.announcementList.get(position).getAnnouncementTitle()));
        txt_announce_body.setText(new StringBuilder(Common.announcementList.get(position).getAnnouncementBody()));
        txt_announce_owner.setText(new StringBuilder(Common.announcementList.get(position).getOwner()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}