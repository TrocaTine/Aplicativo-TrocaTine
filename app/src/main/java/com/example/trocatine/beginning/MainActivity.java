package com.example.trocatine.beginning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trocatine.R;
import com.example.trocatine.login.Login;
import com.example.trocatine.register.Register1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openRegister(View view) {
        Intent intent = new Intent(MainActivity.this, Register1.class);
        startActivity(intent);
    }

    public void openLogin(View view) {
        Intent intent = new Intent(MainActivity.this, Login.class);
        finish();
        startActivity(intent);
    }
}