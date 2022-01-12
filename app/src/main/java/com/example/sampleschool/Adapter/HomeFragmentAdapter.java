package com.example.sampleschool.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.TimeModel;
import com.example.sampleschool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.MyViewHolder> {

    Context context;
    List<TimeModel> timeModelList;
    int weekpos;

    public HomeFragmentAdapter(Context context, List<TimeModel> timeModelList, int weekpos) {
        this.context = context;
        this.timeModelList = timeModelList;
        this.weekpos = weekpos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeFragmentAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.home_timetable_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_home_subject_name.setText(new StringBuilder(timeModelList.get(position).getSubjName()));
        holder.txt_home_time_from.setText(new StringBuilder(timeModelList.get(position).getTimeFrom()));
        holder.txt_home_time_to.setText(new StringBuilder(timeModelList.get(position).getTimeTo()));

        if (Common.classModel.getValue().getTeachingMode())
        {
            String currentTime = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault()).format(new Date());
            SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy hh:mm a",Locale.getDefault());
            try {
                Date timeFrom = parser.parse((new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date())) + " " + timeModelList.get(position).getTimeFrom());
                Date timeTo = parser.parse((new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date())) + " " + timeModelList.get(position).getTimeTo());
                if (parser.parse(currentTime).after(timeFrom) && parser.parse(currentTime).before(timeTo))
                {
                    holder.home_item_cardView.setCardBackgroundColor(Color.parseColor("#00b4d8"));
                    Bundle bundle = new Bundle();
                    bundle.putInt("weekPos",weekpos);
                    bundle.putInt("timPos",position);
                    bundle.putString("subjectName",timeModelList.get(position).getSubjName());
                    holder.itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_jitsi,bundle));

                }
            }catch (ParseException e) {
                e.printStackTrace();
            }
        }



    }

    @Override
    public int getItemCount() {
        return timeModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        Unbinder unbinder;

        @BindView(R.id.txt_home_subject_name)
        TextView txt_home_subject_name;
        @BindView(R.id.txt_home_time_from)
        TextView txt_home_time_from;
        @BindView(R.id.txt_home_time_to)
        TextView txt_home_time_to;
        @BindView(R.id.home_item_cardView)
        CardView home_item_cardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
