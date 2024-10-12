package com.example.trocatine.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.trocatine.R;
import com.example.trocatine.fragments.HomeFragment;
import com.example.trocatine.register.Register3;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    //Configurando binding
    private NavHostFragment navHostFragment; //Manipula os fragments
    private NavController navController; //Manipula opções de menu

    private BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setSelectedItemId(R.id.menu_home);

        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            navController = navHostFragment.getNavController();
            String usuario = dados.getString("usuario");
            String token = dados.getString("token");

            Bundle bundle = new Bundle();
            bundle.putString("usuario", usuario);
            bundle.putString("token", token);
            Log.e("home.class", "usuario: "+usuario+" token: "+token);
//            homeFragment.setArguments(bundle);
            navController.navigate(R.id.menu_home, bundle);

        } else {
            Log.e("HomeActivity", "Dados da Intent estão nulos.");
        }
//        initNavigation();

    }
//private void initNavigation() {
//    BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//    NavigationUI.setupWithNavController(bottomNavigationView, navController);
//}

}