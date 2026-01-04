package com.example.locationcaftan;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import android.graphics.Color;

public class DetailsActivity extends AppCompatActivity {

    ImageView detailImage;
    TextView detailNom, detailPrix, detailTaille, detailCouleur;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // 1. Récupération des views
        detailImage = findViewById(R.id.detailImage);
        detailNom = findViewById(R.id.detailNom);
        detailPrix = findViewById(R.id.detailPrix);
        detailTaille = findViewById(R.id.detailTaille);
        detailCouleur = findViewById(R.id.detailCouleur);
        // detailStatut 7yednah 7it drna lih GONE f XML

        // 2. Récupération des données men l'Intent
        String nom = getIntent().getStringExtra("nom");
        String prix = getIntent().getStringExtra("prix");
        String taille = getIntent().getStringExtra("taille");
        String couleur = getIntent().getStringExtra("couleur");
        String image = getIntent().getStringExtra("image");
        // String statut = getIntent().getStringExtra("statut"); // Mab9ach me7tajin n'affichiwh

        /* 3. Logic de Bouton

        Button btnReserver = findViewById(R.id.btnReserver);
        if (statut == null || !statut.equalsIgnoreCase("disponible")) {
            btnReserver.setEnabled(false);
            btnReserver.setAlpha(0.3f);
        } else {
            btnReserver.setEnabled(true);
            btnReserver.setAlpha(1f);
        }

        btnReserver.setOnClickListener(v -> {
            int caftanId = getIntent().getIntExtra("caftan_id", -1);
            Intent i = new Intent(DetailsActivity.this, ReserverActivity.class);
            i.putExtra("caftan_id", caftanId);
            startActivity(i);
        });
        */

        // 4. Affichage des textes
        detailNom.setText("Nom : " + nom);
        detailPrix.setText("Prix : " + prix + " DH");
        detailTaille.setText("Taille : " + taille);
        detailCouleur.setText("Couleur : " + couleur);

        /* 5. Logic dyal l'Statut (Commentit hadchi bach maybanch)

        if (statut != null && statut.trim().equalsIgnoreCase("disponible")) {
            detailStatut.setText("Statut : Disponible");
            detailStatut.setTextColor(Color.parseColor("#00A000"));
        } else {
            detailStatut.setText("Statut : Indisponible");
            detailStatut.setTextColor(Color.parseColor("#FF0000"));
        }
        */

        // 6. Chargement de l'image avec Glide (Dima khaddam)
        if (image != null && !image.isEmpty()) {
            String fullUrl = "http://10.0.2.2:8000/storage/" + image.trim();
            Glide.with(this)
                    .load(fullUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(detailImage);
        }
    }
}