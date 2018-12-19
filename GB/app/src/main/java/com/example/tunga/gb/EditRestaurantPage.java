package com.example.tunga.gb;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.tunga.gb.model.Person;
import com.example.tunga.gb.retrofit.RetrofitClient;
import com.example.tunga.gb.retrofit.RetrofitUrls;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRestaurantPage extends AppCompatActivity {

    private Button button;
    private RetrofitUrls retrofitUrls;
    private EditText editText;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    Person ownerPerson = new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        retrofitUrls = RetrofitClient.getUrls();
        final int RestaurantId = intent.getIntExtra("RestaurantId",0);
        //editText.setText();
        editText = (EditText) findViewById(R.id.change_restaurant_name);
        editText1 = (EditText) findViewById(R.id.change_restaurant_phone_number);
        editText2 = (EditText) findViewById(R.id.change_restaurant_features);
        editText3 = (EditText) findViewById(R.id.change_restaurant_table_capacity);
        editText4 = (EditText) findViewById(R.id.change_restaurant_person_capacity);
        button = (Button) findViewById(R.id.restaurant_edit_page_send_for_approval_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String restaurantName = editText.getText().toString();
                String telNo = editText1.getText().toString();
                String features = editText2.getText().toString();
                String capaOfTable = editText3.getText().toString();
                String capacityOfPeople  = editText4.getText().toString();
                

                changeRestaurantInfo(restaurantName,telNo,capacityOfPeople,capaOfTable,features);

            }
        });



        SharedPreferences result = getSharedPreferences("saveDATA",MODE_PRIVATE);
        int value = result.getInt("Value",0);
        Call<com.example.tunga.gb.model.Person> call =  RetrofitClient.getUrls().getPersonDetails(value);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {

                if (response.isSuccessful()) {
                    Person person = response.body();
                    System.out.println(person.toString());
                    ownerPerson.setId(person.getId());
                    ownerPerson.setRole(person.getRole());
                    ownerPerson.setEmail(person.getEmail());
                }
            }
            @Override
            public void onFailure(Call<Person> call, Throwable t) {

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
    }

    private void changeRestaurantInfo(String restaurantName,String telNo,String capacityOfPeople,String capaOfTable,String features){
        Intent intent = getIntent();
        final int RestaurantId = intent.getIntExtra("RestaurantId",0);

        System.out.println(RestaurantId+"fgsdfgdfgwesgdfhqetqertqertqret");
        HashMap<String, String> body = new HashMap<>();
        body.put("restaurantName",restaurantName);
        body.put("telNo",telNo);
        body.put("capacityOfPeople",capacityOfPeople);
        body.put("capacityOfTable",capaOfTable);
        body.put("features" ,String.valueOf(features));
        body.put("personID",String.valueOf(ownerPerson.getId()));
        body.put("role",ownerPerson.getRole());
        body.put("email",ownerPerson.getEmail());
        body.put("restaurantID",String.valueOf(RestaurantId));



        Call<ResponseBody> call1 = retrofitUrls.updateRestaurantInfo(body);

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Restaurant bilgileri başarılı bir şekilde admine gönderildi", Toast.LENGTH_SHORT)
                            .show();
                    hideKeyboard();
                    editText.setText("");
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,
                                  @NonNull Throwable t) {
                hideKeyboard();

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
