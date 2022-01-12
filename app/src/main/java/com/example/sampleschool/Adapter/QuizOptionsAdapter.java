package com.example.sampleschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschool.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class QuizOptionsAdapter extends RecyclerView.Adapter<QuizOptionsAdapter.MyViewHolder> {

    Context context;
    List<String>options;
    String optionSelected="";
    private int lastCheckedPosition = -1;

    public QuizOptionsAdapter(Context context, List<String> options) {
        this.context = context;
        this.options = options;
    }

    public String getOptionSelected() {
        return optionSelected;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuizOptionsAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.quiz_option_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_option.setText(options.get(position));
        holder.radio_option.setChecked(position == lastCheckedPosition);
        if (holder.radio_option.isChecked())
            optionSelected = options.get(position);

    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.radio_option)
        RadioButton radio_option;
        @BindView(R.id.txt_option)
        TextView txt_option;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
            radio_option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(lastCheckedPosition);
                }
            });
        }
    }
}
