package com.example.tunga.gb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyRestaurantsFragment extends Fragment implements View.OnClickListener {
    View myView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.my_restaurants_layout, container, false);
        Button upButton = myView.findViewById(R.id.button_add);
        upButton.setOnClickListener(this);
        Button upButton2=myView.findViewById(R.id.button_delete);
        upButton2.setOnClickListener(this);
        return myView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
                Intent i = new Intent(getActivity(), AddRestaurant.class);
                startActivity(i);
                break;
            case R.id.button_delete:
                    Intent i2=new Intent(getActivity(),DeleteRestaurant.class);
                    startActivity(i2);
                    break;
        }


    }
}
