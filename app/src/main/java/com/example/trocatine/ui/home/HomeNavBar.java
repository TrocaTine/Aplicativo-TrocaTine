package com.example.trocatine.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.example.trocatine.R;
import com.example.trocatine.util.UserUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeNavBar extends AppCompatActivity {
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

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        initNavigation();
        navController.navigate(R.id.menu_home);

    }
protected void initNavigation() {
    BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
    NavigationUI.setupWithNavController(bottomNavigationView, navController);
}
}