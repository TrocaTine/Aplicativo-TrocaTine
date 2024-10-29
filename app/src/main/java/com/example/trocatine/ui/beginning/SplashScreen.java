package com.example.trocatine.ui.beginning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.trocatine.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            Intent main = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(main);
            finish();
        } else {
            //Glide usado para usar gif na splash screen
            Handler handler = new Handler(); //usado para threads, usamos para definir a velocidade da splash screen na tela
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    abrirSegundaTela();
                }
            },2000);
        }

    }

    private void abrirSegundaTela() {
        //abrir novas paginas, intenção
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent); //toda activity fica no manisfest tambem
        finish();
    }
}