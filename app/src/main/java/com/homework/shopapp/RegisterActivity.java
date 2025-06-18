package com.homework.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.homework.shopapp.utils.SharedPrefManager;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail    = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister= findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass  = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Use SharedPrefManager to save registration data
            SharedPrefManager.getInstance(this).register(email, pass);

            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();

            // Go to Login screen
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
