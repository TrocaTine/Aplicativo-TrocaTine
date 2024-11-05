package com.example.trocatine.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.RecycleViewModels.MessageChat;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyViewHolder>{
    private List<MessageChat> chatLists;
    private Context context;
    private String userMobile;


    public AdapterChat(List<MessageChat> chatLists, Context context, String userMobile) {
        this.chatLists = chatLists;
        this.context = context;
        this.userMobile = userMobile;
    }

    @NonNull
    @Override
    public AdapterChat.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_message, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChat.MyViewHolder holder, int position) {
        MessageChat list2 = chatLists.get(position);
//        Log.e("ADAPTER", userMobile);
//        Log.e("GETMOBILE", list2.getMobile());

        if (list2.getMobile() != null && list2.getMobile().equals(userMobile)) {
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility(View.GONE);
            holder.myMessage.setText(list2.getMessage());
        } else {
            holder.myLayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);
            holder.oppoMessage.setText(list2.getMessage());
        }

    }
    public void updateChat(List<MessageChat> chatLists){
        this.chatLists = chatLists;

    }


    @Override
    public int getItemCount() {
        return chatLists.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout oppoLayout, myLayout;
        private TextView oppoMessage, myMessage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            oppoLayout = itemView.findViewById(R.id.oppoLayout);
            myLayout = itemView.findViewById(R.id.myLayout);
            oppoMessage = itemView.findViewById(R.id.oppoMessage);
            myMessage = itemView.findViewById(R.id.myMessage);
        }
    }
}
