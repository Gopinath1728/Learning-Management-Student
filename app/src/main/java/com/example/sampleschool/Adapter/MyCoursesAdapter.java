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

import com.example.sampleschool.Model.SubjectModel;
import com.example.sampleschool.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyCoursesAdapter extends RecyclerView.Adapter<MyCoursesAdapter.MyViewHolder> {

    Context context;
    List<SubjectModel> subjectModelList;

    public MyCoursesAdapter(Context context, List<SubjectModel> subjectModelList) {
        this.context = context;
        this.subjectModelList = subjectModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyCoursesAdapter.MyViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.subject_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_subject_item_name.setText(new StringBuilder(subjectModelList.get(position).getSubjectName()));
        Bundle bundle = new Bundle();
        bundle.putString("subjectLink",subjectModelList.get(position).getMaterialEdtLink());
        holder.txt_subject_item_name.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_webView,bundle));
    }

    @Override
    public int getItemCount() {
        return subjectModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Unbinder unbinder;

        @BindView(R.id.txt_subject_item_name)
        TextView txt_subject_item_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
