package com.example.tunga.gb;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tunga.gb.model.Restaurant;
import com.example.tunga.gb.retrofit.RetrofitClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateRestaurantPage extends AppCompatActivity {

    UpdateRestaurantAdapter restaurantAdapter;
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
        setContentView(R.layout.activity_update_restaurant_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Update Restaurant Page");
        setSupportActionBar(toolbar);
        final Intent intent = getIntent();
        int RestaurantId = intent.getIntExtra("RestaurantId", 0);


        editRestaurant = (Button) findViewById(R.id.update_restaurant_page_edit_profile_button);
        deleteRestaurant = (Button) findViewById(R.id.update_restaurant_page_delete_restaurant_button);
        textView = (TextView) findViewById(R.id.update_restaurant_page_rest_name_tw);
        textView1 = (TextView) findViewById(R.id.update_restaurant_page_rest_address_tw);
        textView2 = (TextView) findViewById(R.id.update_restaurant_page_rest_phone_tw);
        textView3 = (TextView) findViewById(R.id.update_restaurant_page_rest_features_tw);
        makeReservation = (Button) findViewById(R.id.update_make_reservation_button);
        showReservationList = (Button) findViewById(R.id.update_show_reservation_list_button);
        approveButton = (Button) findViewById(R.id.update_approve_button_for_admin);
        rejectButton=(Button) findViewById(R.id.update_reject_button_for_admin);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
/*        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


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

                approveUpdateRestaurantRequest();



            }
        });
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectUpdateRestaurantRequest();
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
            approveButton.setVisibility((View.VISIBLE));
            rejectButton.setVisibility((View.VISIBLE));


        }

    }

    public void deleteRestaurantMethod () {

    }

    public void approveUpdateRestaurantRequest(){
        Intent intent = getIntent();
        int RestaurantId = intent.getIntExtra("RestaurantId", 0);

        HashMap<String, String> body = new HashMap<>();
        body.put("restaurantID",String.valueOf(RestaurantId));
        Call<Restaurant> call = RetrofitClient.getUrls().approveUpdateRestaurantRequest(body);
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Restaurant  başarılı bir şekilde güncellendi", Toast.LENGTH_SHORT)
                            .show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);
                        }
                    }, 1500);


                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });
    }
    public void rejectUpdateRestaurantRequest(){
        Intent intent = getIntent();
        int RestaurantId = intent.getIntExtra("RestaurantId", 0);

        HashMap<String, String> body = new HashMap<>();
        body.put("restaurantID",String.valueOf(RestaurantId));
        Call<Restaurant> call = RetrofitClient.getUrls().rejectUpdateRestaurantRequest(body);
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Restaurant  güncelleme red edildi", Toast.LENGTH_SHORT)
                            .show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);
                        }
                    }, 1500);



                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });
    }
}

