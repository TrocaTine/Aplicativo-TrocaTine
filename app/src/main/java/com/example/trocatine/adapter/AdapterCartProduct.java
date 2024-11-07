package com.example.trocatine.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.RecycleViewModels.CartProduct;
import com.example.trocatine.database.DatabaseCamera;

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
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_cart_product, parent, false);
        //chamar o ViewHolder para carregar os objetos
        return new AdapterCartProduct.ViewHolder(viewItem);

    }

    public int getItemCount() {
        return listProduct.size();
    }

    public void onBindViewHolder(@NonNull AdapterCartProduct.ViewHolder holder, int position) {
        CartProduct cartProduct = listProduct.get(position);
        DatabaseCamera databaseCamera = new DatabaseCamera();

        if (cartProduct.getQualit() == 0) {
            cartProduct.setQualit(1);
        }

        holder.name.setText(cartProduct.getTitle());
        holder.value.setText(String.valueOf(cartProduct.getValue()));
        holder.quantity.setText("Quantidade: " + cartProduct.getQualit());
        databaseCamera.downloadGaleriaProduct(holder.itemView.getContext(), holder.image, String.valueOf(cartProduct.getIdProduct()));

        holder.addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartProduct != null) {
                    int quantity = cartProduct.getQualit();
                    quantity += 1;
                    cartProduct.setQualit(quantity);
                    holder.quantity.setText("Quantidade: " + quantity); // Atualiza a quantidade no TextView
                }
            }
        });

        holder.decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartProduct != null) {
                    int quantity = cartProduct.getQualit();
                    if (quantity > 1) {
                        quantity -= 1;
                        cartProduct.setQualit(quantity);
                        holder.quantity.setText("Quantidade: " + quantity); // Atualiza a quantidade no TextView
                    } else {
                        listProduct.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                }
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, value, description;
        ImageButton addQuantity, decreaseQuantity;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            value = itemView.findViewById(R.id.cartProductValue);
            quantity = itemView.findViewById(R.id.cartQuantity);
            description = itemView.findViewById(R.id.description);
            addQuantity = itemView.findViewById(R.id.addQuantity);
            decreaseQuantity = itemView.findViewById(R.id.decreaseQuantity);
            name = itemView.findViewById(R.id.cartProductName);
            image = itemView.findViewById(R.id.cartProductImage);
        }
    }
}
