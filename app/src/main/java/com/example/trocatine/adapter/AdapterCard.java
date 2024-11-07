package com.example.trocatine.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.RecycleViewModels.Card;
import com.example.trocatine.ui.product.buy.Card.Buy4PurchaseMade;
import com.example.trocatine.util.CardUtil;
import com.example.trocatine.util.UserUtil;

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
            holder.listCardCvv.setText(String.valueOf(card.getCvv()));
            holder.listCardFullName.setText(UserUtil.fullName);
            holder.listCardExpirationDate.setText(String.valueOf(card.getExpirationDate()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardUtil.cardNumber = holder.listCardNumber.getText().toString();
                CardUtil.expirationDate = holder.listCardExpirationDate.getText().toString();
                CardUtil.cvvNumber = holder.listCardCvv.getText().toString();
                Intent intent = new Intent(v.getContext(), Buy4PurchaseMade.class);
                v.getContext().startActivity(intent);
                Log.e("card", "cartao setado");
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView listCardNumber, listCardCvv, listCardExpirationDate, listCardFullName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listCardFullName = itemView.findViewById(R.id.listCardFullName);
            listCardCvv = itemView.findViewById(R.id.listCardCvv);
            listCardNumber = itemView.findViewById(R.id.listCardNumber);
            listCardExpirationDate = itemView.findViewById(R.id.listCardExpirationDate);
        }
    }
}
