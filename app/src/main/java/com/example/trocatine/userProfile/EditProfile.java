package com.example.trocatine.userProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trocatine.R;
import com.example.trocatine.fragments.MyUserProfileFragment;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(EditProfile.this, MyUserProfileFragment.class);
        startActivity(intent);
    }
}