package com.example.tunga.gb.retrofit;

import com.example.tunga.gb.model.Restaurant;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitUrls {

    @GET("persons")
    Call<ResponseBody> getPersons(
            @Query("id") int id
    );

    @POST("help")
    Call<ResponseBody> help(
            @Body HashMap<String, String> body
    );

    @POST("restaurant/create")
    Call<ResponseBody> restaurant(
            @Body HashMap<String, String> body);

    @POST("persons/update")
    Call<ResponseBody> updateUserInfo(
            @Body HashMap<String, String> body);

    @GET("restaurants")
    Call<List<Restaurant>> getRestaurant();

    @GET("restaurants/{id}")
    Call<Restaurant> getRestaurantDetails(
            @Path("id") int id
    );

}

