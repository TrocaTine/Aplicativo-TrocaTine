package com.example.trocatine.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.AdapterProduct;
import com.example.trocatine.RecycleViewModels.Product;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.repository.ProductRepository;
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
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Retrofit retrofit;


    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView productRv;
    private TextView initialText;
    private Dialog dialog;
    private NavController navController;
    private Button buttonCancel;
    private String token;

    private ImageButton buttonFilter;

    List<Product> listProduct = new ArrayList<>();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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


        // Configurando Recycle View

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        productRv = view.findViewById(R.id.productRv);
        buttonFilter = view.findViewById(R.id.buttonFilter);
        productRv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        AdapterProduct adapterProduct = new AdapterProduct(listProduct);
        productRv.setAdapter(adapterProduct);
        productRv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        //Configurando o filtro

        dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.card_filter);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_bg);
        dialog.setCancelable(false);
        buttonCancel = dialog.findViewById(R.id.buttonCancel);
        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        initialText = view.findViewById(R.id.initialText2);
        initialText.setText("Boa tarde, " + UserUtil.email);
        Log.e("home fragment", "email do android util"+ UserUtil.email);

        token = UserUtil.token;
        listProducts(productRv, token);
        return view;
    }


    private void listProducts(RecyclerView recyclerView, String token) {
        String API = "https://api-spring-boot-trocatine.onrender.com/";
        Log.e("token no home fragment", UserUtil.token);
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

        ProductRepository productApi = retrofit.create(ProductRepository.class);
        Call<StandardResponseDTO> call = productApi.findProductCard();
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    List<Product> products = new Gson().fromJson(new Gson().toJson(response.body().getData()), new TypeToken<List<Product>>(){}.getType());
                    recyclerView.setAdapter(new AdapterProduct(products));
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso no home fragment: " + response.code() + " - " + response.errorBody().string()+"token: "+token);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO no onFailure", throwable.getMessage());
            }
        });
    }

}