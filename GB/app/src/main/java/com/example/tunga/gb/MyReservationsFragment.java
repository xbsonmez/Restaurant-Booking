package com.example.tunga.gb;

import android.content.Context;
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
import android.widget.Toast;

import com.example.tunga.gb.model.Reservation;
import com.example.tunga.gb.model.Restaurant;
import com.example.tunga.gb.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MyReservationsFragment extends Fragment {

    View myView;
    private RecyclerView recyclerView;
    Context mContext;
    private ReservationAdapter reservationAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_my_reservations_fragment,container,false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView_reservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SharedPreferences result = this.getActivity().getSharedPreferences("saveDATA",MODE_PRIVATE);
        int value = result.getInt("Value",0);

        Call<List<Reservation>> call =  RetrofitClient.getUrls().reservationList(value);
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(@NonNull Call<List<Reservation>> call,
                                   @NonNull Response<List<Reservation>> response) {
                if (response.isSuccessful()){
                    List<Reservation> reservations = response.body();
                    reservationAdapter = new ReservationAdapter(reservations,getActivity());
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(reservationAdapter);


                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Reservation>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(getActivity(),"Connection Problem",Toast.LENGTH_SHORT)
                        .show();
            }
        });

        return myView;


    }

    public void reservations(){


    }


}
