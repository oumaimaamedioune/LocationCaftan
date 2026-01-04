package com.example.locationcaftan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.locationcaftan.adapters.CaftanAdapter;
import com.example.locationcaftan.api.ApiService;
import com.example.locationcaftan.api.RetrofitClient;
import com.example.locationcaftan.model.Caftan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCaftansListActivity extends AppCompatActivity {

    RecyclerView recyclerCaftans;
    CaftanAdapter adapter;
    List<Caftan> caftanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_caftans_list);

        recyclerCaftans = findViewById(R.id.recyclerCaftans);
        recyclerCaftans.setLayoutManager(new LinearLayoutManager(this));

        caftanList = new ArrayList<>();
        adapter = new CaftanAdapter(this, caftanList);

        recyclerCaftans.setAdapter(adapter);

        loadCaftans();
    }

    private void loadCaftans() {

        ApiService api = RetrofitClient.getApi();

        api.getCaftans().enqueue(new Callback<List<Caftan>>() {
            @Override
            public void onResponse(Call<List<Caftan>> call, Response<List<Caftan>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    caftanList.clear();
                    caftanList.addAll(response.body());

                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(AdminCaftansListActivity.this, "Erreur de réponse", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Caftan>> call, Throwable t) {
                Toast.makeText(AdminCaftansListActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}