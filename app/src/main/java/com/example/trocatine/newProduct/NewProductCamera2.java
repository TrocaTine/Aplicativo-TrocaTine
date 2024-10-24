package com.example.trocatine.newProduct;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trocatine.R;
import com.example.trocatine.database.DatabaseCamera;
import com.example.trocatine.util.ProductUtil;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewProductCamera2 extends AppCompatActivity {
    private static final String[] REQUIRED_PERMISSIONS = {
            android.Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };

    private DatabaseCamera database = new DatabaseCamera();
    private Map<String, String> docData = new HashMap<>();

    private ExecutorService cameraExecutor;
    private androidx.camera.view.PreviewView viewFinder;
    private ImageView foto;
    private ImageButton lente;
    private ImageCapture imageCapture;
    private CameraSelector cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product_camera);
        // Iniciar objetos
        cameraExecutor = Executors.newSingleThreadExecutor();
        viewFinder = findViewById(R.id.viewFinder);
        foto = findViewById(R.id.foto);
        lente = findViewById(R.id.lente);

        // Request de permissão
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            requestPermissions();
        }

        lente.setOnClickListener(v -> {
            if(cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
            } else {
                cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;
            }
            startCamera();
        });

        // Clique para tirar a foto
        findViewById(R.id.photo_btn).setOnClickListener(v -> {
            takePhoto();
        });

        ImageButton btnGallery = findViewById(R.id.gallery);
        btnGallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            resultLauncherGaleria.launch(intent);
        });
        foto.setOnClickListener(v -> {
            foto.setVisibility(View.INVISIBLE);
            if(docData.get("url") != null) {
                foto.setVisibility(View.VISIBLE);
                database.downloadGaleria(foto, Uri.parse(docData.get("url")));
            }else{
                Toast.makeText(this, "Nenhuma imagem encontrada", Toast.LENGTH_SHORT).show();
            }
        });


    }
    // Configura a camera
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder().build();

                try {
                    cameraProvider.unbindAll();

                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(
                            this,
                            cameraSelector,
                            preview,
                            imageCapture
                    );
                } catch (Exception exc) {
                    Log.e("Log", "Camera binding failed", exc);
                }

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {
        if (imageCapture == null) {
            return;
        }

        String name = "IMG_" + System.currentTimeMillis() + ".jpg";

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/CameraSalaF");

        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(
                getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
        ).build();

        OrientationEventListener orientationEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    int rotation;

                    if (orientation >= 45 && orientation < 135) {
                        rotation = Surface.ROTATION_270;
                    } else if (orientation >= 135 && orientation <= 224) {
                        rotation = Surface.ROTATION_180;
                    } else if (orientation >= 225 && orientation <= 314) {
                        rotation = Surface.ROTATION_90;
                    } else {
                        rotation = Surface.ROTATION_0;
                    }
                    imageCapture.setTargetRotation(rotation);
                }
            }
        };
        orientationEventListener.enable();

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                foto.setVisibility(View.VISIBLE);
                foto.setImageURI(outputFileResults.getSavedUri());
                database.uploadPhoto(getApplicationContext(), foto, docData);
                Uri savedUri = outputFileResults.getSavedUri();
                if (savedUri != null) {
                    ProductUtil.imageURL = savedUri.toString();
                }
                Toast.makeText(getApplicationContext(), "FOTO TIRADA!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Toast.makeText(getApplicationContext(), "DEU RUIM!\n" + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS);
    }

    private ActivityResultLauncher<String[]> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            permissions -> {
                boolean permissionGranted = true;
                for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
                    if (Arrays.asList(REQUIRED_PERMISSIONS).contains(entry.getKey()) && !entry.getValue()) {
                        permissionGranted = false;
                        break;
                    }
                }
                if (!permissionGranted) {
                    Toast.makeText(getApplicationContext(),"Permissão NEGADA.",Toast.LENGTH_SHORT).show();
                } else {
                    startCamera();
                }
            });

    private ActivityResultLauncher<Intent> resultLauncherGaleria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                Uri imageUri = result.getData().getData();
                if (imageUri != null) {
                    foto.setVisibility(View.VISIBLE);
                    foto.setImageURI(imageUri);
                }
            });

    public void onClickNext(View view) {
        Intent intent = new Intent(NewProductCamera2.this, NewProductTrade3.class);
        startActivity(intent);
        finish();
    }

    public void OnClickBack(View view) {
        Intent intent = new Intent(NewProductCamera2.this, NewProductTrade1.class);
        startActivity(intent);
    }
}