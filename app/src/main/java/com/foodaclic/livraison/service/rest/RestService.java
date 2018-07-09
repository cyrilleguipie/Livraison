package com.foodaclic.livraison.service.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by cyrilleguipie on 10/27/15.
 */
public interface RestService {


    @FormUrlEncoded
    @POST("api/register_phone.php")
    Call<ResponseBody> registerDevice(@Field("regID") String regID, @Field("id") int id);

    @FormUrlEncoded
    @POST("api/pay.php")
    Call<ResponseBody> pay( @Field("id") int id, @Field("userliv") String userliv);


    @FormUrlEncoded
    @POST("api/take.php")
    Call<ResponseBody> take( @Field("id") int id, @Field("userliv") String userliv, @Field("code") String code
        , @Field("reception") String reception, @Field("pourboire") String pourboire);

    @FormUrlEncoded
    @POST("api/position.php")
    Call<ResponseBody> position( @Field("id") int id, @Field("lat") double lat, @Field("lng") double lng);

    @FormUrlEncoded
    @POST("api/auth.php")
    Call<ResponseBody> authenticate(@Field("login") String login,
        @Field("password") String password);

    @FormUrlEncoded
    @POST("api/getOrders.php")
    Call<ResponseBody> fetch(@Field("userliv") String userliv, @Field("livcouverture") String livcouverture);


}
