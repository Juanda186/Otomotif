package com.destinyapp.onlineshop.SharedPreferance;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Helper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "onlineshop.db";
    private static final int DATABASE_VERSION = 1;

    //Session
    public static final String TABLE_NAME_SESSION = "session";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_LEVEL = "level";

    //Favorite
    public static final String TABLE_FAVORITE = "favorite";
    public static final String COLUMN_ID_BARANG = "id_barang";
    public static final String COLUMN_NAMA_BARANG = "nama_barang";
    public static final String COLUMN_HARGA_BARANG = "harga_barang";
    public static final String COLUMN_DESKRIPSI_BARANG = "deskripsi_barang";
    public static final String COLUMN_GAMBAR_BARANG = "gambar_barang";

    //Keranjang
    public static final String TABLE_KERANJANG = "keranjang";

    public DB_Helper(Context context){super(
            context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME_SESSION+" (" +
                COLUMN_USERNAME+" TEXT NOT NULL, "+
                COLUMN_NAMA+" TEXT NOT NULL, "+
                COLUMN_LEVEL+" TEXT NOT NULL);"
        );
        db.execSQL("CREATE TABLE "+TABLE_FAVORITE+" (" +
                COLUMN_ID_BARANG+" TEXT NOT NULL, "+
                COLUMN_NAMA_BARANG+" TEXT NOT NULL, "+
                COLUMN_HARGA_BARANG+" TEXT NOT NULL, "+
                COLUMN_DESKRIPSI_BARANG+" TEXT NOT NULL, "+
                COLUMN_GAMBAR_BARANG+" TEXT NOT NULL);"
        );
        db.execSQL("CREATE TABLE "+TABLE_KERANJANG+" (" +
                COLUMN_ID_BARANG+" TEXT NOT NULL, "+
                COLUMN_NAMA_BARANG+" TEXT NOT NULL, "+
                COLUMN_HARGA_BARANG+" TEXT NOT NULL, "+
                COLUMN_DESKRIPSI_BARANG+" TEXT NOT NULL, "+
                COLUMN_GAMBAR_BARANG+" TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SESSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KERANJANG);
        this.onCreate(db);
    }


    //SAVE
    public void saveUser(String username,String nama,String level){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_NAMA, nama);
        values.put(COLUMN_LEVEL, level);
        db.insert(TABLE_NAME_SESSION,null,values);
        db.close();
    }
    public void saveFavorite(String id,String nama,String harga,String deskripsi,String gambar){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_BARANG, id);
        values.put(COLUMN_NAMA_BARANG, nama);
        values.put(COLUMN_HARGA_BARANG, harga);
        values.put(COLUMN_DESKRIPSI_BARANG, deskripsi);
        values.put(COLUMN_GAMBAR_BARANG, gambar);
        db.insert(TABLE_FAVORITE,null,values);
        db.close();
    }

    public void saveKeranjang(String id,String nama,String harga,String deskripsi,String gambar){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_BARANG, id);
        values.put(COLUMN_NAMA_BARANG, nama);
        values.put(COLUMN_HARGA_BARANG, harga);
        values.put(COLUMN_DESKRIPSI_BARANG, deskripsi);
        values.put(COLUMN_GAMBAR_BARANG, gambar);
        db.insert(TABLE_KERANJANG,null,values);
        db.close();
    }
}
