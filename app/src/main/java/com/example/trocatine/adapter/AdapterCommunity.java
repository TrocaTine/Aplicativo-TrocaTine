package com.example.trocatine.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.RecycleViewModels.Community;
import com.example.trocatine.ui.product.productTrade.Chat;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCommunity extends RecyclerView.Adapter<AdapterCommunity.ViewHolder>{
    private List<Community> listCommunity;

    public AdapterCommunity(List<Community> arg) {
        this.listCommunity = arg;
    }

    @NonNull
    @Override
    public AdapterCommunity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_community_rv, parent, false);
        return new AdapterCommunity.ViewHolder(viewItem);
    }

    public int getItemCount() {
        return listCommunity.size();
    }

    public void onBindViewHolder(@NonNull AdapterCommunity.ViewHolder holder, int position) {

        Community community = listCommunity.get(position);
        boolean communityType = community.getCommunity();

        if (community != null) {
            holder.name.setText(community.getName());
            Picasso.get().load(community.getPhoto()).into(holder.image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(communityType){
                    Intent inkjhtent = new Intent();
                }else{
                    Intent intent = new Intent(v.getContext(), Chat.class);
                }

            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircleImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.communityName);
            image = itemView.findViewById(R.id.imageView25);

        }
    }
}
