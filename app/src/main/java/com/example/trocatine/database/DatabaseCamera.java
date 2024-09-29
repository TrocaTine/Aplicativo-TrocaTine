package com.example.trocatine.database;

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
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class DatabaseCamera {
    // Abrir database
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    public void uploadPhoto(Context context, ImageView foto, Map<String, String> docData) {
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
                });

    }
    public void downloadGaleria(ImageView img, Uri urlFirebase) {
        img.setRotation(0);
        Glide.with(img.getContext()).asBitmap().load(urlFirebase).into(img);
    }

    public void getLastPic(ImageView img, String urlFirebase) {
        img.setRotation(0);
        storage.getReference(urlFirebase).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(img.getContext()).asBitmap().load(uri).into(img);
            }
        });
    }
}
