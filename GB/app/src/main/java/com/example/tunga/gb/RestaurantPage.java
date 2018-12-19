package com.example.tunga.gb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tunga.gb.model.Reservation;
import com.example.tunga.gb.model.Restaurant;
import com.example.tunga.gb.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantPage extends AppCompatActivity {

    RestaurantAdapter restaurantAdapter;
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    Button deleteRestaurant;
    Button editRestaurant;
    Button makeReservation;
    Button showReservationList;
    Button approveButton;
    Button rejectButton;


    Restaurant restaurantPage = new Restaurant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Intent intent = getIntent();
        final int RestaurantId = intent.getIntExtra("RestaurantId", 0);


        editRestaurant = (Button) findViewById(R.id.restaurant_page_edit_profile_button);
        deleteRestaurant = (Button) findViewById(R.id.restaurant_page_delete_restaurant_button);
        textView = (TextView) findViewById(R.id.restaurant_page_rest_name_tw);
        textView1 = (TextView) findViewById(R.id.restaurant_page_rest_address_tw);
        textView2 = (TextView) findViewById(R.id.restaurant_page_rest_phone_tw);
        textView3 = (TextView) findViewById(R.id.restaurant_page_rest_features_tw);
        makeReservation = (Button) findViewById(R.id.make_reservation_button);
        showReservationList = (Button) findViewById(R.id.show_reservation_list_button);
        approveButton = (Button) findViewById(R.id.approve_button_for_admin);
        rejectButton=(Button) findViewById(R.id.reject_button_for_admin);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Call<Restaurant> call = RetrofitClient.getUrls().getRestaurantDetails(RestaurantId);
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

                    restaurantPage.setOwnerID(restaurant.getOwnerID());
                    restaurantPage.setRestaurantID(restaurant.getRestaurantID());
                    setButtonsVİsiblity ();

                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });

        editRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditRestaurantPage.class);
                i.putExtra("RestaurantId",RestaurantId);
                startActivity(i);


            }
        });

        makeReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(getApplicationContext(),MakeReservationActivity.class);
                i.putExtra("RestaurantID",restaurantPage.getRestaurantID());

                startActivity(i);
            }
        });

        approveButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                approveRestaurantRequest();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);


            }
        });
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectRestaurantRequest();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        deleteRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRestaurantMethod();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        showReservationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RestaurantReservationList.class);
                i.putExtra("RestaurantId",RestaurantId);
                startActivity(i);

            }
        });
        }



    public void setButtonsVİsiblity () {

        SharedPreferences result = getSharedPreferences("saveDATA",MODE_PRIVATE);
        int value = result.getInt("Value",0);

        SharedPreferences result2 = getSharedPreferences("saveStringDATA", MODE_PRIVATE);
        String roleUser=result2.getString("Role","visitor");

        if(roleUser.equals("MANAGER") && value== restaurantPage.getOwnerID()){
            editRestaurant.setVisibility(View.VISIBLE);
            deleteRestaurant.setVisibility(View.VISIBLE);
            makeReservation.setVisibility(View.INVISIBLE);
            showReservationList.setVisibility(View.VISIBLE);
            approveButton.setVisibility((View.INVISIBLE));
            rejectButton.setVisibility((View.INVISIBLE));

        }
        else if(roleUser.equals("MANAGER" )&& value!= restaurantPage.getOwnerID()){

            editRestaurant.setVisibility(View.INVISIBLE);
            deleteRestaurant.setVisibility(View.INVISIBLE);
            makeReservation.setVisibility(View.INVISIBLE);
            showReservationList.setVisibility(View.INVISIBLE);
            approveButton.setVisibility((View.INVISIBLE));
            rejectButton.setVisibility((View.INVISIBLE));



        }
        else if(roleUser.equals("USER")){
            editRestaurant.setVisibility((View.INVISIBLE));
            deleteRestaurant.setVisibility(View.INVISIBLE);
            makeReservation.setVisibility(View.VISIBLE);
            showReservationList.setVisibility(View.INVISIBLE);
            approveButton.setVisibility((View.INVISIBLE));
            rejectButton.setVisibility((View.INVISIBLE));


        }else if(roleUser.equals("visitor")){

            editRestaurant.setVisibility(View.INVISIBLE);
            deleteRestaurant.setVisibility((View.INVISIBLE));
            makeReservation.setVisibility(View.INVISIBLE);
            showReservationList.setVisibility(View.INVISIBLE);
            approveButton.setVisibility((View.INVISIBLE));
            rejectButton.setVisibility((View.INVISIBLE));

        }
        else if (roleUser.equals("ADMIN")){
            editRestaurant.setVisibility(View.INVISIBLE);
            deleteRestaurant.setVisibility((View.INVISIBLE));
            makeReservation.setVisibility(View.INVISIBLE);
            showReservationList.setVisibility(View.INVISIBLE);
            approveButton.setVisibility((View.INVISIBLE));
            rejectButton.setVisibility((View.INVISIBLE));


        }

    }

    public void deleteRestaurantMethod () {
        Intent intent = getIntent();
        int RestaurantId = intent.getIntExtra("RestaurantId", 0);

        HashMap<String, String> body = new HashMap<>();
        body.put("restaurantID",String.valueOf(RestaurantId));
        Call<Restaurant> call = RetrofitClient.getUrls().deleteRequestRestaurantRequest(body);
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if(response.isSuccessful()){




                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });



    }

    public void approveRestaurantRequest(){
        Intent intent = getIntent();
        int RestaurantId = intent.getIntExtra("RestaurantId", 0);

        HashMap<String, String> body = new HashMap<>();
        body.put("restaurantID",String.valueOf(RestaurantId));
        Call<Restaurant> call = RetrofitClient.getUrls().approveRestaurantRequest(body);
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Restaurant  başarılı bir şekilde eklendi", Toast.LENGTH_SHORT)
                            .show();


                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });
    }

    public void rejectRestaurantRequest(){
        Intent intent = getIntent();
        int RestaurantId = intent.getIntExtra("RestaurantId", 0);

        HashMap<String, String> body = new HashMap<>();
        body.put("restaurantID",String.valueOf(RestaurantId));
        Call<Restaurant> call = RetrofitClient.getUrls().rejectAddRestaurantRequest(body);
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Restaurant oluşturma onaylanmadı.", Toast.LENGTH_SHORT)
                            .show();


                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });
    }




    }
