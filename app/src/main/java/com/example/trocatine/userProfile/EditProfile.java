package com.example.trocatine.userProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trocatine.R;
import com.example.trocatine.api.repository.UsersRepository;
import com.example.trocatine.api.requestDTO.user.EditPersonalInformationRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.database.DatabaseCamera;
import com.example.trocatine.util.UserUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfile extends AppCompatActivity {
    EditText newEmail, newPhone, newUserName, newCpf, newBirthdate, newFullName;
    private static final int REQUEST_IMAGE_PICK = 1001;
    private static final int REQUEST_IMAGE_CAPTURE = 1002;
    Uri selectedImageUri;
    ImageView userImage;
    private DatabaseCamera database = new DatabaseCamera();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        newEmail = findViewById(R.id.newEmail);
        newPhone = findViewById(R.id.newPhone);
        newUserName = findViewById(R.id.newUserName);
        newFullName = findViewById(R.id.newFullName);
        newCpf = findViewById(R.id.newCpf);
        newBirthdate = findViewById(R.id.newBirthDate);

        newEmail.setText(UserUtil.email);
        newPhone.setText(UserUtil.phone);
        newUserName.setText(UserUtil.fullName);
        newCpf.setText(UserUtil.cpf);
        newFullName.setText(UserUtil.fullName);

        newBirthdate.setText(UserUtil.birthDate);

        userImage = findViewById(R.id.productImg);
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickEdit(View view) {
        finish();
        editPersonalInformation(UserUtil.token,newEmail.getText().toString(), newPhone.getText().toString(), newUserName.getText().toString(), newFullName.getText().toString(), newCpf.getText().toString(), newBirthdate.getText().toString());
        Log.d("Deu certo!!! Usuario editado", "usuario editado");
    }

    private void editPersonalInformation(String token,String newEmail, String newPhone, String newUserName, String newFullName, String newCpf, String newBirthdate) {
        String API = "https://api-spring-boot-trocatine.onrender.com/";
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Authorization", token)
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

        UsersRepository usersApi = retrofit.create(UsersRepository.class);
        String fullNameBundle = newFullName;
        String[] partes = fullNameBundle.split(" ");
        String newFirstName = partes[0];
        String newLastName = partes[partes.length - 1];
        Call<StandardResponseDTO> call = usersApi.editPersonalInformation(new EditPersonalInformationRequestDTO(UserUtil.email, newEmail, newPhone, newUserName, newFirstName, newLastName, newCpf, newBirthdate));
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("dados resgatados", response.body().getData().toString());
                    StandardResponseDTO responseBody = response.body();

                    Gson gson = new Gson();
                    EditPersonalInformationRequestDTO personalInfo = gson.fromJson(
                            gson.toJson(responseBody.getData()),
                            EditPersonalInformationRequestDTO.class);
                    UserUtil.userName = newUserName.toString();
                    UserUtil.email = newEmail.toString();
                    UserUtil.phone = newPhone.toString();
                    UserUtil.fullName = newFullName.toString();

                    UserUtil.cpf = newCpf.toString();
                    Log.e("o to string", newCpf.toString()+ newBirthdate.toString()+ newEmail.toString()+ newPhone.toString()+ newUserName.toString());
                    Log.e("userutil editado", UserUtil.fullName + " " + UserUtil.birthDate + " " + UserUtil.cpf + " " + UserUtil.email + " " + UserUtil.phone + " " + UserUtil.address+" "+UserUtil.userName);
                } else {
                    try {
                        Log.e("Erro no edit profile", "Resposta não foi successosoo: " + response.code() + " - " + response.errorBody().string() + "token: " + UserUtil.token);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }@Override
            public void onFailure(Call<StandardResponseDTO> call, Throwable throwable) {
                Log.e("ERRO no onFailure", throwable.getMessage());
            }
        });
    }

    public void onClickChangeImage(View view) {
        String[] options = {"Câmera", "Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha uma opção")
                .setItems(options, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                            break;
                        case 1:
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK);
                            break;
                    }
                });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap imageBitmap = null;
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                selectedImageUri = data.getData();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (imageBitmap != null) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                roundedBitmapDrawable.setCircular(true);
                userImage.setImageDrawable(roundedBitmapDrawable);
                uploadPhotoToFirebase(imageBitmap);
            }
        }
    }

    private void uploadPhotoToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] databyte = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.getReference("userImages")
                .child("foto" + System.currentTimeMillis() + ".jpeg" + "=" + UserUtil.email)
                .putBytes(databyte)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Upload bem-sucedido", Toast.LENGTH_SHORT).show();

                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_SHORT).show();
                                UserUtil.imageProfile = uri;
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Falha no upload: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}