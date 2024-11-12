package com.example.trocatine.ui.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.trocatine.R;
import com.example.trocatine.adapter.AdapterQuestion;
import com.example.trocatine.adapter.RecycleViewModels.Question;
import com.example.trocatine.api.repository.CartRepository;
import com.example.trocatine.api.repository.ProductRepository;
import com.example.trocatine.api.requestDTO.cart.AddProductShoppingCartResquestDTO;
import com.example.trocatine.api.requestDTO.product.FindQuestionsByProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.FindTagByTypeRequestDTO;
import com.example.trocatine.api.requestDTO.product.SaveFavoriteProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.SaveQuestionsProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.UnfavoriteProductRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.responseDTO.product.FindQuestionsByProductResponseDTO;
import com.example.trocatine.api.responseDTO.product.QuestionDTO;
import com.example.trocatine.error.ErrorDialog;
import com.example.trocatine.ui.product.buy.Buy1;
import com.example.trocatine.database.DatabaseCamera;
import com.example.trocatine.ui.userProfile.OthersUserProfile;
import com.example.trocatine.util.ProductUtil;
import com.example.trocatine.util.UserUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.IOException;
import java.math.BigDecimal;
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

public class ProductBuy extends AppCompatActivity {
    TextView productName, productDescription, productCreatedAt, productValue;
    String id;
    ImageView buttonFavorite, imgLoading, questionsNull;
    RecyclerView questionRv;
    List<Question> listQuestion = new ArrayList<>();
    Button buttonNewQuestion;

    BottomSheetDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_buy);
        DatabaseCamera databaseCamera = new DatabaseCamera();
        questionRv = findViewById(R.id.questionRv);
        buttonNewQuestion = findViewById(R.id.newQuestion);
        AdapterQuestion adapterQuestion = new AdapterQuestion(listQuestion);
        questionRv.setAdapter(adapterQuestion);
        questionRv.setLayoutManager(new GridLayoutManager(this, 1));

        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productCreatedAt = findViewById(R.id.productCreated);
        productValue = findViewById(R.id.listCardNumber);

        imgLoading = findViewById(R.id.imgLoading);
        Glide.with(this).load("https://loading.io/assets/img/p/articles/quality/clamp-threshold.gif").centerCrop().into(imgLoading);

        questionsNull = findViewById(R.id.questionsNull);
        questionsNull.setVisibility(View.INVISIBLE);

        Bundle dados = getIntent().getExtras();
        productValue.setText("R$ "+String.valueOf(dados.getDouble("value")));
        productCreatedAt.setText(dados.getString("createdAt"));
        productDescription.setText(dados.getString("description"));
        productName.setText(dados.getString("name"));
        id = dados.getString("id");
        ProductUtil.idProduct = id;
        ProductUtil.value = new BigDecimal(dados.getDouble("value"));
        databaseCamera.downloadGaleriaProduct(this, findViewById(R.id.photoProduct), id);
        buttonFavorite = findViewById(R.id.buttonFavorite);
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verifica se é favorito
                if (buttonFavorite.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.icon_heart).getConstantState())) {{
                    buttonFavorite.setImageDrawable(getResources().getDrawable(R.drawable.icon_heart_fill));
                    saveFavoriteProduct(UserUtil.email, Long.parseLong(id));
                }}else {
                    unFavoriteProduct(UserUtil.email, Long.parseLong(id));
                    buttonFavorite.setImageDrawable(getResources().getDrawable(R.drawable.icon_heart));
                }
            }
        });
        dialog = new BottomSheetDialog(this);
        buttonNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.new_question_dialog, null, false);
                dialog.setContentView(view);
                dialog.show();
                ImageView buttonAddQuestion = dialog.findViewById(R.id.buttonAddQuestion);
                buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextInputEditText question = dialog.findViewById(R.id.question);
                        saveQuestion(UserUtil.email, UserUtil.password, Long.parseLong(id),question.getText().toString());
                    }
                });
            }
        });
        listQuestion(questionRv, Long.parseLong(id));
//        findTagType("Brinquedo");
    }


    public void OnClickBackActivity(View view) {
        finish();
    }

    public void onClickBuyNow(View view) {
        Intent intent = new Intent(ProductBuy.this, Buy1.class);
        finish();
        startActivity(intent);
    }

    public void onClickAddToCart(View view) {
        addProductShoppingCart(new AddProductShoppingCartResquestDTO(Long.parseLong(ProductUtil.idProduct), 1,UserUtil.email));
    }

    public void onClickOtherUserProfile(View view) {
        Intent intent = new Intent(ProductBuy.this, OthersUserProfile.class);
        finish();
        startActivity(intent);
    }
    private void saveFavoriteProduct(String email, long id) {
        String API = "https://api-spring-boot-trocatine.onrender.com/";
        Log.e("save favorite", "token: "+UserUtil.token);
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
        Call<StandardResponseDTO> call = productApi.saveFavoriteProduct(new SaveFavoriteProductRequestDTO(email, id));
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Adicionado aos favoritos", Toast.LENGTH_SHORT).show();
                    Log.e("dados resgatados", response.body().getData().toString());
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso no favoritar: " + response.code() + " - " + response.errorBody().string()+"token: "+UserUtil.token);
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
    private void unFavoriteProduct(String email, long id) {
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
        Call<StandardResponseDTO> call = productApi.unfavoriteProduct(new UnfavoriteProductRequestDTO(id, email));
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "removido dos favoritos", Toast.LENGTH_SHORT).show();
                    Log.e("dados resgatados", response.body().getData().toString());
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso no favoritar: " + response.code() + " - " + response.errorBody().string()+"token: "+UserUtil.token);
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
    private void saveQuestion(String email, String password, long idProduct, String message) {
        String API = "https://api-mongodb-trocatine.onrender.com";
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
        Call<StandardResponseDTO> call = productApi.saveQuestion(new SaveQuestionsProductRequestDTO(email,password, idProduct, message));
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Pergunta adicionada", Toast.LENGTH_SHORT).show();
                    Log.e("dados resgatados", response.body().getData().toString());
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso no pergunta: " + response.code() + " - " + response.errorBody().string()+"token: "+UserUtil.token);
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
    private void listQuestion(RecyclerView recyclerView, long id) {
        String API = "https://api-mongodb-trocatine.onrender.com";

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
        Call<StandardResponseDTO> call = productApi.findQuestions(new FindQuestionsByProductRequestDTO(UserUtil.email, UserUtil.password, id));
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    imgLoading.setVisibility(View.INVISIBLE);
                    // Obtendo a lista de perguntas dentro do objeto de resposta
                    FindQuestionsByProductResponseDTO result = new Gson().fromJson(
                            new Gson().toJson(response.body().getData()), FindQuestionsByProductResponseDTO.class
                    );
                    List<Question> questions = new ArrayList<>();
                    for (QuestionDTO questionDTO : result.getQuestionDTOList()) {
                        questions.add(new Question(
                                questionDTO.getEmail(),
                                questionDTO.getMessage(),
                                questionDTO.getEmail()
                        ));
                        Log.e("email da questao", questionDTO.getEmail());
                    }
                    if (questions.isEmpty()){
                        questionsNull.setVisibility(View.VISIBLE);
                    }
                    recyclerView.setAdapter(new AdapterQuestion(questions));
                    Log.e("dados resgatados", response.body().getData().toString());
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso no list products name: " + response.code() + " - " + response.errorBody().string()+"token: "+UserUtil.token);
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
    private void addProductShoppingCart(AddProductShoppingCartResquestDTO addProductShoppingCartResquestDTO) {
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
        Call<StandardResponseDTO> call = cartApi.addProduct(addProductShoppingCartResquestDTO);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("dados resgatados no cart deu green", response.body().getData().toString());
                    StandardResponseDTO responseBody = response.body();
                } else {
                    try {
                        ErrorDialog errorDialog = new ErrorDialog(ProductBuy.this);
                        errorDialog.show("Erro", "Não é possível adicionar um produto que você criou no carrinho");
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
    private void findTagType(String type) {
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

        ProductRepository productAPI = retrofit.create(ProductRepository.class);
        FindTagByTypeRequestDTO request = new FindTagByTypeRequestDTO(type);
        Call<StandardResponseDTO> call = productAPI.findTagByType(type);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("tag encontrada", "deu green ao encontrar tag");
                } else {
                    try {
                        Log.e("Erro ao encontrar tag", "Resposta não foi sucesso: " + response.code() + " - " + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO", "Falha na requisição: " + throwable.getMessage(), throwable);
            }
        });
    }
}