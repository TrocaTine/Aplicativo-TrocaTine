package com.example.trocatine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocatine.R;
import com.example.trocatine.api.responseDTO.FindRankingTrocadinhaResponseDTO;

import java.util.List;

public class AdapterTrocadinhas extends RecyclerView.Adapter<AdapterTrocadinhas.ViewHolder> {
    private List<FindRankingTrocadinhaResponseDTO> listFindRankingTrocadinhaResponseDTO;

    public AdapterTrocadinhas(List<FindRankingTrocadinhaResponseDTO> arg) {
        this.listFindRankingTrocadinhaResponseDTO = arg;
    }

    @NonNull
    @Override
    public AdapterTrocadinhas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_trocadinha_item_rank, parent, false);
        return new AdapterTrocadinhas.ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTrocadinhas.ViewHolder holder, int position) {

        FindRankingTrocadinhaResponseDTO rankingTrocadinha = listFindRankingTrocadinhaResponseDTO.get(position);

        if (rankingTrocadinha != null) {
            holder.nickName.setText(rankingTrocadinha.getNickname());
            holder.countTrocadinha.setText(String.valueOf(rankingTrocadinha.getCountTrocadinha()));
        }
    }

    @Override
    public int getItemCount() {
        return listFindRankingTrocadinhaResponseDTO.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nickName, countTrocadinha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nickName = itemView.findViewById(R.id.nickName);
            countTrocadinha = itemView.findViewById(R.id.numTrocadinhas);
        }
    }
}
