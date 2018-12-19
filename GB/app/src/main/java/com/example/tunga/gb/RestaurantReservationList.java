package com.example.tunga.gb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tunga.gb.model.Reservation;
import com.example.tunga.gb.model.Restaurant;
import com.example.tunga.gb.retrofit.RetrofitClient;
import com.example.tunga.gb.retrofit.RetrofitUrls;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantReservationList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter reservationAdapter;
    RetrofitUrls retrofitUrls;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_reservation_list);

        recyclerView = findViewById(R.id.recyclerView_restaurant_reservation_list);
        retrofitUrls = RetrofitClient.getUrls();
        showReservationListMethod();

    }

    public void showReservationListMethod(){
        Intent intent = getIntent();
        int RestaurantId = intent.getIntExtra("RestaurantId", 0);
        Call<List<Reservation>> call = RetrofitClient.getUrls().showMyRestaurantReservations(RestaurantId);

        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if(response.isSuccessful()){
                    List<Reservation> reservations= response.body();
                    reservationAdapter = new ReservationAdapter(reservations,getApplicationContext());
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(reservationAdapter);




                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {

            }
        });


    }
}
