package com.example.aranankanbulundu.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.example.aranankanbulundu.R;
import com.example.aranankanbulundu.model.KanIlanModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KanIlanlariActivity extends AppCompatActivity {

    private LinearLayout linearLayoutKanIlanlari;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kan_ilanlari);

        linearLayoutKanIlanlari = findViewById(R.id.linearLayoutKanIlanlari);
        databaseReference = FirebaseDatabase.getInstance().getReference("kan_ilanlari");

        getIlanlar();
    }

    private void getIlanlar() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ilanSnapshot : snapshot.getChildren()) {
                    KanIlanModel ilan = ilanSnapshot.getValue(KanIlanModel.class);

                    if (ilan != null) {
                        TextView textView = new TextView(KanIlanlariActivity.this);
                        textView.setText(
                                "Ad: " + ilan.getAd() + " " + ilan.getSoyad() + "\n" +
                                        "Kan Grubu: " + ilan.getKanGrubu() + "\n" +
                                        "Hastane: " + ilan.getHastane() + "\n" +
                                        "İl/İlçe: " + ilan.getIl() + "/" + ilan.getIlce() + "\n" +
                                        "Telefon: " + ilan.getTelefon()
                        );
                        textView.setPadding(32, 32, 32, 32);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, 0, 0, 32);
                        textView.setLayoutParams(params);
                        textView.setGravity(Gravity.START);
                        linearLayoutKanIlanlari.addView(textView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Hata durumunda yapılacaklar
            }
        });
    }
}
