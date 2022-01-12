package com.example.sampleschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sampleschool.Model.AssignmentModel;
import com.example.sampleschool.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.MyViewHolder> {

    Context context;
    List<AssignmentModel> assignmentModelList;

    public AssignmentAdapter(Context context, List<AssignmentModel> assignmentModelList) {
        this.context = context;
        this.assignmentModelList = assignmentModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.view_assignment_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_subject_name.setText(new StringBuilder(assignmentModelList.get(position).getSubject()));
        holder.txt_teacher_name.setText(new StringBuilder(assignmentModelList.get(position).getTeacherName()));
        holder.txt_topic.setText(new StringBuilder(assignmentModelList.get(position).getTopic()));
        holder.txt_body.setText(new StringBuilder(assignmentModelList.get(position).getBody()));
        holder.txt_date.setText(new StringBuilder(assignmentModelList.get(position).getDate()));

    }

    @Override
    public int getItemCount() {
        return assignmentModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        Unbinder unbinder;

        @BindView(R.id.txt_subject_name)
        TextView txt_subject_name;
        @BindView(R.id.txt_teacher_name)
        TextView txt_teacher_name;
        @BindView(R.id.txt_topic)
        TextView txt_topic;
        @BindView(R.id.txt_body)
        TextView txt_body;
        @BindView(R.id.txt_date)
        TextView txt_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
