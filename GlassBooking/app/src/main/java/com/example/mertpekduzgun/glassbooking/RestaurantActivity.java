package com.example.mertpekduzgun.glassbooking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);


    }

    public void makeReservation(View view) {

        Intent intent = new Intent(getApplicationContext(), ReservationActivity.class);

        startActivity(intent);

    }



}
