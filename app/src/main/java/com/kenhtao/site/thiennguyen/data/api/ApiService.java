package com.kenhtao.site.thiennguyen.data.api;

import com.kenhtao.site.thiennguyen.data.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("/api/auth/login")
    Call<User> login(@Field("email") String email, @Field("password") String password);

    @POST("/api/auth/register")
    Call<User> register(@Body User user);
}
