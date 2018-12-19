package com.example.tunga.gb;

import android.support.v7.app.AppCompatActivity;

public class SourceForProfile extends AppCompatActivity  {
    /*TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    Button logoutButton;
    Button button;

    final Context context  =this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_for_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView) findViewById(R.id.profile_page_user_name_tw);
        textView1 = (TextView) findViewById(R.id.profile_page_name_tw);
        textView2 = (TextView) findViewById(R.id.profile_page_email_tw);
        textView3 = (TextView) findViewById(R.id.profile_page_phone_tw);
        logoutButton = (Button) findViewById(R.id.logout_button);
        button=(Button) findViewById(R.id.profile_page_edit_profile_button);


       logoutButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

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
                               Intent i =new Intent(getApplicationContext(),LoginActivity.class);
                               startActivity(i);
                               SourceForProfile.this.finish();
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




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.profile_page_edit_profile_button:
                        Intent i = new Intent(getApplicationContext(), EditProfile.class);
                        startActivity(i);
                        break;
                }

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        Call<Person> call =  RetrofitClient.getUrls().getPersonDetails(3);
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

    public void saveData(int data) {
        SharedPreferences sharedPreferences = getSharedPreferences("saveDATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Value", data);

        editor.apply();


    }
*/


}
