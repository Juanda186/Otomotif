package com.destinyapp.onlineshop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel {
    //USER
    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("nama")
    @Expose
    public String nama;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("wallet")
    @Expose
    public String wallet;
    @SerializedName("profile")
    @Expose
    public String profile;

    @SerializedName("alamat")
    @Expose
    public String alamat;

    @SerializedName("level")
    @Expose
    public String level;

    //Barang
    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("nama_barang")
    @Expose
    public String nama_barang;

    @SerializedName("harga")
    @Expose
    public String harga;

    @SerializedName("quantity")
    @Expose
    public String quantity;

    @SerializedName("gambar")
    @Expose
    public String gambar;

    @SerializedName("deskripsi")
    @Expose
    public String id_penjual;
    //GETTER SETTER
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }


    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getId_penjual() {
        return id_penjual;
    }

    public void setId_penjual(String id_penjual) {
        this.id_penjual = id_penjual;
    }
}
