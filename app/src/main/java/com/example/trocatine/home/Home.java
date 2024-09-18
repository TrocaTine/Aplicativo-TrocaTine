package com.example.trocatine.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.trocatine.R;
import com.example.trocatine.adapter.AdapterProduct;
import com.example.trocatine.databinding.ActivityMainBinding;
import com.example.trocatine.models.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

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