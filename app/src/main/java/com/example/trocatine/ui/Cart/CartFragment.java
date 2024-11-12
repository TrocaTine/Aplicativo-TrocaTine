package com.example.trocatine.ui.Cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trocatine.R;
import com.example.trocatine.adapter.AdapterCartProduct;
import com.example.trocatine.adapter.RecycleViewModels.CartProduct;
import com.example.trocatine.adapter.RecycleViewModels.Product;
import com.example.trocatine.api.repository.CartRepository;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.ui.home.HomeFragment;
import com.example.trocatine.ui.product.buy.Buy1;
import com.example.trocatine.util.CartUtil;
import com.example.trocatine.util.UserUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView totalPriceCart, textNotFound;

    private RecyclerView cartProductRv;
    ImageView imgLoading, imgNotFound, imgBack;

    List<CartProduct> listCartProduct = new ArrayList<>();

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        totalPriceCart = view.findViewById(R.id.totalPriceCart);
        imgBack = view.findViewById(R.id.imgBack);
        Log.e("cart price", String.valueOf(CartUtil.cartPrice));
        cartProductRv = view.findViewById(R.id.cartProductRv);
        cartProductRv.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        Product product = new Product(1, 1, "nome", "descricao", 2.99, 5, "05/12/2007", true);
//        listCartProduct.add(new CartProduct(product, 1));
        AdapterCartProduct adapterCartProduct = new AdapterCartProduct(listCartProduct);
        cartProductRv.setAdapter(adapterCartProduct);
        imgNotFound = view.findViewById(R.id.imgNotFound);
        imgNotFound.setVisibility(View.INVISIBLE);
        textNotFound = view.findViewById(R.id.textNotFound);
        imgLoading = view.findViewById(R.id.imgLoading);
        Glide.with(this).load("https://loading.io/assets/img/p/articles/quality/clamp-threshold.gif").centerCrop().into(imgLoading);
        textNotFound.setVisibility(View.INVISIBLE);
        listCartProducts(cartProductRv);
        // Inflate the layout for this fragment
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.menu_home);
            }
        });
        return view;
    }
    private void listCartProducts(RecyclerView recyclerView) {
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
        Call<StandardResponseDTO> call = cartApi.findShoppingCart(UserUtil.email);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    imgLoading.setVisibility(View.INVISIBLE);
                    List<CartProduct> cartProducts = new Gson().fromJson(new Gson().toJson(response.body().getData()), new TypeToken<List<CartProduct>>(){}.getType());
//                    cartProducts.get(0).setIdProduct(Long.parseLong(ProductUtil.idProduct));
                    if (cartProducts.isEmpty()){
                        imgNotFound.setVisibility(View.VISIBLE);
                        textNotFound.setVisibility(View.VISIBLE);
                        CartUtil.cartPrice = 0;
                        Log.e("entrou no is empty", "entrou");
                    }
                    Log.e("deu green", "deu green no carrinho");
                    AdapterCartProduct adapter = new AdapterCartProduct(cartProducts);
                    recyclerView.setAdapter(adapter);
                    totalPriceCart.setText("Total: R$ " + adapter.getTotal());
                } else {
                    if (response.code() == 404){
                        textNotFound.setVisibility(View.VISIBLE);
                        imgNotFound.setVisibility(View.VISIBLE);
                        imgLoading.setVisibility(View.INVISIBLE);
                    }
                    try {
                        Log.e("Erro", "Resposta não foi sucesso no carrinho " + response.code() + " - " + response.errorBody().string()+"token: "+UserUtil.token);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO no onFailure carrinho", throwable.getMessage());
            }
        });
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(getContext(), Buy1.class);
        startActivity(intent);
    }
}