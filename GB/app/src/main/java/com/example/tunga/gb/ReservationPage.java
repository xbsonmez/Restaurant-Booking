package com.example.tunga.gb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tunga.gb.model.Reservation;
import com.example.tunga.gb.model.Restaurant;
import com.example.tunga.gb.retrofit.RetrofitClient;
import com.example.tunga.gb.retrofit.RetrofitUrls;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationPage extends AppCompatActivity {

    String restaurantName;
    String restaurantLoc;

    TextView tw;
    TextView tw1;
    TextView tw2;
    TextView tw3;
    TextView tw4;
    Button cancelReservationButton;
    Button updateReservationButton;
    private RetrofitUrls retrofitUrls;
    int ReservationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tw = (TextView) findViewById(R.id.textView1000);
        tw1 = (TextView) findViewById(R.id.textView1001);
        tw2 = (TextView) findViewById(R.id.reservation_phone_number_tv);
        tw3 = (TextView) findViewById(R.id.reservation_date_tv);
        tw4 = (TextView) findViewById(R.id.reservation_time_tv);
        cancelReservationButton=(Button) findViewById(R.id.cancel_reservation_button);
        updateReservationButton=(Button) findViewById(R.id.update_reservation_button);

        final Intent intent = getIntent();
        final int RestaurantId = intent.getIntExtra("RestaurantId", 0);
         ReservationID = intent.getIntExtra("ReservationId",0);
        System.out.println(ReservationID+"dkjfghsjdkfgjhsdfjghdsfjkghsg");
        SharedPreferences result = getSharedPreferences("saveDATA",MODE_PRIVATE);
        int value = result.getInt("Value",0);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); }
        });
*/


        Call<Restaurant> call = RetrofitClient.getUrls().getRestaurantDetails(RestaurantId);
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {

                if (response.isSuccessful()) {
                    Restaurant restaurant = response.body();
                    System.out.println(restaurant.toString());
                    tw.setText(restaurant.getRestaurantName());
                    tw1.setText(restaurant.getLocation());
                    tw2.setText(restaurant.getTelNo());
                    }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });


        Call<List<Reservation>> call1 =  RetrofitClient.getUrls().reservationList(value);
        call1.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(@NonNull Call<List<Reservation>> call,
                                   @NonNull Response<List<Reservation>> response) {
                if (response.isSuccessful()){
                    List<Reservation> reservations = response.body();
                       for(int i=0;i<reservations.size();i++){
                           if(i==ReservationID){

                               tw3.setText(reservations.get(i).getDate());
                               tw4.setText(String.valueOf(reservations.get(i).getTime()));

                           }

                       }


                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Reservation>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"Connection Problem",Toast.LENGTH_SHORT)
                        .show();
            }
        });



        cancelReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelReservation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                    }
                }, 1500);

            }
        });

        updateReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReservationPage.this);

                // set title
                alertDialogBuilder.setTitle("Update Reservation");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure want to Update Reservation.Because this action will delete your reservation.")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                cancelReservation();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent i=new Intent(getApplicationContext(),MakeReservationActivity.class);
                                        i.putExtra("RestaurantID",RestaurantId);
                                        startActivity(i);
                                    }
                                }, 1500);

                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                }
        });


            }


            void cancelReservation() {
                final Intent intent = getIntent();

                int ReservationID = intent.getIntExtra("ReservationId", 0);
                SharedPreferences result = getSharedPreferences("saveDATA", MODE_PRIVATE);
                int value = result.getInt("Value", 0);
                System.out.println(ReservationID + "ssgdfgadfghadfhdhhadhadhf");

                Call<Reservation> call = RetrofitClient.getUrls().cancelReservation(ReservationID);
                call.enqueue(new Callback<Reservation>() {
                    @Override
                    public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Reservation Canceled ", Toast.LENGTH_SHORT).show();


                        }
                    }

                    @Override
                    public void onFailure(Call<Reservation> call, Throwable t) {

                    }
                });
            }


            void updateReservation() {


            }


        }