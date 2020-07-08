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
import com.destinyapp.onlineshop.SharedPreferance.DB_Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button Login;
    DB_Helper dbHelper;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DB_Helper(this);
        username=findViewById(R.id.etUsername);
        password=findViewById(R.id.etPassword);
        Login=findViewById(R.id.btnLogin);
        register=findViewById(R.id.tvRegister);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logic();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Logic(){
        if (username.getText().toString().isEmpty()){
            Toast.makeText(this, "Username Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else if(password.getText().toString().isEmpty()){
            Toast.makeText(this, "Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }else{
            final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Sedang Mencoba Login");
            pd.setCancelable(false);
            pd.show();
            ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
            Call<ResponseModel> log = api.Login(username.getText().toString(),password.getText().toString());
            log.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.body().getStatus().equals("success")){
                        dbHelper.saveUser(
                                response.body().getData().get(0).username,
                                response.body().getData().get(0).nama,
                                response.body().getData().get(0).email,
                                response.body().getData().get(0).profile,
                                response.body().getData().get(0).alamat,
                                response.body().getData().get(0).level
                        );
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Username dan Password salah", Toast.LENGTH_SHORT).show();
                    }
                    pd.hide();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                    pd.hide();
                }
            });
        }
    }
}
