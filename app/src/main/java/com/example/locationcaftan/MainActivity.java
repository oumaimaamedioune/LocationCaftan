package com.example.locationcaftan;

import com.example.locationcaftan.adapters.CaftanAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;
import android.widget.Button;
import android.content.Intent;

import com.example.locationcaftan.api.ApiService;
import com.example.locationcaftan.api.RetrofitClient;
import com.example.locationcaftan.model.Caftan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CaftanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerCaftans);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //  btn Admin
        Button btnAdmin = findViewById(R.id.btnAdmin);
        btnAdmin.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AdminLoginActivity.class);
            startActivity(i);
        });

        loadCaftans();
    }

    private void loadCaftans() {
        ApiService api = RetrofitClient.getRetrofit().create(ApiService.class);

        Call<List<Caftan>> call = api.getCaftans();

        call.enqueue(new Callback<List<Caftan>>() {
            @Override
            public void onResponse(Call<List<Caftan>> call, Response<List<Caftan>> response) {

                Log.d("API_RESPONSE", "Code: " + response.code());

                if (response.isSuccessful()) {
                    List<Caftan> caftanList = response.body();

                    Log.d("API_RESPONSE", "Caftans size: " + caftanList.size());

                    adapter = new CaftanAdapter(caftanList);
                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(MainActivity.this, "Erreur serveur", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Caftan>> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(MainActivity.this, "Connexion impossible", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
