package com.example.lenovo.glassbookingapp;

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

import com.example.lenovo.glassbookingapp.Model.Person;
import com.example.lenovo.glassbookingapp.retrofit.RetrofitClient;
import com.example.lenovo.glassbookingapp.retrofit.RetrofitUrls;

import java.util.HashMap;

import okhttp3.ResponseBody;
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

    private  Button saveButton;
    private RetrofitUrls retrofitUrls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=(EditText)findViewById(R.id.editText4);
        username=(EditText)findViewById(R.id.editText5);
        nameAndSurname=(EditText)findViewById(R.id.editText6);
        phoneNumber=(EditText)findViewById(R.id.editText8);
        passoword=(EditText)findViewById(R.id.editText9);
        confirmPassoword=(EditText)findViewById(R.id.editText10);
        saveButton=(Button)findViewById(R.id.button7);

        roleGroup=(RadioGroup)findViewById(R.id.radioGroup);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Person person=new Person();
                person.setEmail(email.getText().toString());
                person.setUsername(username.getText().toString());
                person.setName(nameAndSurname.getText().toString());
                person.setPhoneNumber(phoneNumber.getText().toString());
                person.setPassword(passoword.getText().toString());
                person.setConfirmPassword(confirmPassoword.getText().toString());

                int selectedID=roleGroup.getCheckedRadioButtonId();
                roleButton=(RadioButton)findViewById(selectedID);
                person.setRole(roleButton.getText().toString());


                 signUp(person);



            }
        });
        retrofitUrls = RetrofitClient.getUrls();
    }
    public void openLoginActivity() {
        Intent ıntent = new Intent(this, LoginActivity.class);
        startActivity(ıntent);
    }

    public void signUp(final Person p1){
        System.out.println(p1.toString());
        HashMap<String, String> body = new HashMap<>();
        body.put("name",p1.getName());
        body.put("email",p1.getEmail());
        body.put("username",p1.getUsername());
        body.put("telno",p1.getPhoneNumber());
        body.put("password",p1.getPassword());
        body.put("confirmPassword",p1.getConfirmPassword());
        body.put("role",p1.getRole());

        Call<ResponseBody> call =retrofitUrls.signUp(body);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Successfully!!", Toast.LENGTH_SHORT)
                            .show();
                    hideKeyboard();
                    sendMail(p1.getEmail());
                    openLoginActivity();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Failure!!",Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Connection Problem!!",Toast.LENGTH_SHORT)
                        .show();
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

    private void sendMail(String email) {
        System.out.println(email);

        HashMap<String, String> body = new HashMap<>();
        body.put("email", email);


        Call<ResponseBody> call = retrofitUrls.help(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Successfully!!", Toast.LENGTH_SHORT)
                            .show();
                    hideKeyboard();


                }else{
                    Toast.makeText(getApplicationContext(),"Wrong email",Toast.LENGTH_SHORT)
                            .show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,
                                  @NonNull Throwable t) {

                Toast.makeText(getApplicationContext(), "Connection Problem!!", Toast.LENGTH_SHORT)
                        .show();

            }
        });


    }


}
