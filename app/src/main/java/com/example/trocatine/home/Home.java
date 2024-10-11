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
        initNavigation();
        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setSelectedItemId(R.id.menu_home);


//        if (dados != null) {
//            // Obtenha o fragmento atual e passe os dados
//            HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//            if (homeFragment != null) {
//                homeFragment.setArguments(dados); // Passa os dados para o fragmento
//            }
//        }
    }

//    private void initNavigation() {
//        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_main);
//        if (navHostFragment != null) {
//            navController = navHostFragment.getNavController();
//            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//            NavigationUI.setupWithNavController(bottomNavigationView, navController);
//            Log.e("Home", "deu certo, navigation nao é nula");
//        } else {
//            Log.e("Home", "NavHostFragment entrou no nuloo");
//        }
//    }
private void initNavigation() {
    navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    navController = navHostFragment.getNavController();
    BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
    NavigationUI.setupWithNavController(bottomNavigationView, navController);
}
}