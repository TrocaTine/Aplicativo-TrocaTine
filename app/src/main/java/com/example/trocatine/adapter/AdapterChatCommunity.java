package com.example.trocatine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.trocatine.R;
import com.example.trocatine.adapter.RecycleViewModels.ChatCommunity;
import com.example.trocatine.util.ChatCommunityUtil;
import com.example.trocatine.util.UserUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterChatCommunity  extends RecyclerView.Adapter<AdapterChatCommunity.MyViewHolder>{
    private List<ChatCommunity> chatLists;


    public AdapterChatCommunity(List<ChatCommunity> chatLists) {
        this.chatLists = chatLists;
    }

    @NonNull
    @Override
    public AdapterChatCommunity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterChatCommunity.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_community, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChatCommunity.MyViewHolder holder, int position) {

        ChatCommunity chatCommunity = chatLists.get(position);

        Picasso.get().load(chatCommunity.getPhoto()).into(holder.photo);
        holder.message.setText(chatCommunity.getMessage());
        holder.nickname.setText(chatCommunity.getNickname());



    }
    public void updateChat(List<ChatCommunity> chatLists){
        this.chatLists = chatLists;

    }


    @Override
    public int getItemCount() {
        return chatLists.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView photo;
        private TextView nickname, message;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.profileImage);
            nickname = itemView.findViewById(R.id.userName);
            message = itemView.findViewById(R.id.commentDescription);
        }
    }
}
