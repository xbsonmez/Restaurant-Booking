package com.example.lenovo.glassbookingapp.retrofit;

import com.example.lenovo.glassbookingapp.Model.Person;
import com.example.lenovo.glassbookingapp.Services.MessageService;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitUrls {

    @GET("persons")
    Call<List<Person>> getPersons();

    @POST("help")
    Call<ResponseBody> help(
            @Body HashMap<String, String> body
    );

    @POST("accessLogin")
    Call<Person>login(@Body HashMap<String,String>body);

    @POST("addPerson")
    Call<MessageService>signUp(@Body HashMap<String,String>body);

}
