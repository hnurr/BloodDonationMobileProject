package com.example.aranankanbulundu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aranankanbulundu.MainActivity;
import com.example.aranankanbulundu.R;
import com.example.aranankanbulundu.databinding.ActivityDonarBinding;
import com.example.aranankanbulundu.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonarActivity extends AppCompatActivity {

    private ActivityDonarBinding binding;
    private String name, email, number, password, city, district, bloodgroup;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonarBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = binding.inputName.getText().toString();
                email = binding.inputEmail.getText().toString();
                number = binding.inputNumber.getText().toString();
                password = binding.inputPassword.getText().toString();
                city = binding.inputCity.getText().toString();
                district = binding.inputDistrict.getText().toString();
                bloodgroup = binding.inputBloodGroup.getText().toString();

                if (name.isEmpty()) {
                    binding.inputName.setError("Enter your name!");
                    binding.inputName.requestFocus();
                } else if (email.isEmpty()) {
                    binding.inputEmail.setError("Enter your email!");
                    binding.inputEmail.requestFocus();
                } else if (number.isEmpty()) {
                    binding.inputNumber.setError("Enter your phone number!");
                    binding.inputNumber.requestFocus();
                } else if (password.isEmpty()) {
                    binding.inputPassword.setError("Enter your password!");
                    binding.inputPassword.requestFocus();
                } else if (city.isEmpty()) {
                    binding.inputCity.setError("Enter your city!");
                    binding.inputCity.requestFocus();
                } else if (district.isEmpty()) {
                    binding.inputDistrict.setError("Enter your district!");
                    binding.inputDistrict.requestFocus();
                } else if (bloodgroup.isEmpty()) {
                    binding.inputBloodGroup.setError("Enter your blood group!");
                    binding.inputBloodGroup.requestFocus();
                } else if (password.length() < 6) {
                    binding.inputPassword.setError("Password must be at least 6 characters!");
                    binding.inputPassword.requestFocus();
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        insertData();
                                    } else {
                                        Toast.makeText(DonarActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void insertData() {
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(currentUser);

        UserModel userModel = new UserModel(name, email, password, number, city, district, bloodgroup);
        databaseReference.setValue(userModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DonarActivity.this, "Donor Registration is successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(DonarActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(DonarActivity.this, "Failed to save user data: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
