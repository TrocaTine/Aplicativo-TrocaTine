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
import com.example.trocatine.ui.database.DatabaseCamera;
import com.example.trocatine.ui.product.EditProduct;
import com.example.trocatine.util.ProductUtil;

import java.util.List;

public class AdapterMyProduct extends RecyclerView.Adapter<AdapterMyProduct.ViewHolder>{
    private List<Product> listProduct;

    public AdapterMyProduct(List<Product> arg){
        this.listProduct = arg;
    }
    @NonNull
    @Override
    public AdapterMyProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //carregando o template de visualização
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_fav_product,parent, false);
        //chamar o ViewHolder para carregar os objetos
        return new AdapterMyProduct.ViewHolder(viewItem);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyProduct.ViewHolder holder, int position) {

        Product product = listProduct.get(position);
        DatabaseCamera databaseCamera = new DatabaseCamera();

        if (product != null) {
            Log.e("flag troca", String.valueOf(product.getFlagTrade()));
            if (product.getFlagTrade()) {
                holder.flagTrade.setImageResource(R.drawable.icon_arrows_trade);
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
                Log.e("entrou no metodo edit", "entrou no metodo");
                Intent intent = new Intent(view.getContext(), EditProduct.class);
                Bundle dados = new Bundle();

                dados.putString("title",listProduct.get(position).getName());
                dados.putString("value",String.valueOf(listProduct.get(position).getValue()));
                dados.putString("createdAt",listProduct.get(position).getCreatedAt());
                dados.putString("description",listProduct.get(position).getDescription());
                Log.e("id do produto edit", String.valueOf(product.getid()));
                dados.putString("id",String.valueOf(product.getid()));
                ProductUtil.idProduct = String.valueOf(product.getid());
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
        ImageView image, flagTrade;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            value = itemView.findViewById(R.id.cartProductValue);
            name = itemView.findViewById(R.id.productdName);
            createdAt = itemView.findViewById(R.id.productCreatedAt);
            image = itemView.findViewById(R.id.productImage);
            flagTrade = itemView.findViewById(R.id.productFlagTroca);
        }
    }
}
