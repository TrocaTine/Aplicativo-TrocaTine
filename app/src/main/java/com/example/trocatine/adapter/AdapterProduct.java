package com.example.trocatine.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trocatine.R;
import com.example.trocatine.RecycleViewModels.Product;
import com.example.trocatine.product.ProductBuy;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder>{

    private List<Product> listProduct;

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

        if (product != null) {
            holder.name.setText(product.getName());
            holder.value.setText("R$ " + product.getValue());
            holder.createdAt.setText(product.getCreatedAt());
            holder.description.setText(product.getDescription());
            Glide.with(holder.itemView.getContext())
                    .load(product.getImageUrl())
                    .error(R.drawable.product_photo)
                    .into(holder.image);
        };
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductBuy.class);
                Bundle dados = new Bundle();

                dados.putString("name",listProduct.get(position).getName());
                dados.putDouble("value",listProduct.get(position).getValue());
                dados.putString("createdAt",listProduct.get(position).getCreatedAt());
                dados.putString("description",listProduct.get(position).getDescription());
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

            description = itemView.findViewById(R.id.description);
            value = itemView.findViewById(R.id.cartProductValue);
            name = itemView.findViewById(R.id.productdName);
            createdAt = itemView.findViewById(R.id.productCreatedAt);
            image = itemView.findViewById(R.id.productImage);
        }
    }
}
