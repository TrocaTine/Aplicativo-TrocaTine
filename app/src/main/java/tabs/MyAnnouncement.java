package tabs;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.example.trocatine.adapter.AdapterMyProduct;
import com.example.trocatine.adapter.RecycleViewModels.Product;
import com.example.trocatine.api.repository.ProductRepository;
import com.example.trocatine.api.requestDTO.product.FindProductByUserRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
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
 * Use the {@link MyAnnouncement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAnnouncement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView productRv;
    List<Product> listProduct = new ArrayList<>();
    TextView textNotFound;
    ImageView imgLoading, imgNotFound;


    public MyAnnouncement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAnnouncement.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAnnouncement newInstance(String param1, String param2) {
        MyAnnouncement fragment = new MyAnnouncement();
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
        View view = inflater.inflate(R.layout.fragment_my_announcement, container, false);
        productRv = view.findViewById(R.id.productRv);
        productRv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        imgNotFound = view.findViewById(R.id.imgNotFound);
        imgNotFound.setVisibility(View.INVISIBLE);
        textNotFound = view.findViewById(R.id.textNotFound);
        textNotFound.setVisibility(View.INVISIBLE);

        //Populando a recycle view
        listProductByUser(productRv, UserUtil.token);
        AdapterMyProduct adapterMyProduct = new AdapterMyProduct(listProduct);
        productRv.setAdapter(adapterMyProduct);
        productRv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        imgLoading = view.findViewById(R.id.imgLoading);
        Glide.with(this).load("https://loading.io/assets/img/p/articles/quality/clamp-threshold.gif").centerCrop().into(imgLoading);
        // Inflate the layout for this fragment
        return view;
    }
    private void listProductByUser(RecyclerView recyclerView, String token) {
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
        Call<StandardResponseDTO> call = productApi.findProductByUser(UserUtil.email);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    imgLoading.setVisibility(View.INVISIBLE);
                    List<Product> products = new Gson().fromJson(new Gson().toJson(response.body().getData()), new TypeToken<List<Product>>(){}.getType());
                    if (products.isEmpty()){
                        imgNotFound.setVisibility(View.VISIBLE);
                        textNotFound.setVisibility(View.VISIBLE);
                        Log.e("entrou no is empty", "entrou");
                    }
                    recyclerView.setAdapter(new AdapterMyProduct(products));
                } else {
                    if (response.code() == 404){
                        textNotFound.setVisibility(View.VISIBLE);
                        imgNotFound.setVisibility(View.VISIBLE);
                        imgLoading.setVisibility(View.INVISIBLE);
                    }
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