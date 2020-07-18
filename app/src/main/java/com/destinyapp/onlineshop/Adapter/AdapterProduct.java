package com.destinyapp.onlineshop.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.destinyapp.onlineshop.API.ApiRequest;
import com.destinyapp.onlineshop.API.RetroServer;
import com.destinyapp.onlineshop.Activity.DetailActivity;
import com.destinyapp.onlineshop.Activity.HistoryActivity;
import com.destinyapp.onlineshop.Activity.MainActivity;
import com.destinyapp.onlineshop.Model.DataModel;
import com.destinyapp.onlineshop.Model.Method;
import com.destinyapp.onlineshop.Model.ResponseModel;
import com.destinyapp.onlineshop.R;
import com.destinyapp.onlineshop.SharedPreferance.DB_Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    Dialog myDialog;
    Method method;
    EditText Jumlah;
    TextView Barang;
    Button Submit,Cancel;
    DB_Helper dbHelper;
    String username,nama,email,profile,alamat,level;
    public AdapterProduct (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_product,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduct.HolderData holderData, int posistion) {
        final DataModel dm = mList.get(posistion);
        method=new Method();
        myDialog = new Dialog(ctx);
        myDialog.setContentView(R.layout.dialog_beli);
        Jumlah=myDialog.findViewById(R.id.etJumlah);
        Barang=myDialog.findViewById(R.id.tvNamaBarangs);
        Submit=myDialog.findViewById(R.id.btnBeli);
        Cancel=myDialog.findViewById(R.id.btnCancel);
        dbHelper = new DB_Helper(ctx);
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

        holderData.nama.setText(dm.getNama_barang());
        holderData.harga.setText(method.MagicRP(Double.parseDouble(dm.getHarga())));
        holderData.quantity.setText(method.MagicNumber(method.MagicRP(Double.parseDouble(dm.getQuantity()))));
        Glide.with(ctx)
                .load(ctx.getString(R.string.base_url)+"img/product/"+dm.getGambar())
                .apply(new RequestOptions().override(350, 550))
                .into(holderData.gambar);
        holderData.beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.show();
                Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Jumlah.getText().toString().isEmpty() || Jumlah.getText().toString().equals("0")){
                            Toast.makeText(ctx, "Harap isi data Jumlah", Toast.LENGTH_SHORT).show();
                        }else{
                            String j = Jumlah.getText().toString();
                            String q = dm.getQuantity();
                            Checker(j,q,username,dm.getId());

                        }
                    }
                });
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.hide();
                    }
                });
            }
        });
        holderData.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goInput = new Intent(ctx, DetailActivity.class);
                goInput.putExtra("GAMBAR",ctx.getString(R.string.base_url)+"img/product/"+dm.getGambar());
                goInput.putExtra("NAMA_BARANG",dm.getNama_barang());
                goInput.putExtra("HARGA",method.MagicRP(Double.parseDouble(dm.getHarga())));
                goInput.putExtra("QUANTITY",method.MagicNumber(method.MagicRP(Double.parseDouble(dm.getQuantity()))));
                goInput.putExtra("DESKRIPSI",dm.getDeskripsi());
                goInput.putExtra("PENJUAL",dm.getNama());
                ctx.startActivities(new Intent[]{goInput});
            }
        });
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView nama,harga,quantity,penjual;
        ImageView gambar;
        LinearLayout beli,detail;
        DataModel dm;
        HolderData(View v){
            super(v);
            nama=v.findViewById(R.id.tvNamaBarang);
            harga=v.findViewById(R.id.tvHarga);
            quantity=v.findViewById(R.id.tvQuantity);
            gambar=v.findViewById(R.id.tvGambar);
            beli=v.findViewById(R.id.linearBeli);
            detail=v.findViewById(R.id.linearDetail);
        }
    }
    private void Checker(final String j,final String q,final String User,final String IDS){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> log = api.Checker(username);
        log.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().getStatus().equals("success")){
                    if (Integer.parseInt(j) <= Integer.parseInt(q)){
                        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
                        Call<ResponseModel> log = api.Penjualan(User,IDS,j);
                        log.enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                Toast.makeText(ctx, "Pembelian Sukses", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ctx,MainActivity.class);
                                ctx.startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                                Toast.makeText(ctx, "Koneksi Gagal Pembelian Gagal", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(ctx, "Stock barang Sedang Kurang", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ctx, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx, "Koneksi internet Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
