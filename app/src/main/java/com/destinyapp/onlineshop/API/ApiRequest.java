package com.destinyapp.onlineshop.API;

import com.destinyapp.onlineshop.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseModel> Login(@Field("username") String username,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("user/checker")
    Call<ResponseModel> Checker(@Field("username") String username);

    @FormUrlEncoded
    @POST("product/all")
    Call<ResponseModel> AllProduct(@Field("username") String username);

    @FormUrlEncoded
    @POST("product/my")
    Call<ResponseModel> MyProduct(@Field("username") String username);

    @FormUrlEncoded
    @POST("history/all")
    Call<ResponseModel> AllHistory(@Field("username") String username);

    @FormUrlEncoded
    @POST("history/beli")
    Call<ResponseModel> BeliHistory(@Field("username") String username);

    @FormUrlEncoded
    @POST("history/jual")
    Call<ResponseModel> JualHistory(@Field("username") String username);

    @FormUrlEncoded
    @POST("product/penjualan")
    Call<ResponseModel> Penjualan(@Field("username") String username,
                                  @Field("id_barang") String id_barang,
                                  @Field("beli") String beli);
}
