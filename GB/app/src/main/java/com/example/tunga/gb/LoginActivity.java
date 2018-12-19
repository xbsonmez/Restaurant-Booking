package com.example.tunga.gb;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tunga.gb.model.Person;
import com.example.tunga.gb.retrofit.RetrofitClient;
import com.example.tunga.gb.retrofit.RetrofitUrls;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ImageView logo;
    private Button button;
    private Button button2;
    private Button buttonLogin;
    private RetrofitUrls retrofitUrls;
    private EditText username;
    private EditText password;
    private Button visitorButton;


    public int sharedID;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        button = (Button) findViewById(R.id.sign_up_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
        button2 = (Button) findViewById(R.id.forget_password_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgetActivity();
            }
        });
        visitorButton=(Button) findViewById(R.id.visitor_button);
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        buttonLogin = (Button) findViewById(R.id.login_button);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String pass = password.getText().toString();

                login(userName, pass);


            }
        });

        visitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });



        retrofitUrls = RetrofitClient.getUrls();
        int imageResource = getResources().getIdentifier("@drawable/gb", null, this.getPackageName());
        logo = (ImageView) findViewById(R.id.imageView);
        logo.setImageResource(imageResource);

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        //moveTaskToBack(true);
    }

    public void openForgetActivity() {
        Intent ıntent = new Intent(this, ForgetPassword.class);
        startActivity(ıntent);
    }

    public void openRegisterActivity() {
         Intent ıntent = new Intent(this, RegisterActivity.class);
      startActivity(ıntent);
    }

    public void openMainActivity() {
        Intent ıntent = new Intent(this, MainActivity.class);
        startActivity(ıntent);
    }


    public void login(String username, String pass) {
        System.out.println(username + pass);
        HashMap<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", pass);


        Call<Person> call = retrofitUrls.login(body);
        call.enqueue(new Callback<Person>() {


            @Override
            public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Successfully!!", Toast.LENGTH_SHORT)
                            .show();
                    hideKeyboard();
                    Person person = response.body();
                    sharedID = person.getId();
                    saveData(sharedID);
                    String sharedRole=person.getRole();
                    saveStringData(sharedRole);
                    System.out.println("TEEESADSADA::::" + sharedID);
                    openMainActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong password or username!!", Toast.LENGTH_SHORT)
                            .show();
                }
            }


            @Override
            public void onFailure(@NonNull Call<Person> call, @NonNull Throwable t) {
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

    public void saveData(int data) {
        SharedPreferences sharedPreferences = getSharedPreferences("saveDATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Value", data);

        editor.apply();


    }

    public int loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Value", MODE_PRIVATE);
        int value = sharedPreferences.getInt("Value", 0);
        return value;
    }

    public void saveStringData(String s){
        SharedPreferences sharedPreferences = getSharedPreferences("saveStringDATA", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Role",s);
        editor.apply();
    }

    /*boolean flag = true;
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if(flag)
            Toast.makeText(getApplicationContext(), "start", Toast.LENGTH_SHORT).show();
        else
        {
            Toast.makeText(getApplicationContext(), "Restart 2", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            finish();
            startActivity(i);

        }
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Toast.makeText(getApplicationContext(), "Restart", Toast.LENGTH_SHORT).show();
        flag = false;
    }*/


}