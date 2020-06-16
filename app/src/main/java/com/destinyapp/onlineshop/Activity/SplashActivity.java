package com.destinyapp.onlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.destinyapp.onlineshop.R;

public class SplashActivity extends AppCompatActivity {
    //oke untuk main activity pertama ini mendingan kita buat splash screen supaya ngikutin jaman skarang aja
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //ini adalah logika simple dalam splash screen
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                changeActivity();
            }
        }, 3000); //3000 L = 3 detik/Milisecond
    }
    // kita mungkin mau buat apps mirip kyk shopee tapi coba kita ubah sedikit designnya karena ini apps lebih kecil
    // kita akan membuat dengan Navigator Drawer
    private void changeActivity(){
        //Logic disini untuk memindahkan dari SplashActivity ke MainActivity
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
