package com.destinyapp.onlineshop.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.destinyapp.onlineshop.Activity.LoginActivity;
import com.destinyapp.onlineshop.Activity.MainActivity;
import com.destinyapp.onlineshop.R;
import com.destinyapp.onlineshop.SharedPreferance.DB_Helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class Method {
    public String MagicRP(double nilai){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        BigDecimal bd1 = new BigDecimal(nilai).setScale(0, RoundingMode.HALF_UP);
        String Format = formatRupiah.format(bd1);
        String MAGIC1 = Format.replace("Rp","Rp.");
        return MAGIC1;
    }
    public String MagicChange(String magic){
        String MAGIC1 = magic.replace("Rp","");
        String MAGIC2 = MAGIC1.replace(",","");
        return MAGIC2.replace(".","");
    }
    public String MagicNumber(String magic){
        String MAGIC1 = magic.replace("Rp.","");
        String MAGIC2 = MAGIC1.replace(",",".");
        return MAGIC2;
    }
    public void Wallet(Context ctx){
        Toast.makeText(ctx, "Pengisian Wallet ?", Toast.LENGTH_SHORT).show();
    }
    public void Isi(Context ctx){
        Toast.makeText(ctx, "Permintaan Pengisian", Toast.LENGTH_SHORT).show();
    }
    public void Logout(final Context ctx){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("Anda Yakin ingin Logout ?")
                .setCancelable(false)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(ctx, LoginActivity.class);
                        DB_Helper dbHelper = new DB_Helper(ctx);
                        dbHelper.userLogout(ctx);
                        ctx.startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                //Set your icon here
                .setTitle("Perhatian !!!")
                .setIcon(R.drawable.ic_close_black_24dp);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
