package com.example.trocatine.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocatine.R;
import com.example.trocatine.models.CartProduct;
import com.example.trocatine.models.Product;
import com.example.trocatine.product.ProductBuy;

import java.util.List;

public class AdapterCartProduct extends RecyclerView.Adapter<AdapterCartProduct.ViewHolder> {

    private List<CartProduct> listProduct;

    public AdapterCartProduct(List<CartProduct> arg) {
        this.listProduct = arg;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //carregando o template de visualização
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_product, parent, false);
        //chamar o ViewHolder para carregar os objetos
        return new AdapterCartProduct.ViewHolder(viewItem);

    }

    public int getItemCount() {
        return listProduct.size();
    }

    public void onBindViewHolder(@NonNull AdapterCartProduct.ViewHolder holder, int position) {

        CartProduct cartProduct = listProduct.get(position);

        if (cartProduct != null) {
            holder.name.setText(cartProduct.getProduct().getName());
            holder.value.setText(String.valueOf(cartProduct.getProduct().getValue()));
            holder.quantity.setText(String.valueOf(cartProduct.getQuantity()));
        }

        holder.addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = cartProduct.getQuantity();
                quantity += 1;
                cartProduct.setQuantity(quantity);
                notifyItemChanged(position);
            }
        });
        holder.decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = cartProduct.getQuantity();
                if (quantity > 1) {
                    quantity -= 1;
                }
                cartProduct.setQuantity(quantity);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, value;
        ImageButton addQuantity, decreaseQuantity;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            value = itemView.findViewById(R.id.productValue);
            quantity = itemView.findViewById(R.id.quantity);
            addQuantity = itemView.findViewById(R.id.addQuantity);
            decreaseQuantity = itemView.findViewById(R.id.decreaseQuantity);
            name = itemView.findViewById(R.id.productdName);
            image = itemView.findViewById(R.id.productImage);
        }
    }
}
