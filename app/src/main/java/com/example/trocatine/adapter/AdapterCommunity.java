package com.example.trocatine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.RecycleViewModels.Community;

import java.util.List;

public class AdapterCommunity extends RecyclerView.Adapter<AdapterCommunity.ViewHolder>{
    private List<Community> listCommunity;

    public AdapterCommunity(List<Community> arg) {
        this.listCommunity = arg;
    }

    @NonNull
    @Override
    public AdapterCommunity.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //carregando o template de visualização
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_community_rv, parent, false);
        //chamar o ViewHolder para carregar os objetos
        return new AdapterCommunity.ViewHolder(viewItem);

    }

    public int getItemCount() {
        return listCommunity.size();
    }

    public void onBindViewHolder(@NonNull AdapterCommunity.ViewHolder holder, int position) {

        Community community = listCommunity.get(position);

        if (community != null) {
            holder.name.setText(community.getName());
            holder.pendingMessageQuantity.setText(String.valueOf(community.getPendingMessageQuantity()));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, pendingMessageQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.communityName);
            pendingMessageQuantity = itemView.findViewById(R.id.pendingMessageQuantity);
        }
    }
}
