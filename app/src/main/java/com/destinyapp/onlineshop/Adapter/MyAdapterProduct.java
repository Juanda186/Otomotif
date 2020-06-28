package com.destinyapp.onlineshop.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.destinyapp.onlineshop.Model.DataModel;
import com.destinyapp.onlineshop.Model.Method;
import com.destinyapp.onlineshop.R;
import com.destinyapp.onlineshop.SharedPreferance.DB_Helper;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterProduct extends RecyclerView.Adapter<MyAdapterProduct.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    Dialog myDialog;
    Method method;
    public MyAdapterProduct (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_my_product,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterProduct.HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        method=new Method();
        holderData.nama.setText(dm.getNama_barang());
        holderData.harga.setText(method.MagicRP(Double.parseDouble(dm.getHarga())));
        holderData.quantity.setText(method.MagicNumber(method.MagicRP(Double.parseDouble(dm.getQuantity()))));
        Glide.with(ctx)
                .load(ctx.getString(R.string.base_url)+"img/product/"+dm.getGambar())
                .apply(new RequestOptions().override(350, 550))
                .into(holderData.gambar);
        holderData.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holderData.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        LinearLayout edit,hapus;
        DataModel dm;
        HolderData(View v){
            super(v);
            nama=v.findViewById(R.id.tvNamaBarang);
            harga=v.findViewById(R.id.tvHarga);
            quantity=v.findViewById(R.id.tvQuantity);
            gambar=v.findViewById(R.id.tvGambar);
            edit=v.findViewById(R.id.linearEdit);
            hapus=v.findViewById(R.id.linearHapus);
        }
    }

}
