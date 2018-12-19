package com.example.tunga.gb;

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

import com.example.tunga.gb.model.Restaurant;
import com.example.tunga.gb.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AnswerUpdateRestaurantRequestPage extends Fragment {

    View myView;
    RecyclerView recyclerView;
    UpdateRestaurantAdapter restaurantAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView  =inflater.inflate(R.layout.activity_answer_update_restaurant_request_page, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView_update_restaurant_request);
        SharedPreferences result = this.getActivity().getSharedPreferences("saveDATA",MODE_PRIVATE);
        int value = result.getInt("Value",0);

        Call<List<Restaurant>> call =  RetrofitClient.getUrls().getUpdateWaitingRestaurants();
        call.enqueue(new Callback<List<Restaurant>>() {


            @Override
            public void onResponse(@NonNull Call<List<Restaurant>> call,
                                   @NonNull Response<List<Restaurant>> response) {
                if (response.isSuccessful()){
                    List<Restaurant> restaurants = response.body();

                    restaurantAdapter = new UpdateRestaurantAdapter(restaurants, getContext());
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

}
