package com.example.trocatine.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.RecycleViewModels.Notification;
import com.example.trocatine.adapter.RecycleViewModels.Notification;

import java.util.List;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.ViewHolder>{
    private List<Notification> listNotification;
    Intent intent;

    public AdapterNotification(List<Notification> arg){
        this.listNotification = arg;
    }
    @NonNull
    @Override
    public AdapterNotification.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //carregando o template de visualização
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notification_card,parent, false);
        //chamar o ViewHolder para carregar os objetos
        return new AdapterNotification.ViewHolder(viewItem);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotification.ViewHolder holder, int position) {

        Notification notification = listNotification.get(position);
        holder.notificationTitle.setText(notification.getTitle());
        holder.notificationDescription.setText(notification.getDescription());
    }
    public int getItemCount() {
        return listNotification.size();
    }
    //fazer o innerclass para carregar os elementos XML vs Java
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView notificationTitle, notificationDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationDescription = itemView.findViewById(R.id.notificationDescription);
        }
    }
}
