package com.example.trocatine.newProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trocatine.R;
import com.example.trocatine.fragments.HomeFragment;

public class NewProduct3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product3);
    }

    public void onClickNext(View view) {
        Intent intent = new Intent(NewProduct3.this, HomeFragment.class);
        startActivity(intent);
    }

    public void OnClickBack(View view) {
        Intent intent = new Intent(NewProduct3.this, NewProductCamera.class);
        startActivity(intent);
    }
}