package com.example.aranankanbulundu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;  // TextView import edilmelidir
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aranankanbulundu.MainActivity;
import com.example.aranankanbulundu.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button signInButton;
    private EditText emailEditText, passwordEditText;
    private FirebaseAuth auth;
    private TextView backButton;  // "Don't have an account?" yazısı için

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Firebase Authentication instance
        auth = FirebaseAuth.getInstance();

        // UI bileşenlerini al
        signInButton = findViewById(R.id.signInButton);
        emailEditText = findViewById(R.id.inputEmail);
        passwordEditText = findViewById(R.id.inputPassword);
        backButton = findViewById(R.id.backButton);  // Don't have an account? yazısını bul

        // Giriş butonuna tıklanabilirlik ekle
        signInButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Eğer email veya şifre boşsa hata mesajı ver
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            } else {
                signInUser(email, password);
            }
        });

        // "Don't have an account?" yazısına tıklanınca kayıt olma sayfasına yönlendir
        backButton.setOnClickListener(v -> {
            // Yeni activity'ye geçiş yap
            startActivity(new Intent(LoginActivity.this, RegistionActivity.class));
        });
    }

    private void signInUser(String email, String password) {
        // Firebase ile giriş yap
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Giriş başarılı, ana sayfaya yönlendir
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    } else {
                        // Hata mesajını göster
                        Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
