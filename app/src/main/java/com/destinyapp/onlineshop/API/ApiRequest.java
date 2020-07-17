package com.destinyapp.onlineshop.API;

import com.destinyapp.onlineshop.Model.ResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("user/Login")
    Call<ResponseModel> Login(@Field("username") String username,
                                     @Field("password") String password);
    @FormUrlEncoded
    @POST("user/Checker")
    Call<ResponseModel> Checker(@Field("username") String username);

    @FormUrlEncoded
    @POST("user/Register")
    Call<ResponseModel> Register(@Field("username") String username,
                                @Field("password") String password,
                                @Field("nama") String nama,
                                @Field("email") String email,
                                @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST("product/AllKategori")
    Call<ResponseModel> AllProduct(@Field("username") String username);

    @FormUrlEncoded
    @POST("product/MyKategori")
    Call<ResponseModel> MyProduct(@Field("username") String username);

    @FormUrlEncoded
    @POST("history/All")
    Call<ResponseModel> AllHistory(@Field("username") String username);

    @FormUrlEncoded
    @POST("history/Beli")
    Call<ResponseModel> BeliHistory(@Field("username") String username);

    @FormUrlEncoded
    @POST("history/Jual")
    Call<ResponseModel> JualHistory(@Field("username") String username);

    @FormUrlEncoded
    @POST("product/Penjualan")
    Call<ResponseModel> Penjualan(@Field("username") String username,
                                  @Field("id_barang") String id_barang,
                                  @Field("beli") String beli);


    @Multipart
    @POST("product/Tambah")
    Call<ResponseModel> InputBarang(@Part("username") RequestBody username,
                                 @Part("nama") RequestBody nama,
                                 @Part("harga") RequestBody harga,
                                 @Part("quantity") RequestBody quantity,
                                 @Part MultipartBody.Part gambar,
                                 @Part("deskripsi") RequestBody deskripsi);
}
