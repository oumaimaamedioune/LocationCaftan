package com.example.locationcaftan;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.locationcaftan.api.ApiService;
import com.example.locationcaftan.api.RetrofitClient;
import com.example.locationcaftan.model.ApiResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCaftanActivity extends AppCompatActivity {

    EditText edtNom, edtPrix, edtCouleur, edtTaille;
    Spinner spinnerStatut;
    Button btnImage, btnSave;
    ImageView previewImage;

    Uri selectedImageUri = null;

    // 1. Déclaration du l'Launcher (qui utuilise ACTION_OPEN_DOCUMENT)
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // Check si l'utilisateur choisi image
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedUri = result.getData().getData();

                    if (selectedUri != null) {
                        selectedImageUri = selectedUri;

                        //  l'image reste tjrs
                        getContentResolver().takePersistableUriPermission(
                                selectedImageUri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );
                        previewImage.setImageURI(selectedImageUri);
                    }
                } else {
                    // Message
                    Toast.makeText(this, "Opération annulée ou image non sélectionnée.", Toast.LENGTH_SHORT).show();
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_caftan);

        edtNom = findViewById(R.id.edtNom);
        edtPrix = findViewById(R.id.edtPrix);
        edtCouleur = findViewById(R.id.edtCouleur);
        edtTaille = findViewById(R.id.edtTaille);
        spinnerStatut = findViewById(R.id.spinnerStatut);
        btnImage = findViewById(R.id.btnSelectImage);
        btnSave = findViewById(R.id.btnSaveCaftan);
        previewImage = findViewById(R.id.previewImage);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"disponible", "indisponible"}
        );
        spinnerStatut.setAdapter(adapter);


        btnImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            // Launcher l'Intent avec nv'Launcher
            imagePickerLauncher.launch(intent);
        });

        // enregistrer caftans
        btnSave.setOnClickListener(v -> saveCaftan());
    }



    private void saveCaftan() {

        if (selectedImageUri == null) {
            Toast.makeText(this, "Choisissez une image", Toast.LENGTH_SHORT).show();
            return;
        }

        String nom = edtNom.getText().toString().trim();
        String prix = edtPrix.getText().toString().trim();
        String couleur = edtCouleur.getText().toString().trim();
        String taille = edtTaille.getText().toString().trim();
        String statut = spinnerStatut.getSelectedItem().toString();

        if (nom.isEmpty() || prix.isEmpty() || couleur.isEmpty() || taille.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }


        File file = FileUtils.getFile(this, selectedImageUri);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imagePart =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RequestBody requestNom = RequestBody.create(MediaType.parse("text/plain"), nom);
        RequestBody requestPrix = RequestBody.create(MediaType.parse("text/plain"), prix);
        RequestBody requestCouleur = RequestBody.create(MediaType.parse("text/plain"), couleur);
        RequestBody requestTaille = RequestBody.create(MediaType.parse("text/plain"), taille);
        RequestBody requestStatut = RequestBody.create(MediaType.parse("text/plain"), statut);

        ApiService api = RetrofitClient.getApi();

        api.addCaftan(requestNom, requestPrix, requestCouleur, requestTaille, requestStatut, imagePart)
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                        if (response.isSuccessful() && response.body().isSuccess()) {
                            Toast.makeText(AddCaftanActivity.this, "Caftan ajouté !", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(AddCaftanActivity.this, "Échec de l'ajout", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Toast.makeText(AddCaftanActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}