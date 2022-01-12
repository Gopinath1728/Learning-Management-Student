package com.example.sampleschool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sampleschool.Model.MessageModel;
import com.example.sampleschool.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MessageViewAdapter extends RecyclerView.Adapter<MessageViewAdapter.MyViewHolder> {

    Context context;
    List<MessageModel> messageModels;

    public MessageViewAdapter(Context context, List<MessageModel> messageModels) {
        this.context = context;
        this.messageModels = messageModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageViewAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.view_message_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_message_date.setText(new StringBuilder(messageModels.get(position).getDate()));
        holder.txt_message_time.setText(new StringBuilder(messageModels.get(position).getTime()));
        holder.txt_message_title.setText(new StringBuilder(messageModels.get(position).getTitle()));
        holder.txt_message_body.setText(new StringBuilder(messageModels.get(position).getBody()));
        holder.txt_message_from.setText(new StringBuilder(messageModels.get(position).getFrom()));
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.txt_message_date)
        TextView txt_message_date;
        @BindView(R.id.txt_message_time)
        TextView txt_message_time;
        @BindView(R.id.txt_message_title)
        TextView txt_message_title;
        @BindView(R.id.txt_message_body)
        TextView txt_message_body;
        @BindView(R.id.txt_message_from)
        TextView txt_message_from;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
        }
    }
}
