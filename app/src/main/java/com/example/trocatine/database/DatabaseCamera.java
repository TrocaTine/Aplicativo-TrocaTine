package com.example.trocatine.database;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class DatabaseCamera {
    // Abrir database
    private static FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final StorageReference storageRef = storage.getReference();

    public void uploadPhoto(Context context, ImageView foto, Map<String, String> docData, String idProduto) {
        //Converter foto
        Bitmap bitmap = ((BitmapDrawable) foto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] databyte = baos.toByteArray();

        //Abrir database
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.getReference("galeria").child("foto" + System.currentTimeMillis() + ".jpeg")
                .putBytes(databyte)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(context, "Deu grin", Toast.LENGTH_SHORT).show();
                        //obter URL imagem
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show();
                                docData.put("url", uri.toString());
                            }
                        });
                    }
                });;
    }
    public static void downloadGaleriaProduct(Context context, ImageView imageView, String id) {
        String imagePath = "foto.jpeg=" + Float.parseFloat(id);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("productImages/"+imagePath);
        storageRef.getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    if (context instanceof Activity && !((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                        Glide.with(context)
                                .load(uri)
                                .centerCrop()
                                .into(imageView);
                        Log.e("imagem do produto", "url da imagem" + uri.toString());

                    } else {
                        Log.w("downloadImageFromFirebase", "A Activity não está mais ativa, imagem não pode ser carregada.");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("downloadImageFromFirebase", "Erro ao baixar imagem: " + e.getMessage());
                });
    }
    public static void downloadGaleriaUserProfile(Context context, ImageView imageView, String email) {
        String imagePath = "foto.jpeg=" + email;
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("userImages/"+imagePath);
        storageRef.getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    if (context instanceof Activity && !((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                        Glide.with(context)
                                .load(uri)
                                .centerCrop()
                                .circleCrop()
                                .into(imageView);
                        Log.e("imagem do usuario", "url da imagem" + uri.toString());

                    } else {
                        Log.w("downloadImageFromFirebase", "A Activity não está mais ativa, imagem não pode ser carregada.");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("downloadImageFromFirebase", "Erro ao baixar imagem: " + e.getMessage());
                });
    }

}
