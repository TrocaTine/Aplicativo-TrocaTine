package com.example.trocatine.adapter;

import android.util.Log;
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
import com.example.trocatine.api.repository.CartRepository;
import com.example.trocatine.api.requestDTO.cart.AddProductShoppingCartResquestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.database.DatabaseCamera;
import com.example.trocatine.error.ErrorDialog;
import com.example.trocatine.ui.product.ProductBuy;
import com.example.trocatine.util.CartUtil;
import com.example.trocatine.util.UserUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        holder.value.setText("R$ "+cartProduct.getValue());
        Log.e("cart util no adapter", String.valueOf(CartUtil.cartPrice));
        Log.e("cart util no adapter", String.valueOf(CartUtil.cartPrice));
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
                        deleteCartProduct(cartProduct.getIdProduct());
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
    private void deleteCartProduct(long idProduct) {
        String API = "https://api-spring-boot-trocatine.onrender.com/";
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Authorization", UserUtil.token)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CartRepository cartApi = retrofit.create(CartRepository.class);
        Call<StandardResponseDTO> call = cartApi.deleteProductFromCart(UserUtil.email, idProduct);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("dados resgatados no remove cart deu green", response.body().getData().toString());
                    StandardResponseDTO responseBody = response.body();
                } else {
                    try {
//                        ErrorDialog errorDialog = new ErrorDialog(ProductBuy.this);
//                        errorDialog.show("Erro", "Não é possível adicionar um produto que você criou no carrinho");
                        Log.e("Erro no cart", "Resposta não foi successosoo: " + response.code() + " - " + response.errorBody().string()+"token: "+UserUtil.token);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO no onFailure cart", throwable.getMessage());
            }
        });
    }
    public BigDecimal getTotal(){
        BigDecimal totalValue = BigDecimal.valueOf(0);
        for(CartProduct product : listProduct) {
            totalValue = totalValue.add(product.getValue());
        }
        return totalValue;
    }

}
