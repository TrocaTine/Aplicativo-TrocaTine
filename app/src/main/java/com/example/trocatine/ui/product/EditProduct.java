package com.example.trocatine.ui.product;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.trocatine.R;
import com.example.trocatine.api.models.Product;
import com.example.trocatine.api.models.TagDTO;
import com.example.trocatine.api.repository.ProductRepository;
import com.example.trocatine.api.repository.UsersRepository;
import com.example.trocatine.api.requestDTO.product.DeleteProductRequestDTO;
import com.example.trocatine.api.requestDTO.product.EditProductRequestDTO;
import com.example.trocatine.api.requestDTO.user.EditPersonalInformationRequestDTO;
import com.example.trocatine.api.responseDTO.StandardResponseDTO;
import com.example.trocatine.util.ProductUtil;
import com.example.trocatine.util.UserUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
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

public class EditProduct extends AppCompatActivity {
    EditText newTitle, newDescription, newValue;
    Spinner newCategory;
    Retrofit retrofit;
    Bundle bundle;
    private static final int REQUEST_IMAGE_PICK = 1001;
    private static final int REQUEST_IMAGE_CAPTURE = 1002;
    private Uri selectedImageUri;
    private ImageView productImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        newTitle = findViewById(R.id.newtitle);
        newDescription = findViewById(R.id.newDescription);
        newValue = findViewById(R.id.newValue);
        newCategory = findViewById(R.id.newCategory);
        productImg = findViewById(R.id.newProductImg);

        findCategory();

        bundle = getIntent().getExtras();
        newTitle.setText(bundle.getString("title"));
        newDescription.setText(bundle.getString("description"));
        newValue.setText(bundle.getString("value"));
        newCategory.setSelection(bundle.getInt("category"));

    }
    public void onClickEdit(View view) {
        List<TagDTO> tags = new ArrayList<>();
        tags.add(new TagDTO("Quality", "selectedQuality"));

        List<String> category = new ArrayList<>();
        category.add(newCategory.getSelectedItem().toString());
        finish();

        Log.e("edit product", "id: " + bundle.getString("id")+" new title: "+newTitle.getText().toString()+"new description: "+newDescription.getText().toString()+"new value: "+newValue.getText().toString()+"new category: "+newCategory.getSelectedItem().toString());
        editProduct(Long.valueOf(bundle.getString("id")), newTitle.getText().toString(), newDescription.getText().toString(), new BigDecimal(newValue.getText().toString()), bundle.getLong("stock"), false, tags, category);
    }
    private void editProduct(Long id, String title, String description, BigDecimal value, Long stock, boolean flagTrade, List<TagDTO> tags, List<String> categories) {
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
        ProductRepository productRepository = retrofit.create(ProductRepository.class);
        Call<StandardResponseDTO> call = productRepository.editProduct(new EditProductRequestDTO(id, title, description, value, stock, flagTrade, tags, categories));
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("dados resgatados", response.body().getData().toString());
                    Log.e("edit product", "deu green");

                } else {
                    try {
                        Log.e("Erro no edit product", "Resposta não foi successosoo: " + response.code() + " - " +  response.errorBody().string() + "token: " + UserUtil.token);
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
    private void findCategory() {
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
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductRepository productAPI = retrofit.create(ProductRepository.class);
        Call<StandardResponseDTO> call = productAPI.findAllCategory();
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("categria encontrada", "deu green ao encontrar categoria");
                    List<String> categories = (List<String>) response.body().getData();
                    Log.e("Categoria encontrada", "Categorias recebidas: " + categories);
                    setupUnitiesSelect(categories);

                } else {
                    try {
                        Log.e("Erro ao encontrar categoria", "Resposta não foi sucesso: " + response.code() + " - " + response.errorBody().string());
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
    private void deleteProduct(Long id) {
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
        ProductRepository productRepository = retrofit.create(ProductRepository.class);
        Call<StandardResponseDTO> call = productRepository.deleteProduct(new DeleteProductRequestDTO(id));
        call.enqueue(new Callback<StandardResponseDTO>() {
            @Override
            public void onResponse(Call<StandardResponseDTO> call, Response<StandardResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("dados resgatados", String.valueOf(response.errorBody()));
                    StandardResponseDTO responseBody = response.body();
                    Log.e("delete product", "deu green");

                } else {
                    try {
                        Log.e("Erro no delete product", "Resposta não foi successosoo: " + response.code() + " - " + response.errorBody().string() + "token: " + UserUtil.token);
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
    private void setupUnitiesSelect(List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        newCategory.setAdapter(adapter);    }

    public void onClickDelete(View view) {
        Log.e("ID DELETE", "ID: " + bundle.getString("id"));
        deleteProduct(Long.valueOf(bundle.getString("id")));
        finish();
    }

    public void onClickPhoto(View view) {
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
                roundedBitmapDrawable.setCircular(false);

                productImg.setImageDrawable(roundedBitmapDrawable);

                uploadPhotoToFirebase(imageBitmap, ProductUtil.idProduct);
            }
        }
    }

    private void uploadPhotoToFirebase(Bitmap bitmap, String id) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] databyte = baos.toByteArray();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.getReference("productImages")
                .child("foto.jpeg=" + id)
                .putBytes(databyte)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Upload bem-sucedido", Toast.LENGTH_SHORT).show();
                        Log.e("id da imagem", "Upload bem-sucedido"+id);
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_SHORT).show();
                                Log.e("Imagem", uri.toString());

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