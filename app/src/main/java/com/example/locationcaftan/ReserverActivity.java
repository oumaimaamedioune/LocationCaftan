package com.example.locationcaftan;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.locationcaftan.api.ApiService;
import com.example.locationcaftan.model.ReservationRequest;
import com.example.locationcaftan.model.ApiResponse;
import com.example.locationcaftan.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReserverActivity extends AppCompatActivity {

    EditText edtNom, edtTel;
    Button btnValider;
    int caftanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserver);

        edtNom = findViewById(R.id.edtNom);
        edtTel = findViewById(R.id.edtTel);
        btnValider = findViewById(R.id.btnValider);

        // DetailsActivity du caftan_id
        caftanId = getIntent().getIntExtra("caftan_id", -1);

        btnValider.setOnClickListener(v -> {



            String nom = edtNom.getText().toString();
            String tel = edtTel.getText().toString();

            if(nom.isEmpty() || tel.isEmpty()){
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            envoyerReservation(nom, tel);
        });
    }

    private void envoyerReservation(String nom, String tel){

        ApiService api = RetrofitClient.getApi();

        ReservationRequest req = new ReservationRequest(nom, tel, caftanId);

        api.envoyerReservation(req).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if(response.isSuccessful()){
                    Toast.makeText(ReserverActivity.this, "Réservation enregistrée !", Toast.LENGTH_LONG).show();
                    finish();
                } else {

                    // serveur Laravel
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(
                                ReserverActivity.this,
                                "Erreur serveur : " + errorBody,
                                Toast.LENGTH_LONG
                        ).show();
                    } catch (Exception e) {
                        Toast.makeText(
                                ReserverActivity.this,
                                "Erreur inconnue !",
                                Toast.LENGTH_LONG
                        ).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(ReserverActivity.this, "Connexion échouée : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
