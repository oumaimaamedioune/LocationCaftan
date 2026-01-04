package com.example.locationcaftan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    Button btnAddCaftan, btnListCaftans, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnAddCaftan = findViewById(R.id.btnAddCaftan);
        btnListCaftans = findViewById(R.id.btnListCaftans);
        btnLogout = findViewById(R.id.btnLogout);

        //ouverture du page ajout caftans
        btnAddCaftan.setOnClickListener(v -> {
            Intent i = new Intent(AdminDashboardActivity.this, AddCaftanActivity.class);
            startActivity(i);
        });

        // ouverture liste caftans
        btnListCaftans.setOnClickListener(v -> {
            Intent i = new Intent(AdminDashboardActivity.this, AdminCaftansListActivity.class);
            startActivity(i);
        });

        // Logout
        btnLogout.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("ADMIN_PREFS", MODE_PRIVATE);
            prefs.edit().putBoolean("isLoggedIn", false).apply();

            Intent i = new Intent(AdminDashboardActivity.this, AdminLoginActivity.class);
            startActivity(i);
            finish();
        });
    }
}
