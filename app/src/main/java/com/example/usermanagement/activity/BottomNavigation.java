package com.example.usermanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.usermanagement.R;
import com.example.usermanagement.fragment.DashBoard;
import com.example.usermanagement.fragment.Profile;
import com.example.usermanagement.fragment.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        //getSupportActionBar().setTitle("Bottom Navigation");
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        fragmentsTransactions(new DashBoard());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.dashboard:
                fragment = new DashBoard();
                break;
            case R.id.users:
                fragment = new Users();
                break;
            case R.id.profile:
                fragment = new Profile();
                break;
        }
        if (fragment != null){
            fragmentsTransactions(fragment);
        }
        return true;
    }

    void fragmentsTransactions(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.relative_layout,fragment).commit();
    }
}