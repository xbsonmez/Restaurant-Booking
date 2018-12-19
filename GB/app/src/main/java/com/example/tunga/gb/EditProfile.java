package com.example.tunga.gb;

import android.app.Activity;
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

public class EditProfile extends AppCompatActivity {
    private Button button;
    private RetrofitUrls retrofitUrls;
    private EditText editText;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    RestaurantAdapter restaurantAdapter;
    Person person=new Person();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText = (EditText) findViewById(R.id.change_user_name);
        editText1 = (EditText) findViewById(R.id.change_phone_number);
        editText2 = (EditText) findViewById(R.id.change_password);
        editText3 = (EditText) findViewById(R.id.change_confirm_password);
        button = (Button) findViewById(R.id.edit_user_info_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username=editText.getText().toString();
                String phoneNumber=editText1.getText().toString();
                String password=editText2.getText().toString();
                String confirmPassword=editText3.getText().toString();

                changeUserInfo(username, phoneNumber, password, confirmPassword);



                }
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        retrofitUrls = RetrofitClient.getUrls();

        SharedPreferences result = getSharedPreferences("saveDATA",MODE_PRIVATE);
        int value = result.getInt("Value",0);


        Call<Person> getPersonDetails=retrofitUrls.getPersonDetails(value);
        getPersonDetails.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if(response.isSuccessful()){
                    Person p1=response.body();
                    person.setName(p1.getName());
                    person.setPassword(p1.getPassword());
                    person.setEmail(p1.getEmail());
                    person.setId(p1.getId());
                    person.setRole(p1.getRole());
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });


    }

    private void changeUserInfo(String username,String phoneNumber,String password,String confirmPassword){







        HashMap<String, String> body = new HashMap<>();
        body.put("username",username);
        body.put("telno",phoneNumber);
        body.put("password",password);
        body.put("confirmPassword",confirmPassword);
        body.put("name" ,person.getName());
        body.put("id", String.valueOf(person.getId()));
        body.put("role",person.getRole());
        body.put("email",person.getEmail());




        Call<ResponseBody> call1 = retrofitUrls.updateUserInfo(body);

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Kullanıcı bilgileri başarılı bir şekilde değiştirildi", Toast.LENGTH_SHORT)
                            .show();
                    hideKeyboard();
                    editText.setText("");
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");

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
