package com.example.tunga.gb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tunga.gb.model.Restaurant;
import com.example.tunga.gb.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MyRestaurantsFragment extends Fragment implements View.OnClickListener {
    View myView;
    RecyclerView recyclerView;
    RestaurantAdapter restaurantAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.my_restaurants_layout, container, false);
        Button upButton = myView.findViewById(R.id.button_add);
        upButton.setOnClickListener(this);


        recyclerView = (RecyclerView) myView.findViewById(R.id.rv_my_restaurants);

        SharedPreferences result = this.getActivity().getSharedPreferences("saveDATA",MODE_PRIVATE);
        int value = result.getInt("Value",0);

        Call<List<Restaurant>> call =  RetrofitClient.getUrls().getMyRestaurants(value);
        call.enqueue(new Callback<List<Restaurant>>() {


            @Override
            public void onResponse(@NonNull Call<List<Restaurant>> call,
                                   @NonNull Response<List<Restaurant>> response) {
                if (response.isSuccessful()){
                    List<Restaurant> restaurants = response.body();

                    restaurantAdapter = new RestaurantAdapter(restaurants, getContext());
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(restaurantAdapter);


                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Restaurant>> call,
                                  @NonNull Throwable t) {

            }
        });
        return myView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
                Intent i = new Intent(getActivity(), AddRestaurant.class);
                startActivity(i);
                break;

        }


    }
}
