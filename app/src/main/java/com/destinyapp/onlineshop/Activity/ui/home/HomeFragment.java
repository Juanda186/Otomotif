package com.destinyapp.onlineshop.Activity.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.destinyapp.onlineshop.Activity.InputProductActivity;
import com.destinyapp.onlineshop.Activity.LoginActivity;
import com.destinyapp.onlineshop.Activity.MainActivity;
import com.destinyapp.onlineshop.Activity.ProductActivity;
import com.destinyapp.onlineshop.R;
import com.destinyapp.onlineshop.SharedPreferance.DB_Helper;

public class HomeFragment extends Fragment {
    LinearLayout Product,MyProduct,Tambah;
    DB_Helper dbHelper;
    String username,nama,email,profile,alamat,level;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Product=view.findViewById(R.id.linearBelanja);
        MyProduct=view.findViewById(R.id.linearMyProduct);
        Tambah=view.findViewById(R.id.linearTambah);
        dbHelper = new DB_Helper(getActivity());
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
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goInput = new Intent(getActivity(),ProductActivity.class);
                goInput.putExtra("ACTIVITY","All");
                getActivity().startActivities(new Intent[]{goInput});
                startActivity(goInput);
            }
        });
        MyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goInput = new Intent(getActivity(),ProductActivity.class);
                goInput.putExtra("ACTIVITY","My");
                getActivity().startActivities(new Intent[]{goInput});
                startActivity(goInput);
            }
        });
        Tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),InputProductActivity.class);
                startActivity(intent);
            }
        });
    }
}
