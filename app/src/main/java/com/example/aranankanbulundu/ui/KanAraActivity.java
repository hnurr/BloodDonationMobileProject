package com.example.aranankanbulundu.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aranankanbulundu.R;
import com.example.aranankanbulundu.model.KanIlanModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.textfield.TextInputEditText;

public class KanAraActivity extends AppCompatActivity {

    TextInputEditText editTextAd, editTextSoyad, editTextKanGrubu, editTextHastane,
            editTextPoliklinik, editTextIl, editTextIlce, editTextTelefon;
    Button buttonKanAra;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kan_ara);

        // XML ID'lerine uygun atamalar
        editTextAd = findViewById(R.id.editTextAd);
        editTextSoyad = findViewById(R.id.editTextSoyad);
        editTextKanGrubu = findViewById(R.id.editTextKanGrubu);
        editTextHastane = findViewById(R.id.editTextHastane);
        editTextPoliklinik = findViewById(R.id.editTextPoliklinik);
        editTextIl = findViewById(R.id.editTextIl);
        editTextIlce = findViewById(R.id.editTextIlce);
        editTextTelefon = findViewById(R.id.editTextTelefon);
        buttonKanAra = findViewById(R.id.buttonKanAra);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("kan_ilanlari");

        buttonKanAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ilanVer();
            }
        });
    }

    private void ilanVer() {
        String ad = editTextAd.getText().toString().trim();
        String soyad = editTextSoyad.getText().toString().trim();
        String kanGrubu = editTextKanGrubu.getText().toString().trim();
        String hastane = editTextHastane.getText().toString().trim();
        String poliklinik = editTextPoliklinik.getText().toString().trim();
        String il = editTextIl.getText().toString().trim();
        String ilce = editTextIlce.getText().toString().trim();
        String telefon = editTextTelefon.getText().toString().trim();

        // Boş alan kontrolü
        if (TextUtils.isEmpty(ad)) {
            editTextAd.setError("Ad boş olamaz!");
            editTextAd.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(soyad)) {
            editTextSoyad.setError("Soyad boş olamaz!");
            editTextSoyad.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(kanGrubu)) {
            editTextKanGrubu.setError("Kan Grubu boş olamaz!");
            editTextKanGrubu.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(hastane)) {
            editTextHastane.setError("Hastane boş olamaz!");
            editTextHastane.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(il)) {
            editTextIl.setError("İl boş olamaz!");
            editTextIl.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(ilce)) {
            editTextIlce.setError("İlçe boş olamaz!");
            editTextIlce.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(telefon)) {
            editTextTelefon.setError("Telefon numarası boş olamaz!");
            editTextTelefon.requestFocus();
            return;
        }

        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "unknown_user";
        String ilanId = databaseReference.push().getKey();

        // Yeni KanIlanModel buna göre düzenlendi
        KanIlanModel ilan = new KanIlanModel(ilanId, userId, ad, soyad, kanGrubu, hastane, poliklinik, il, ilce, telefon);

        if (ilanId != null) {
            databaseReference.child(ilanId).setValue(ilan).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(KanAraActivity.this, "İlan başarıyla oluşturuldu!", Toast.LENGTH_LONG).show();
                        clearFields();
                    } else {
                        Toast.makeText(KanAraActivity.this, "İlan oluşturulamadı: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void clearFields() {
        editTextAd.setText("");
        editTextSoyad.setText("");
        editTextKanGrubu.setText("");
        editTextHastane.setText("");
        editTextPoliklinik.setText("");
        editTextIl.setText("");
        editTextIlce.setText("");
        editTextTelefon.setText("");
    }
}
