package com.example.trocatine.beginning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.trocatine.R;

public class SplashScreen extends AppCompatActivity {
    ImageView imgSplash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imgSplash = findViewById(R.id.imgSplash);
        //Glide usado para usar gif na splash screen
        Handler handler = new Handler(); //usado para threads, usamos para definir a velocidade da splash screen na tela
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                abrirSegundaTela();
            }
        },2000);
    }

    private void abrirSegundaTela() {
        //abrir novas paginas, intenção
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent); //toda activity fica no manisfest tambem
        finish();
    }
}