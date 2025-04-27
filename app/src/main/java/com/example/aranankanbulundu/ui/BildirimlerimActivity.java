package com.example.aranankanbulundu.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aranankanbulundu.R;
import com.example.aranankanbulundu.model.KanIlanModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class BildirimlerimActivity extends AppCompatActivity {

    private LinearLayout linearLayoutBildirimler;
    private TextView textViewBosMesaj;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private String kullaniciSehir, kullaniciIlce, kullaniciKanGrubu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bildirimlerim);

        linearLayoutBildirimler = findViewById(R.id.linearLayoutBildirimler);
        textViewBosMesaj = findViewById(R.id.textViewBosMesaj);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        kullaniciBilgileriniGetir();
    }

    private void kullaniciBilgileriniGetir() {
        String userId = auth.getCurrentUser().getUid();

        databaseReference.child("user").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            kullaniciSehir = snapshot.child("city").getValue(String.class);
                            kullaniciIlce = snapshot.child("district").getValue(String.class);
                            kullaniciKanGrubu = snapshot.child("bloodgroup").getValue(String.class);

                            ilanlariGetir();
                        } else {
                            textViewBosMesaj.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        textViewBosMesaj.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void ilanlariGetir() {
        databaseReference.child("kan_ilanlari")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean ilanVar = false;
                        linearLayoutBildirimler.removeAllViews(); // Önce temizle

                        for (DataSnapshot ilanSnapshot : snapshot.getChildren()) {
                            KanIlanModel ilan = ilanSnapshot.getValue(KanIlanModel.class);

                            if (ilan != null &&
                                    ilan.getIl() != null &&
                                    ilan.getIlce() != null &&
                                    ilan.getKanGrubu() != null &&
                                    ilan.getIl().equalsIgnoreCase(kullaniciSehir) &&
                                    ilan.getIlce().equalsIgnoreCase(kullaniciIlce) &&
                                    ilan.getKanGrubu().equalsIgnoreCase(kullaniciKanGrubu)) {

                                View cardView = LayoutInflater.from(BildirimlerimActivity.this)
                                        .inflate(R.layout.card_bildirim_item, linearLayoutBildirimler, false);

                                TextView textViewAdSoyad = cardView.findViewById(R.id.textViewAdSoyad);
                                TextView textViewKanGrubu = cardView.findViewById(R.id.textViewKanGrubu);
                                TextView textViewSehirIlce = cardView.findViewById(R.id.textViewSehirIlce);
                                TextView textViewTelefon = cardView.findViewById(R.id.textViewTelefon);

                                String adSoyad = (ilan.getAd() != null ? ilan.getAd() : "") + " " + (ilan.getSoyad() != null ? ilan.getSoyad() : "");
                                textViewAdSoyad.setText(!adSoyad.trim().isEmpty() ? adSoyad : "Ad Soyad Yok");
                                textViewKanGrubu.setText("Kan Grubu: " + (ilan.getKanGrubu() != null ? ilan.getKanGrubu() : "-"));
                                textViewSehirIlce.setText(ilan.getIl() + " / " + ilan.getIlce());
                                textViewTelefon.setText("Telefon: " + (ilan.getTelefon() != null ? ilan.getTelefon() : "-"));

                                linearLayoutBildirimler.addView(cardView);
                                ilanVar = true;
                            }
                        }

                        if (ilanVar) {
                            textViewBosMesaj.setVisibility(View.GONE);
                        } else {
                            textViewBosMesaj.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        textViewBosMesaj.setVisibility(View.VISIBLE);
                    }
                });
    }
}
