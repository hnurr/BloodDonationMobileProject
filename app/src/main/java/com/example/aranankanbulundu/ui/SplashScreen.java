package com.example.aranankanbulundu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aranankanbulundu.MainActivity;
import com.example.aranankanbulundu.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }
        );

        Handler handler = new Handler();
        handler.postDelayed(new Runnable (){
            @Override
            public void run(){

                if(FirebaseAuth.getInstance().getCurrentUser() == null){
                    startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                }
                else{
                    startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                    //burada MainActivity yazılı idi geçiçi olarak değiştirm...
                    finish();
                }



//                startActivity(new Intent( SplashScreen.this, MainActivity.class));
//                finish();
            }
        },3000);


    }
}