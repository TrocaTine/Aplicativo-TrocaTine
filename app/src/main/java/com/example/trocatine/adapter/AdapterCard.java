package com.example.trocatine.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.RecycleViewModels.Card;
import com.example.trocatine.util.CardUtil;

import java.util.List;

public class AdapterCard extends RecyclerView.Adapter<AdapterCard.ViewHolder>{
    private List<Card> listProduct;

    public AdapterCard(List<Card> arg) {
        this.listProduct = arg;
    }

    @NonNull
    @Override
    public AdapterCard.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //carregando o template de visualização
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cards, parent, false);
        //chamar o ViewHolder para carregar os objetos
        return new AdapterCard.ViewHolder(viewItem);

    }

    public int getItemCount() {
        return listProduct.size();
    }

    public void onBindViewHolder(@NonNull AdapterCard.ViewHolder holder, int position) {

        Card card = listProduct.get(position);

        if (card != null) {
            holder.listCardNumber.setText(card.getCardNumber());
            holder.listCardCvv.setText(card.getCvv());
            holder.listCardFullName.setText(card.getFullName());
            holder.listCardExpirationDate.setText(String.valueOf(card.getExpirationDate()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardUtil.fullName = holder.listCardFullName.getText().toString();
                CardUtil.cardNumber = holder.listCardNumber.getText().toString();
                CardUtil.expirationDate = holder.listCardExpirationDate.getText().toString();
                CardUtil.cvvNumber = holder.listCardCvv.getText().toString();
                Log.e("card", "cartao setado");
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView listCardNumber, listCardCvv, listCardFullName, listCardExpirationDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listCardCvv = itemView.findViewById(R.id.listCardCvv);
            listCardNumber = itemView.findViewById(R.id.listCardNumber);
            listCardFullName = itemView.findViewById(R.id.listCardFullName);
            listCardExpirationDate = itemView.findViewById(R.id.listCardExpirationDate);
        }
    }
}
