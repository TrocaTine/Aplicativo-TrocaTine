package com.example.trocatine.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trocatine.R;
import com.example.trocatine.adapter.AdapterProduct;
import com.example.trocatine.adapter.ViewPagerAdapter;
import com.example.trocatine.api.models.RecycleViewModels.Product;
import com.example.trocatine.api.repository.ProductRepository;
import com.example.trocatine.api.requestDTO.product.FindProductFavoriteRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.ui.database.DatabaseCamera;
import com.example.trocatine.ui.userProfile.EditProfile;
import com.example.trocatine.util.UserUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
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
 * Use the {@link MyUserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyUserProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TextView userName, userEmail, userPhone, userAdress, userCpf, userBirthDate;
    private ImageButton buttonEditProfile;
    private ImageView backSet;
    private ImageView userImg;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseCamera databaseCamera;

    public MyUserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyUserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyUserProfileFragment newInstance(String param1, String param2) {
        MyUserProfileFragment fragment = new MyUserProfileFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_user_profile, container, false);
        backSet = view.findViewById(R.id.backSet);

        userEmail = view.findViewById(R.id.userEmail);
        userName = view.findViewById(R.id.userName);
        userCpf = view.findViewById(R.id.userCpf);
        userPhone = view.findViewById(R.id.userValue);
        userAdress = view.findViewById(R.id.userAddress);
        userBirthDate = view.findViewById(R.id.userBirthDate);

        userEmail.setText(UserUtil.email);
        userBirthDate.setText(UserUtil.birthDate);
        userAdress.setText(UserUtil.address);
        userPhone.setText(UserUtil.phone);
        userName.setText(UserUtil.fullName);
        userCpf.setText(UserUtil.cpf);

        userImg = view.findViewById(R.id.userImg);
        if (UserUtil.imageProfile != null){
            Glide.with(userImg.getContext())
                    .asBitmap()
                    .load(UserUtil.imageProfile)
                    .circleCrop()
                    .into(userImg);
        } else {
            Log.e("userprofile image profile", "null");
        }

        Log.e("userprofile", UserUtil.email+" "+UserUtil.fullName+" "+UserUtil.address+" "+UserUtil.birthDate+" "+UserUtil.cpf+" "+UserUtil.phone);
        buttonEditProfile = view.findViewById(R.id.openEditProfile);

        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditProfile.class);
                startActivity(intent);
            }
        });
        backSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), HomeFragment.class);
                startActivity(intent);
            }
        });
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager2);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }
    private void listFavoriteProduct(RecyclerView recyclerView, String token) {
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

        ProductRepository productApi = retrofit.create(ProductRepository.class);
        Call<StandardResponseDTO> call = productApi.findProductFavorite(new FindProductFavoriteRequestDTO(UserUtil.email));
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    List<Product> products = new Gson().fromJson(new Gson().toJson(response.body().getData()), new TypeToken<List<Product>>(){}.getType());
                    recyclerView.setAdapter(new AdapterProduct(products));
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso no list products name: " + response.code() + " - " + response.errorBody().string()+"token: "+token);
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