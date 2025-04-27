package com.example.aranankanbulundu.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aranankanbulundu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import com.google.firebase.database.DatabaseError;

public class HesapAyarlariActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextNumber, editTextCity, editTextDistrict;
    private Button buttonUpdate;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hesap_ayarlari);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNumber = findViewById(R.id.editTextPhone);
        editTextCity = findViewById(R.id.editTextCity);
        editTextDistrict = findViewById(R.id.editTextDistrict);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        // Firebase bağlantı
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Kullanıcı bulunamadı!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(currentUser.getUid());

        // Mevcut bilgileri çek
        loadUserData();

        // Butona tıklayınca güncelle
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData();
            }
        });
    }

    private void loadUserData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    editTextName.setText(snapshot.child("name").getValue(String.class));
                    editTextEmail.setText(snapshot.child("email").getValue(String.class));
                    editTextNumber.setText(snapshot.child("number").getValue(String.class));
                    editTextCity.setText(snapshot.child("city").getValue(String.class));
                    editTextDistrict.setText(snapshot.child("district").getValue(String.class));
                } else {
                    Toast.makeText(HesapAyarlariActivity.this, "Bilgiler bulunamadı!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HesapAyarlariActivity.this, "Veri çekme hatası: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserData() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextNumber.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String district = editTextDistrict.getText().toString().trim();

        databaseReference.child("name").setValue(name);
        databaseReference.child("email").setValue(email);
        databaseReference.child("number").setValue(phone);
        databaseReference.child("city").setValue(city);
        databaseReference.child("district").setValue(district);

        Toast.makeText(HesapAyarlariActivity.this, "Bilgiler başarıyla güncellendi!", Toast.LENGTH_SHORT).show();
    }
}
