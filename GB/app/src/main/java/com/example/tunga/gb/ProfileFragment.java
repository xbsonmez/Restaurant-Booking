package com.example.tunga.gb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tunga.gb.model.Person;
import com.example.tunga.gb.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.*;

public class ProfileFragment extends Fragment implements OnClickListener{
        View myView;
        Context context;
    //  myView = inflater.inflate(R.layout.profile_layout, container, false);
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    Button logoutButton;
    Button button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.profile_layout, container, false);

        textView = (TextView) myView.findViewById(R.id.profile_page_user_name_tw);
        textView1 = (TextView) myView.findViewById(R.id.profile_page_name_tw);
        textView2 = (TextView) myView.findViewById(R.id.profile_page_email_tw);
        textView3 = (TextView) myView.findViewById(R.id.profile_page_phone_tw);
        logoutButton = (Button) myView.findViewById(R.id.logout_button);
        button=(Button) myView.findViewById(R.id.profile_page_edit_profile_button);

        logoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                // set title
                alertDialogBuilder.setTitle("Your Title");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity

                                saveStringData("visitor");
                                saveData(0);
                                Intent i =new Intent(getActivity(),LoginActivity.class);
                                startActivity(i);

                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EditProfile.class);
                startActivity(i);

            }
        });


     /*   FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        SharedPreferences result = this.getActivity().getSharedPreferences("saveDATA",MODE_PRIVATE);
        int value = result.getInt("Value",0);



        Call<Person> call =  RetrofitClient.getUrls().getPersonDetails(value);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {

                if (response.isSuccessful()) {
                    Person person = response.body();
                    System.out.println(person.toString());
                    textView.setText(person.getUsername());
                    textView1.setText(person.getName());
                    textView2.setText(person.getEmail());
                    textView3.setText(person.getTelno());
                }
            }
            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });

        return myView;}


    public int loadData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Value", MODE_PRIVATE);
        int value = sharedPreferences.getInt("Value", 0);
        return value;
    }

    public void saveStringData(String s){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("saveStringDATA", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Role",s);
        editor.apply();
    }

    public void saveData(int data) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("saveDATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Value", data);

        editor.apply();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set title
                alertDialogBuilder.setTitle("Your Title");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity

                                saveStringData("visitor");
                                saveData(0);
                                Intent i =new Intent(getActivity(),LoginActivity.class);
                                startActivity(i);


                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                break;

            case R.id.profile_page_edit_profile_button:
                Intent i = new Intent(getContext(), EditProfile.class);
                startActivity(i);
                break;
        }




        }


    }




