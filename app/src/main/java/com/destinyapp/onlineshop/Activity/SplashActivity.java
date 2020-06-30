package com.destinyapp.onlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.destinyapp.onlineshop.R;
import com.destinyapp.onlineshop.SharedPreferance.DB_Helper;

public class SplashActivity extends AppCompatActivity {
    DB_Helper dbHelper;
    String username,nama,email,profile,alamat,level;
    LottieAnimationView Animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Animation=findViewById(R.id.loading);
        //ini adalah logika simple dalam splash screen
        final Handler handler = new Handler();

        dbHelper = new DB_Helper(SplashActivity.this);
        Cursor cursor = dbHelper.checkSession();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                username = cursor.getString(0);
                nama = cursor.getString(1);
                email = cursor.getString(2);
                profile = cursor.getString(3);
                alamat = cursor.getString(4);
                level = cursor.getString(5);
            }
        }

        if (username!=null){
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
        }else {
            Animation.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    changeActivity();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }

    }
    // kita mungkin mau buat apps mirip kyk shopee tapi coba kita ubah sedikit designnya karena ini apps lebih kecil
    // kita akan membuat dengan Navigator Drawer
    private void changeActivity(){
        //Logic disini untuk memindahkan dari SplashActivity ke MainActivity
        Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
