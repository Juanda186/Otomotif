package com.destinyapp.onlineshop.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.destinyapp.onlineshop.Model.DataModel;
import com.destinyapp.onlineshop.Model.Method;
import com.destinyapp.onlineshop.R;
import com.destinyapp.onlineshop.SharedPreferance.DB_Helper;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    Dialog myDialog;
    DB_Helper dbHelper;
    String username,nama,email,profile,alamat,level;
    Method method;
    public AdapterHistory (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_history,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistory.HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
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
        if (dm.getId_penjual().equals(username)){
            holderData.actor.setText("Pembeli");
            holderData.nama.setText(dm.getId_pembeli());
        }else{
            holderData.actor.setText("Penjual");
            holderData.nama.setText(dm.getId_penjual());
        }
        holderData.nama_barang.setText(dm.getNama_barang());
        method=new Method();
        holderData.total.setText(method.MagicRP(Double.parseDouble(dm.getTotal())));
        holderData.quantity.setText(method.MagicNumber(method.MagicRP(Double.parseDouble(dm.getQuantity()))));
        holderData.detail.setOnClickListener(new View.OnClickListener() {
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
        TextView nama,total,quantity,actor,nama_barang;
        LinearLayout detail;
        DataModel dm;
        HolderData(View v){
            super(v);
            nama=v.findViewById(R.id.tvNama);
            total=v.findViewById(R.id.tvTotal);
            quantity=v.findViewById(R.id.tvQuantity);
            detail=v.findViewById(R.id.linearDetail);
            actor=v.findViewById(R.id.tvActor);
            nama_barang=v.findViewById(R.id.tvNamaBarang);
        }
    }

}

