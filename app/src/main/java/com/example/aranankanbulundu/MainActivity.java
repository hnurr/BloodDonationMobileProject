package com.example.aranankanbulundu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aranankanbulundu.ui.BildirimlerimActivity;
import com.example.aranankanbulundu.ui.HesapAyarlariActivity;
import com.example.aranankanbulundu.ui.KanAraActivity;
import com.example.aranankanbulundu.ui.KanIlanlariActivity; // bunu da ekliyoruz

public class MainActivity extends AppCompatActivity {

    CardView cardView1; // Kan İlanları kartı için

    CardView cardView2;
    CardView cardView3; // 3 numaralı kart için
    CardView cardView4; // Kan Ara kartı için

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Kartları tanımlıyoruz
        cardView1 = findViewById(R.id.cardView1); // kan ilanları kartı
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3); // 3 numaralı kart
        cardView4 = findViewById(R.id.cardView4); // kan ara kartı

        // Kan İlanları kartı tıklama
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // KanIlanlariActivity açılıyor
                Intent intent = new Intent(MainActivity.this, KanIlanlariActivity.class);
                startActivity(intent);
            }
        });


        // Kan İlanları kartı tıklama
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // KanIlanlariActivity açılıyor
                Intent intent = new Intent(MainActivity.this, BildirimlerimActivity.class);
                startActivity(intent);
            }
        });

        // 3 numaralı kart tıklama
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Burada yeni açılacak bir Activity belirlemen gerekiyor
                // Şimdilik KanAraActivity'e yönlendirelim istersen
                Intent intent = new Intent(MainActivity.this, HesapAyarlariActivity.class);
                startActivity(intent);
            }
        });

        // Kan Ara kartı tıklama
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // KanAraActivity açılıyor
                Intent intent = new Intent(MainActivity.this, KanAraActivity.class);
                startActivity(intent);
            }
        });
    }
}
