package com.example.locationcaftan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    EditText edtUser, edtPass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String u = edtUser.getText().toString().trim();
            String p = edtPass.getText().toString().trim();

            if (u.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Remplissez tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            loginAdmin(u, p);
        });
    }


    private void loginAdmin(String u, String p) {

        if (u.equals("admin") && p.equals("1234")) {

            Toast.makeText(this, "Bienvenue Admin", Toast.LENGTH_LONG).show();

            Intent i = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
            startActivity(i);
            finish();

        } else {
            Toast.makeText(this, "Identifiants incorrects", Toast.LENGTH_LONG).show();
        }
    }
}
