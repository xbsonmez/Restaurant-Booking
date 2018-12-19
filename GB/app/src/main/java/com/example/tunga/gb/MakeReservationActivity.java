package com.example.tunga.gb;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tunga.gb.retrofit.RetrofitClient;
import com.example.tunga.gb.retrofit.RetrofitUrls;
import com.example.tunga.gb.services.Time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeReservationActivity extends AppCompatActivity {
    DatePicker datePicker;
    Button showAvalibaleTimesButton;
    TextView osman;
    private DatePickerDialog.OnDateSetListener mDatesetListener;

    TextView chooseNumberOfPeople;
    NumberPicker np;
    RetrofitUrls retrofitUrls;
    public int peopleValue=1;
    private RecyclerView recyclerView;
    private TimeAdapter timeAdapter;
    Context context;
    Button reserveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);

        showAvalibaleTimesButton = (Button) findViewById(R.id.show_avaliable_times_button);
        reserveButton = (Button) findViewById(R.id.reserve_button);
        reserveButton.setVisibility(View.INVISIBLE);
        osman = (TextView) findViewById(R.id.osman);
        Date myDate = new Date();
        SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
        String mdy = mdyFormat.format(myDate);
        osman.setText(mdy);

        chooseNumberOfPeople = (TextView) findViewById(R.id.choose_number_of_person_tv);

        retrofitUrls = RetrofitClient.getUrls();

        Intent intent = getIntent();
        final int RestaurantId = intent.getIntExtra("RestaurantID", 0);
        recyclerView = (RecyclerView) findViewById(R.id.recylerView_times_for_reservation);

        osman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MakeReservationActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        mDatesetListener,
                        year, month, day);


                long now = System.currentTimeMillis() - 1000;
                // dp_time.setMinDate(now);
                //dp_time.setMaxDate(now+(1000*60*60*24*7));
                cal.add(Calendar.YEAR, 1);
                dialog.getDatePicker().setMinDate(now);
                dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());


                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });


        mDatesetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;

                String date = month + "-" + dayOfMonth + "-" + year;
                osman.setText(date);
                recyclerView.setVisibility(View.INVISIBLE);
                reserveButton.setVisibility(View.INVISIBLE);

            }
        };

        np = (NumberPicker) findViewById(R.id.number_picker_make_reservation);

        //Set TextView text color
        //chooseNumberOfPeople.setTextColor(Color.parseColor("#ffd32b3b"));

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(1);

        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(20);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected number from picker
                chooseNumberOfPeople.setText("Choose number of people " + newVal);
                recyclerView.setVisibility(View.INVISIBLE);
                reserveButton.setVisibility(View.INVISIBLE);
                peopleValue = newVal;
            }
        });


            showAvalibaleTimesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences result = getSharedPreferences("saveDATA", MODE_PRIVATE);
                    int value = result.getInt("Value", 0);


                    final HashMap<String, String> body = new HashMap<>();
                    body.put("date", osman.getText().toString());
                    body.put("numberOfPeople",String.valueOf(peopleValue));
                    Call<List<Time>> call = RetrofitClient.getUrls().listAvaliableTimes(RestaurantId, body);
                    call.enqueue(new Callback<List<Time>>() {
                        @Override
                        public void onResponse(Call<List<Time>> call, Response<List<Time>> response) {
                            if (response.isSuccessful()) {
                                List<Time> times = response.body();
                                System.out.println("dsgjlsdfkgjsdjkfghsdfklg" + RestaurantId);
                                timeAdapter = new TimeAdapter(times, getApplicationContext());
                                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(manager);
                                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                recyclerView.setAdapter(timeAdapter);
                                System.out.println(response.body().toString());
                                recyclerView.setVisibility(View.VISIBLE);
                                if(times.size()==0){
                                    reserveButton.setVisibility(View.INVISIBLE);

                                }
                                else{
                                    reserveButton.setVisibility(View.VISIBLE);

                                }


                            }
                        }

                        @Override
                        public void onFailure(Call<List<Time>> call, Throwable t) {

                        }
                    });

                }

            });


        reserveButton.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    if(timeAdapter.getClickedTime()!=0){
                        makeReservation();



                    }else{
                        Toast.makeText(getApplicationContext(),"Please Choose Time",Toast.LENGTH_SHORT).show();
                    }

                }
            });}


        public void makeReservation(){
            SharedPreferences result = getSharedPreferences("saveDATA", MODE_PRIVATE);
            final int value = result.getInt("Value", 0);
            Intent intent = getIntent();
            final int RestaurantId = intent.getIntExtra("RestaurantID", 0);
            final HashMap<String,String> body  =new HashMap<>();
            body.put("date",osman.getText().toString());
            body.put("numberOfPeople",String.valueOf(peopleValue));
            body.put("time",String.valueOf(timeAdapter.getClickedTime()));

            Call<ResponseBody> call = retrofitUrls.makeReservation(body,RestaurantId,value);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Reservation OK",Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                            }
                        }, 1500);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Connection Failed",Toast.LENGTH_SHORT).show();

                }
            });

        }




    }



