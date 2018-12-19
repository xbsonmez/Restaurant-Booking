package com.example.tunga.gb;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tunga.gb.model.Help;
import com.example.tunga.gb.retrofit.RetrofitClient;
import com.example.tunga.gb.retrofit.RetrofitUrls;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {
    private EditText editText;
    private RetrofitUrls retrofitUrls;
    private Help help1;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        editText = (EditText) findViewById(R.id.mail_text);
        button = (Button) findViewById(R.id.send_mail_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailAdress = editText.getText().toString();

                sendMail(mailAdress);
            }
        });

        retrofitUrls = RetrofitClient.getUrls();

    }
    @Override
    public void onBackPressed() {
        finishAffinity();
        //moveTaskToBack(true);
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
                    editText.setText("");

                } else {
                    Toast.makeText(getApplicationContext(), "Wrong email", Toast.LENGTH_SHORT)
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

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}