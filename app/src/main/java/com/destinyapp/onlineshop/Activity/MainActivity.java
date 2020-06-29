package com.destinyapp.onlineshop.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.destinyapp.onlineshop.API.ApiRequest;
import com.destinyapp.onlineshop.API.RetroServer;
import com.destinyapp.onlineshop.Model.Method;
import com.destinyapp.onlineshop.Model.ResponseModel;
import com.destinyapp.onlineshop.R;
import com.destinyapp.onlineshop.SharedPreferance.DB_Helper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    ImageView Profile;
    TextView Nama,Username;
    DB_Helper dbHelper;
    String username,nama,email,profile,alamat,level;
    Method method;
    MenuItem Wallet,Isi,Logout,Prof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        method=new Method();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        dbHelper = new DB_Helper(MainActivity.this);
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
        if (username==null){
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
        }

        //OK DONE dulu design utama jadi tinggal buat isinya aja
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        Profile=header.findViewById(R.id.ivProfile);
        Nama=header.findViewById(R.id.tvNama);
        Username=header.findViewById(R.id.tvUsername);

        Nama.setText(nama);
        Username.setText(username);
        Glide.with(MainActivity.this)
                .load(getString(R.string.base_url)+"img/profile/"+profile)
                .apply(new RequestOptions().override(350, 550))
                .into(Profile);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        Wallet = menu.findItem(R.id.action_wallet);
        Isi = menu.findItem(R.id.action_isi);
        Prof = menu.findItem(R.id.action_profile);
        Logout = menu.findItem(R.id.action_settings);
        if (level.equals("1")){
            Wallet.setVisible(true);
            Isi.setVisible(true);
            Prof.setVisible(true);
            Logout.setVisible(true);
        }else{
            Wallet.setVisible(true);
            Isi.setVisible(false);
            Prof.setVisible(true);
            Logout.setVisible(true);
        }
        Checker();
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            method.Logout(MainActivity.this);
        }else if(id == R.id.action_wallet){
            method.Wallet(MainActivity.this);
        }else if(id == R.id.action_isi){
            method.Isi(MainActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }
    private void Checker(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> log = api.Checker(username);
        log.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getStatus().equals("success")){
                    String RP = method.MagicRP(Double.parseDouble(response.body().getData().get(0).wallet));
                    CharSequence charSequence = new StringBuilder(RP);
                    Wallet.setTitle(charSequence);
                }else{
                    Toast.makeText(MainActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Koneksi internet Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void logout(){
        method.Logout(MainActivity.this);
    }
}
