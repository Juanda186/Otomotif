package com.destinyapp.onlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapp.onlineshop.API.ApiRequest;
import com.destinyapp.onlineshop.API.RetroServer;
import com.destinyapp.onlineshop.Model.ResponseModel;
import com.destinyapp.onlineshop.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText username,password,nama,email,alamat;
    Button login;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        nama = findViewById(R.id.etNama);
        email = findViewById(R.id.etEmail);
        alamat = findViewById(R.id.etAlamat);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.tvRegister);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checker();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void Logic(){
        final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
        pd.setMessage("Sedang Mengecheck Username");
        pd.setCancelable(false);
        pd.show();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> log = api.Register(username.getText().toString(),
                password.getText().toString(),
                nama.getText().toString(),
                email.getText().toString(),
                alamat.getText().toString());
        log.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                pd.hide();
                try {
                    if (response.body().getStatus().equals("success")){
                        Toast.makeText(RegisterActivity.this, "Akun berhasil terbuat", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(RegisterActivity.this, "Akun Gagal Terbuat", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.hide();
                Toast.makeText(RegisterActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Checker(){
        final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
        pd.setMessage("Sedang Mengecheck Username");
        pd.setCancelable(false);
        pd.show();
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> log = api.Checker(username.getText().toString());
        log.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                pd.hide();
                try {
                    if (!response.body().getStatus().equals("success")){
                        Logic();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Username sudah digunakan harap gunakan username lain", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                pd.hide();
                Toast.makeText(RegisterActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}