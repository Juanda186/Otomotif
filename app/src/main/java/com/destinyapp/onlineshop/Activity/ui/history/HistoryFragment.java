package com.destinyapp.onlineshop.Activity.ui.history;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.destinyapp.onlineshop.Activity.HistoryActivity;
import com.destinyapp.onlineshop.Activity.LoginActivity;
import com.destinyapp.onlineshop.Activity.ProductActivity;
import com.destinyapp.onlineshop.R;
import com.destinyapp.onlineshop.SharedPreferance.DB_Helper;

public class HistoryFragment extends Fragment {


    LinearLayout Seluruh,Beli,Jual;
    DB_Helper dbHelper;
    String username,nama,email,profile,alamat,level;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_history, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        Seluruh=view.findViewById(R.id.linearSeluruh);
        Beli=view.findViewById(R.id.linearPembelian);
        Jual=view.findViewById(R.id.linearPenjualan);

        Seluruh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goInput = new Intent(getActivity(), HistoryActivity.class);
                goInput.putExtra("ACTIVITY","All");
                getActivity().startActivities(new Intent[]{goInput});
                startActivity(goInput);
            }
        });
        Beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goInput = new Intent(getActivity(), HistoryActivity.class);
                goInput.putExtra("ACTIVITY","Beli");
                getActivity().startActivities(new Intent[]{goInput});
                startActivity(goInput);
            }
        });
        Jual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goInput = new Intent(getActivity(), HistoryActivity.class);
                goInput.putExtra("ACTIVITY","Jual");
                getActivity().startActivities(new Intent[]{goInput});
                startActivity(goInput);
            }
        });
    }
}
