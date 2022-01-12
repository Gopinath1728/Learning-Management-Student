package com.example.sampleschool.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschool.Model.AnnouncementModel;
import com.example.sampleschool.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder> {

    Context context;
    List<AnnouncementModel> announcementModelList;

    public AnnouncementAdapter(Context context, List<AnnouncementModel> announcementModelList) {
        this.context = context;
        this.announcementModelList = announcementModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnnouncementAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.announcement_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_announcement_date.setText(new StringBuilder(announcementModelList.get(position).getDate()));
        holder.txt_announcement_topic.setText(new StringBuilder(announcementModelList.get(position).getAnnouncementTitle()));
        holder.txt_announcement_owner.setText(new StringBuilder(announcementModelList.get(position).getOwner()));

        Bundle bundle = new Bundle();
        bundle.putInt("announcementPos",position);
        holder.itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_announcement_detail,bundle));
    }

    @Override
    public int getItemCount() {
        return announcementModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.txt_announcement_date)
        TextView txt_announcement_date;
        @BindView(R.id.txt_announcement_topic)
        TextView txt_announcement_topic;
        @BindView(R.id.txt_announcement_owner)
        TextView txt_announcement_owner;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
