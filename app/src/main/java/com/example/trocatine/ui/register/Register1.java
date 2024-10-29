package com.example.trocatine.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trocatine.R;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.api.repository.UsersRepository;
import com.example.trocatine.api.requestDTO.user.CheckingEmailAlreadyRegisteredRequestDTO;
import com.example.trocatine.ui.beginning.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register1 extends AppCompatActivity {
    private EditText email, password, phone;
    private TextView errorTextEmail, errorTextPassword, errorTextPhone;
    private ImageView backSet;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        errorTextEmail = findViewById(R.id.errorTextEmail);
        errorTextPassword = findViewById(R.id.errorTextPassword);
        errorTextPhone = findViewById(R.id.errorTextPhone);
        backSet = findViewById(R.id.backSet);
        backSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register1.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

    public void onClickNext(View view) {
        // Verificações de input do usuário
        boolean hasError = false;

        if (email.getText().toString().equals("")) {
            email.setError("Digite o e-mail necessário");
            email.requestFocus();
            hasError = true;
        }

        if (password.getText().toString().equals("")) {
            password.setError("Digite a senha necessária");
            password.requestFocus();
            hasError = true;
        }

        if (phone.getText().toString().equals("")) {
            phone.setError("Digite o telefone necessário");
            phone.requestFocus();
            hasError = true;
        } else if (phone.getText().toString().length() != 11) {
            phone.setError("O telefone precisa ter 11 dígitos (inclui DDD)");
            phone.requestFocus();
            hasError = true;
        }

        if (!hasError) {
            chamarAPI_Retrofit(email.getText().toString());
            Intent intent = new Intent(Register1.this, Register2.class);
            Bundle dados = new Bundle();
            dados.putString("email", email.getText().toString());
            dados.putString("password", password.getText().toString());
            dados.putString("phone", phone.getText().toString());

            intent.putExtras(dados);
            startActivity(intent);
        }
    }
    private void salvarLogin(String emailStr, String senhaStr) {
        //fazer o cadastro no firebase
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(emailStr, senhaStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(Register1.this, "Cadastro efetuado", Toast.LENGTH_SHORT).show();

                    //Atualizar o nome do usuario e foto
                    FirebaseUser user = auth.getCurrentUser();
                    UserProfileChangeRequest profileChangeRequest= new UserProfileChangeRequest.Builder()
                            .setDisplayName("nome provisório")
                            .setPhotoUri(Uri.parse("https://www.vagalume.com.br/bruno-mars/images/bruno-mars.webp"))
                            .build();

                    user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    private void chamarAPI_Retrofit(String email) {
        String API = "https://api-spring-boot-trocatine.onrender.com/";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersRepository userAPI = retrofit.create(UsersRepository.class);
        CheckingEmailAlreadyRegisteredRequestDTO request = new CheckingEmailAlreadyRegisteredRequestDTO(email);
        Call<StandardResponseDTO> call = userAPI.checkEmail(request);
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("Sucesso", "Email verificado: " + response.body()+ response.message()+ response.toString());
                    Object responseDTO = response.body().getData();
                    Log.e("retorno fofo: ", "Email verificado: " + responseDTO);

                } else {
                    Log.e("Erro", "Resposta não foi sucesso: " + response.code() + " - " + response.message());
                }
            }
            @Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO", throwable.getMessage());
            }
        });
    }

}