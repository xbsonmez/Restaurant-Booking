package com.example.lenovo.glassbookingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
   private Button button;
   private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        button=(Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
        button2=(Button) findViewById(R.id.button4);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgetActivity();
            }
        });
    }
    public void openForgetActivity(){
        Intent ıntent =new Intent(this,ForgetPassword.class);
        startActivity(ıntent);
    }
     public  void openRegisterActivity(){
         Intent ıntent =new Intent(this,RegisterActivity.class);
         startActivity(ıntent);
     }

}
