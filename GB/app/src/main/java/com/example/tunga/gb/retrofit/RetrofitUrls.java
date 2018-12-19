package com.example.tunga.gb.retrofit;

import com.example.tunga.gb.model.Person;
import com.example.tunga.gb.model.Reservation;
import com.example.tunga.gb.model.Restaurant;
import com.example.tunga.gb.services.MessageService;
import com.example.tunga.gb.services.Time;

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
    //manager request delete
    @POST("restaurant/delete/request")
    Call<Restaurant> deleteRequestRestaurantRequest(
            @Body HashMap<String, String> body);

    @POST("persons/update")
    Call<ResponseBody> updateUserInfo(
            @Body HashMap<String, String> body);

    @POST("restaurant/update/request")
    Call<ResponseBody> updateRestaurantInfo(
            @Body HashMap<String, String> body);


    @GET("restaurants")
    Call<List<Restaurant>> getRestaurant();

    @GET("restaurants/{id}")
    Call<Restaurant> getRestaurantDetails(
            @Path("id") int id
    );

    @GET("restaurant/{id}")
    Call<List<Restaurant>> getMyRestaurants(
            @Path("id") int id
    );

    @GET("persons/{id}")
    Call<Person> getPersonDetails(
            @Path("id") int id
    );

    @POST("restaurant/offline")
    Call<ResponseBody> deleteRestaurant(
            @Body HashMap<String, String> body
    );

    @POST("accessLogin")
    Call<Person> login(@Body HashMap<String, String> body);

   @POST("addPerson")
    Call<MessageService> signUp(@Body HashMap<String, String> body);

   @POST("restaurants/search")
    Call<List<Restaurant>> searchRestaurant(

           @Body HashMap<String, String> body);


   @POST("restaurants/{restaurantID}/reservation")
    Call<List<Time>> listAvaliableTimes(
           @Path("restaurantID") int restaurantID,
            @Body HashMap<String,String>body
           );

   @POST ("restaurant/{restaurantID}/reservation/make/{personID}")
    Call<ResponseBody> makeReservation(
           @Body HashMap<String,String>body,
           @Path("restaurantID") int restaurantID,
           @Path("personID") int personID

   );

   @GET ("restaurants/waiting/approvelist")
   Call<List<Restaurant>> getAddWaitingRestaurants();

   //admin restaurant approvet//
   @POST("restaurant/approve")
   Call<Restaurant> approveRestaurantRequest(
           @Body HashMap<String, String> body);

   @GET ("restaurants/waiting/deletelist")
   Call<List<Restaurant>> getDeleteWaitingRestaurants();

   @GET ("restaurants/waiting/updatelist")
   Call<List<Restaurant>> getUpdateWaitingRestaurants();


   //admin reject add//
   @POST("restaurant/reject")
    Call<Restaurant> rejectAddRestaurantRequest(
            @Body HashMap<String, String> body);


    //admin update rejection//
    @POST("restaurant/reject/update")
    Call<Restaurant> rejectUpdateRestaurantRequest(
            @Body HashMap<String, String> body);

    //admin update approve//
    @POST("restaurant/approve/update")
    Call<Restaurant> approveUpdateRestaurantRequest(
            @Body HashMap<String, String> body);

    //admin delete approve
    @POST("restaurant/approve/delete")
    Call<Restaurant> approveDeleteRestaurantRequest(
            @Body HashMap<String, String> body);

    //admin reject delete
    @POST("restaurant/reject/delete")
    Call<Restaurant> rejectDeleteRestaurantRequest(
            @Body HashMap<String, String> body);



    @GET("reservation/{personID}")
    Call<List<Reservation>> reservationList(
            @Path("personID") int personID);

    @POST("reservation/cancel/{reservationID}")
    Call<Reservation> cancelReservation(
            @Path("reservationID") int reservationID);


    @GET("reservations/myrestaurant/{restaurantID}")
    Call<List<Reservation>> showMyRestaurantReservations(
            @Path("restaurantID") int reservationID);








}



