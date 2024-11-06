package com.example.trocatine.ui.product.productTrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
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
import com.example.trocatine.api.repository.ProductRepository;
import com.example.trocatine.api.repository.UsersRepository;
import com.example.trocatine.api.requestDTO.product.FindQuestionsByProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.SaveFavoriteProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.SaveQuestionsProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.UnfavoriteProductRequestDTO;
import com.example.trocatine.api.responseDTO.SaveInfoProductResponseDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.responseDTO.product.FindQuestionsByProductResponseDTO;
import com.example.trocatine.api.responseDTO.product.QuestionDTO;
import com.example.trocatine.database.DatabaseCamera;
import com.example.trocatine.ui.home.HomeNavBar;
import com.example.trocatine.ui.userProfile.OthersUserProfile;
import com.example.trocatine.util.ChatUtil;
import com.example.trocatine.util.ProductUtil;
import com.example.trocatine.util.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

public class ProductTrade extends AppCompatActivity {
    private String  nameUser1,  nameUser2;
    private Uri profilePic1,  profilePic2;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://trocatine-a226a-default-rtdb.firebaseio.com/");

    int unseenMessage = 0;
    String lastMessage = "";

    private String chatKey = "1";
    ImageView buttonFavorite, questionsNull, imgLoading;
    TextView productName, productDescription, productCreatedAt, productValue;
    String id;
    RecyclerView questionRv;
    List<Question> listQuestion = new ArrayList<>();
    Button buttonNewQuestion, buttonBuyNow;

    BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_trade);

        Log.e("EMAIL DA PESSOA" , UserUtil.email);

        buttonBuyNow = findViewById(R.id.buttonTrade);
        buttonFavorite = findViewById(R.id.buttonFavorite);
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

        questionsNull = findViewById(R.id.questionsNull);
        questionsNull.setVisibility(View.INVISIBLE);

        imgLoading = findViewById(R.id.imgLoading);
        Glide.with(this).load("https://loading.io/assets/img/p/articles/quality/clamp-threshold.gif").centerCrop().into(imgLoading);

        Bundle dados = getIntent().getExtras();
        productCreatedAt.setText(dados.getString("createdAt"));
        productDescription.setText(dados.getString("description"));
        productName.setText(dados.getString("name"));
        id = dados.getString("id");
        ProductUtil.idProduct = id;
        ProductUtil.value = new BigDecimal(dados.getDouble("value"));
        databaseCamera.downloadGaleriaProduct(this, findViewById(R.id.photoProduct), id);
        buttonFavorite = findViewById(R.id.buttonFavorite);
        nameUser1 = UserUtil.userName;
        profilePic1 = UserUtil.imageProfile;
        Log.e("NAME", "nome: " + nameUser1);
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

        String API = "https://api-spring-boot-trocatine.onrender.com/";
        Log.e("find user product", "token: "+ UserUtil.token);
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

        UsersRepository productApi = retrofit.create(UsersRepository.class);
        Call<StandardResponseDTO> call = productApi.saveInfoProduct(Long.parseLong(id), UserUtil.email);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Procurando info", Toast.LENGTH_SHORT).show();
                    StandardResponseDTO result = response.body();
                    ObjectMapper objectMapper = new ObjectMapper();
                    SaveInfoProductResponseDTO resultInfo = objectMapper.convertValue(result.getData(), SaveInfoProductResponseDTO.class);

                    nameUser2 = resultInfo.getNicknameProduct();
                    nameUser1 = resultInfo.getNicknameUser();



                    Log.e("dados o resgatados", "aaaaaaaaaaaaa"+ response.body().getData().toString());
                } else {
                    try {
                        Log.e("Erro", "Resposta não foi sucesso na procura de informações: " + response.code() + " - " + response.errorBody().string()+"token: "+UserUtil.token);
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



        buttonBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica se já existe um chat entre os dois usuários
                databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean chatFound = false;
                        chatKey = ""; // Reseta a chatKey

                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            final String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                            final String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);

                            // Verifica se os usuários correspondem
                            if ((getUserOne.equals(nameUser2) && getUserTwo.equals(nameUser1)) ||
                            (getUserOne.equals(nameUser1) && getUserTwo.equals(nameUser2))) {
                                chatKey = dataSnapshot1.getKey(); // Usa a chave do chat existente
                                chatFound = true;
                                break; // Sai do loop se o chat for encontrado
                            }
                        }

                        // Se não encontrar, cria uma nova chave para o chat
                        if (!chatFound) {
                            chatKey = String.valueOf(snapshot.getChildrenCount() + 1); // Nova chave
                        }

                        // Inicie a atividade Chat após definir chatKey
                        Intent intent = new Intent(v.getContext(), Chat.class);
                        ChatUtil.name = nameUser2;
                        ChatUtil.send = nameUser1;
                        ChatUtil.chatKey = chatKey;
                        v.getContext().startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("DatabaseError", "Erro ao acessar o banco de dados: " + error.getMessage());
                    }
                });
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
    }


    public void OnClickBackActivity(View view) {
        Intent intent = new Intent(ProductTrade.this, HomeNavBar.class);
        finish();
        startActivity(intent);
    }
    public void onClickOtherUserProfile(View view) {
        Intent intent = new Intent(ProductTrade.this, OthersUserProfile.class);
        finish();
        startActivity(intent);
    }
    private void saveFavoriteProduct(String email, long id) {
        String API = "https://api-spring-boot-trocatine.onrender.com/";
        Log.e("save favorite", "token: "+ UserUtil.token);
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
        Log.e("unsave favorite", "token: "+UserUtil.token);

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
                                questionDTO.getId(),
                                questionDTO.getMessage(),
                                questionDTO.getId_user()
                        ));
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
}