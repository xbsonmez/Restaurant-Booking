package com.example.tunga.gb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.tunga.gb.model.Restaurant;
import com.example.tunga.gb.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantPage extends AppCompatActivity {

    RestaurantAdapter restaurantAdapter;
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        int RestaurantId = intent.getIntExtra("RestaurantId",0);
        textView = (TextView) findViewById(R.id.restaurant_page_rest_name_tw);
        textView1=(TextView) findViewById(R.id.restaurant_page_rest_address_tw);
        textView2=(TextView) findViewById(R.id.restaurant_page_rest_phone_tw);
        textView3=(TextView) findViewById(R.id.restaurant_page_rest_features_tw);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        Call<Restaurant> call =  RetrofitClient.getUrls().getRestaurantDetails(RestaurantId);
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {

                if (response.isSuccessful()) {
                    Restaurant restaurant = response.body();
                    System.out.println(restaurant.toString());
                    textView.setText(restaurant.getRestaurantName());
                    textView1.setText(restaurant.getLocation());
                    textView2.setText(restaurant.getTelNo());
                    textView3.setText(restaurant.getFeaturesInfo());
                }
            }
            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });




}
    }
