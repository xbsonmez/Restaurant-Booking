package com.example.tunga.gb;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tunga.gb.model.Restaurant;
import com.example.tunga.gb.retrofit.RetrofitClient;
import com.example.tunga.gb.retrofit.RetrofitUrls;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRestaurant extends AppCompatActivity {
    private Button button;
    private Restaurant restaurant;
    private RetrofitUrls retrofitUrls;
    private EditText editText;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText = (EditText) findViewById(R.id.restaurant_name);
        editText1 = (EditText) findViewById(R.id.restaurant_location);
        editText2 = (EditText) findViewById(R.id.restaurant_phone);
        editText3 = (EditText) findViewById(R.id.restaurant_peoplecapacity);
        editText4 = (EditText) findViewById(R.id.restaurant_tablecapacity);
        editText5 = (EditText) findViewById(R.id.restaurant_features);
        button = (Button) findViewById(R.id.add_restaurant_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Buttona Basınca Admine REQ gidicek
                String restaurantName=editText.getText().toString();
                String location=editText1.getText().toString();
                String telNo=editText2.getText().toString();
                String capacityOfTable=editText4.getText().toString();
                String capacityOfPeople=editText3.getText().toString();
                String features=editText5.getText().toString();

                addRestaurantRequest(restaurantName,location,telNo,capacityOfTable,capacityOfPeople,features);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        retrofitUrls = RetrofitClient.getUrls();
    }

    private void addRestaurantRequest(String restaurantName,String location,String telNo,String capacityOfTable,String capacityOfPeople,String featuresInfo){


        HashMap<String, String> body = new HashMap<>();
        body.put("telNo",telNo);
        body.put("capacityOfPeople",capacityOfPeople);
        body.put("capacityOfTable",capacityOfTable);
        body.put("ownerID","3");
        body.put("featuresInfo",featuresInfo);
        body.put("location",location );
        body.put("restaurantName", restaurantName );




        Call<ResponseBody> call = retrofitUrls.restaurant(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Restaurant ekleme isteği admine gitti", Toast.LENGTH_SHORT)
                            .show();
                    hideKeyboard();
                    editText.setText("");
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                    editText5.setText("");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,
                                  @NonNull Throwable t) {


            }
        });


    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
