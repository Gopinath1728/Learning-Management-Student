package com.example.sampleschool.ui.messages;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sampleschool.Adapter.MessageViewAdapter;
import com.example.sampleschool.Model.MessageModel;
import com.example.sampleschool.R;
import com.example.sampleschool.databinding.FragmentMessageBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MessageFragment extends Fragment {

    private MessageViewModel mViewModel;

    RecyclerView recycler_messages;
    TextView txt_no_messages;

    MessageViewAdapter adapter;

    private FragmentMessageBinding binding;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        binding = FragmentMessageBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        recycler_messages = binding.recyclerMessages;
        txt_no_messages = binding.txtNoMessages;

        mViewModel.getMessageErrorMessage().observe(getViewLifecycleOwner(), s -> Snackbar.make(root,""+s,Snackbar.LENGTH_LONG).show());

        mViewModel.getMessageMutableLiveData().observe(getViewLifecycleOwner(), messageModelList -> {
            if (messageModelList != null && messageModelList.size()>0)
            {
                txt_no_messages.setVisibility(View.GONE);
                adapter = new MessageViewAdapter(getContext(),messageModelList);
                recycler_messages.setAdapter(adapter);
            }
        });
        recycler_messages.setAdapter(adapter);
        recycler_messages.setHasFixedSize(true);
        recycler_messages.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}