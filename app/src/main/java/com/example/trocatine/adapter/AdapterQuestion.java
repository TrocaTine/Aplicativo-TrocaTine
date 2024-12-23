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
import com.example.trocatine.adapter.RecycleViewModels.Question;
import com.example.trocatine.database.DatabaseCamera;


import java.util.List;

public class AdapterQuestion  extends RecyclerView.Adapter<AdapterQuestion.ViewHolder>{
    private List<Question> listQuestion;

    public AdapterQuestion(List<Question> arg){
        this.listQuestion = arg;
    }
    @NonNull
    @Override
    public AdapterQuestion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //carregando o template de visualização
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_question,parent, false);
        //chamar o ViewHolder para carregar os objetos
        return new AdapterQuestion.ViewHolder(viewItem);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterQuestion.ViewHolder holder, int position) {

        Question question = listQuestion.get(position);
        holder.userName.setText(question.getEmail());
        holder.text.setText(question.getText());
        DatabaseCamera databaseCamera = new DatabaseCamera();
        databaseCamera.downloadGaleriaUserProfile(holder.itemView.getContext(), holder.userImage, String.valueOf(question.getEmail()));

    }
    public int getItemCount() {
        return listQuestion.size();
    }
    //fazer o innerclass para carregar os elementos XML vs Java
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName, text;
        ImageView userImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.questionUserImg);
            userName = itemView.findViewById(R.id.questionUserName);
            text = itemView.findViewById(R.id.questionText);
        }
    }
}
