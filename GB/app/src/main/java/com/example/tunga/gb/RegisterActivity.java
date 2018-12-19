package com.example.tunga.gb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tunga.gb.model.Person;
import com.example.tunga.gb.services.MessageService;
import com.example.tunga.gb.retrofit.RetrofitClient;
import com.example.tunga.gb.retrofit.RetrofitUrls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText username;
    private EditText nameAndSurname;
    private EditText phoneNumber;
    private EditText passoword;
    private EditText confirmPassoword;
    private RadioButton roleButton;
    private RadioGroup roleGroup;
    private Button saveButton;
    private RetrofitUrls retrofitUrls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.register_email);
        username = (EditText) findViewById(R.id.register_username);
        nameAndSurname = (EditText) findViewById(R.id.register_name);
        phoneNumber = (EditText) findViewById(R.id.register_phone_number);
        passoword = (EditText) findViewById(R.id.register_password);
        confirmPassoword = (EditText) findViewById(R.id.register_confirm_password);
        saveButton = (Button) findViewById(R.id.save_button);
        roleGroup = (RadioGroup) findViewById(R.id.radioGroup);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Person person = new Person();
                person.setEmail(email.getText().toString());
                person.setUsername(username.getText().toString());
                person.setName(nameAndSurname.getText().toString());
                person.setPhoneNumber(phoneNumber.getText().toString());
                person.setPassword(passoword.getText().toString());
                person.setConfirmPassword(confirmPassoword.getText().toString());
                try {
                    int selectedID = roleGroup.getCheckedRadioButtonId();
                    roleButton = (RadioButton) findViewById(selectedID);
                    person.setRole(roleButton.getText().toString());

                    signUp(person);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "please fill in the blank", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });
        retrofitUrls = RetrofitClient.getUrls();


    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        //moveTaskToBack(true);
    }
    public void openLoginActivity() {
        Intent ıntent = new Intent(this, LoginActivity.class);
        startActivity(ıntent);
    }


    public void signUp(final Person p1) {
        System.out.println(p1.toString());
        HashMap<String, String> body = new HashMap<>();

        body.put("name", p1.getName());
        body.put("email", p1.getEmail());
        body.put("username", p1.getUsername());
        body.put("telno", p1.getPhoneNumber());
        body.put("password", p1.getPassword());
        body.put("confirmPassword", p1.getConfirmPassword());
        body.put("role", p1.getRole());


        Call<MessageService> call = retrofitUrls.signUp(body);
        call.enqueue(new Callback<MessageService>() {

            @Override
            public void onResponse(@NonNull Call<MessageService> call, @NonNull Response<MessageService> response) {

                if (response.isSuccessful()) {
                    MessageService message = response.body();
                    Toast.makeText(getApplicationContext(), message.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                    hideKeyboard();

                    openLoginActivity();
                } else {
                    Gson gson = new GsonBuilder().create();
                    MessageService mess = new MessageService();
                    try {
                        mess = gson.fromJson(response.errorBody().string(), MessageService.class);
                        Toast.makeText(getApplicationContext(), mess.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageService> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection Problem!!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }


    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}