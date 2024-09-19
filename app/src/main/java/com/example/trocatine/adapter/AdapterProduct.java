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
import com.example.trocatine.models.Product;
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
//        holder.description.setText(product.getDescription());
            holder.value.setText(String.valueOf(product.getValue()));
//            holder.stock.setText(String.valueOf(product.getStock()));
            holder.createdAt.setText(product.getCreatedAt());
//            if (product.getFlagTroca()) {
//                holder.flagTroca.setImageResource(R.drawable.icon_arrows_trade);
//            } else {
//                holder.flagTroca.setImageResource(R.drawable.icon_bag_buy);
//            }
//            holder.flagTroca.setImageDrawable( R.drawable.icon_arrows_trade));
        };
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProductBuy.class);
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
            value = itemView.findViewById(R.id.cartProductValue);
            name = itemView.findViewById(R.id.productdName);
            createdAt = itemView.findViewById(R.id.productCreatedAt);
            image = itemView.findViewById(R.id.productImage);
        }
    }
}
