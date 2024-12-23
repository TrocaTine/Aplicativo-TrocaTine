package com.example.trocatine.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.RecycleViewModels.Product;
import com.example.trocatine.database.DatabaseCamera;
import com.example.trocatine.ui.product.ProductBuy;
import com.example.trocatine.ui.product.productTrade.ProductTrade;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder>{

    private List<Product> listProduct;
    Intent intent;

    public AdapterProduct(List<Product> arg){
        this.listProduct = arg;
    }
    @NonNull
    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //carregando o template de visualização
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_product,parent, false);
        //chamar o ViewHolder para carregar os objetos
        return new ViewHolder(viewItem);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Product product = listProduct.get(position);
        DatabaseCamera databaseCamera = new DatabaseCamera();

        if (product != null) {
            Log.e("flag troca", String.valueOf(product.getFlagTrade()));
            if (product.getFlagTrade()) {
                holder.flagTroca.setImageResource(R.drawable.icon_arrows_trade);
                holder.value.setText("Troca");
                Log.e("é troca", "sim");
                holder.name.setText(product.getName());
                holder.createdAt.setText(product.getCreatedAt());
                holder.description.setText(product.getDescription());
                databaseCamera.downloadGaleriaProduct(holder.itemView.getContext(), holder.image, String.valueOf(product.getid()));
            } else {
                Log.e("é troca", "nao");
                holder.name.setText(product.getName());
                holder.value.setText("R$ " + product.getValue());
                holder.createdAt.setText(product.getCreatedAt());
                holder.description.setText(product.getDescription());
                Log.e("id do produto", String.valueOf(product.getid()));
                databaseCamera.downloadGaleriaProduct(holder.itemView.getContext(), holder.image, String.valueOf(product.getid()));
            }

        };
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product.getFlagTrade()){
                    intent = new Intent(view.getContext(), ProductTrade.class);
                } else {
                    intent = new Intent(view.getContext(), ProductBuy.class);
                }
                Bundle dados = new Bundle();
                dados.putString("name",listProduct.get(position).getName());
                dados.putDouble("value",listProduct.get(position).getValue());
                dados.putString("createdAt",listProduct.get(position).getCreatedAt());
                dados.putString("description",listProduct.get(position).getDescription());
                dados.putString("id",String.valueOf(listProduct.get(position).getid()));
                intent.putExtras(dados);
                view.getContext().startActivity(intent);
            }
        });
    }
    public int getItemCount() {
        return listProduct.size();
    }
    //fazer o innerclass para carregar os elementos XML vs Java
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,description,value,stock,createdAt;
        ImageView image, flagTroca;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            flagTroca = itemView.findViewById(R.id.productFlagTroca);
            description = itemView.findViewById(R.id.description);
            value = itemView.findViewById(R.id.cartProductValue);
            name = itemView.findViewById(R.id.productdName);
            createdAt = itemView.findViewById(R.id.productCreatedAt);
            image = itemView.findViewById(R.id.productImage);
        }
    }
}
