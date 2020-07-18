package com.destinyapp.onlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.destinyapp.onlineshop.R;

public class DetailActivity extends AppCompatActivity {
    TextView Penjual,Harga,Quantity,Barang,Deskripsi;
    ImageView gambar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Penjual = findViewById(R.id.tvNamaPenjual);
        Harga = findViewById(R.id.tvHarga);
        Quantity = findViewById(R.id.tvQuantity);
        Barang = findViewById(R.id.tvNamaBarang);
        gambar = findViewById(R.id.ivGambar);
        Deskripsi = findViewById(R.id.tvDeskripsi);

        //GETINTENT
        Intent data = getIntent();
        final String GAMBAR = data.getStringExtra("GAMBAR");
        final String NAMA_BARANG = data.getStringExtra("NAMA_BARANG");
        final String HARGA = data.getStringExtra("HARGA");
        final String QUANTITY = data.getStringExtra("QUANTITY");
        final String DESKRIPSI = data.getStringExtra("DESKRIPSI");
        final String PENJUAL = data.getStringExtra("PENJUAL");

        Penjual.setText(PENJUAL);
        Harga.setText(HARGA);
        Quantity.setText(QUANTITY);
        Barang.setText(NAMA_BARANG);
        Deskripsi.setText(DESKRIPSI);

        Glide.with(this)
                .load(GAMBAR)
                .apply(new RequestOptions().override(350, 550))
                .into(gambar);
    }
}