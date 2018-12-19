package com.example.tunga.gb;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tunga.gb.model.Restaurant;
import com.example.tunga.gb.retrofit.RetrofitClient;
import com.example.tunga.gb.retrofit.RetrofitUrls;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    RetrofitUrls retrofitUrls;

    private RecyclerView recyclerView;

    private RestaurantAdapter restaurantAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recylerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        retrofitUrls = RetrofitClient.getUrls();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Shared ID
        SharedPreferences result = getSharedPreferences("saveDATA",MODE_PRIVATE);
        int value = result.getInt("Value",0);
        //shared Role
        SharedPreferences result2 = getSharedPreferences("saveStringDATA", MODE_PRIVATE);
        String roleUser=result2.getString("Role","visitor");

        if(value==0 && roleUser.equals("visitor")){
            navigationView.getMenu().setGroupVisible(R.id.visitor_menu,true);
            navigationView.getMenu().setGroupVisible(R.id.manager_login_menu,false);
            navigationView.getMenu().setGroupVisible(R.id.user_login_menu,false);
            navigationView.getMenu().setGroupVisible(R.id.admin_menu,false);
        }
        else if(roleUser.equals("USER")){

            navigationView.getMenu().setGroupVisible(R.id.visitor_menu,false);
            navigationView.getMenu().setGroupVisible(R.id.manager_login_menu,false);
            navigationView.getMenu().setGroupVisible(R.id.user_login_menu,true);
            navigationView.getMenu().setGroupVisible(R.id.admin_menu,false);

        }

        else if (roleUser.equals("MANAGER")){

            navigationView.getMenu().setGroupVisible(R.id.visitor_menu,false);
            navigationView.getMenu().setGroupVisible(R.id.manager_login_menu,true);
            navigationView.getMenu().setGroupVisible(R.id.user_login_menu,false);
            navigationView.getMenu().setGroupVisible(R.id.admin_menu,false);
        }
        else if(roleUser.equals("ADMIN")){
            navigationView.getMenu().setGroupVisible(R.id.admin_menu,true);
            navigationView.getMenu().setGroupVisible(R.id.visitor_menu,false);
            navigationView.getMenu().setGroupVisible(R.id.manager_login_menu,false);
            navigationView.getMenu().setGroupVisible(R.id.user_login_menu,false);

        }


        recyclerView=(RecyclerView) findViewById(R.id.recylerView);

        Call<List<Restaurant>> call =  RetrofitClient.getUrls().getRestaurant();
        call.enqueue(new Callback<List<Restaurant>>() {
                @Override
                public void onResponse(@NonNull Call<List<Restaurant>> call,
                                   @NonNull Response<List<Restaurant>> response) {
                if (response.isSuccessful()){
                    List<Restaurant> restaurants = response.body();
                    restaurantAdapter = new RestaurantAdapter(restaurants, getApplicationContext());
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(restaurantAdapter);


                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Restaurant>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"Connection Problem",Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
        }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        // Get the SearchView and set the searchable configuration
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {


                System.out.println(s + " bastın");
                HashMap<String, String> body = new HashMap<>();

                    body.put("search",s);

                Call<List<Restaurant>>searchRestaurant= retrofitUrls.searchRestaurant(body);
                searchRestaurant.enqueue(new Callback<List<Restaurant>>() {

                    @Override
                    public void onResponse(@NonNull Call<List<Restaurant>> call,
                                           @NonNull Response<List<Restaurant>> response) {
                        if (response.isSuccessful()){
                            List<Restaurant> restaurants = response.body();


                            restaurantAdapter = new RestaurantAdapter(restaurants, getApplicationContext());
                            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(restaurantAdapter);


                        }

                    }



                    @Override
                    public void onFailure(@NonNull Call<List<Restaurant>> call, @NonNull Throwable t) {

                    }
                });

                // bundan sornra request atılıp bilgi çekilicek

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if(searchView.getQuery().length()==0){
                    Call<List<Restaurant>> call =  RetrofitClient.getUrls().getRestaurant();
                    call.enqueue(new Callback<List<Restaurant>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<Restaurant>> call,
                                               @NonNull Response<List<Restaurant>> response) {
                            if (response.isSuccessful()){
                                List<Restaurant> restaurants = response.body();


                                restaurantAdapter = new RestaurantAdapter(restaurants, getApplicationContext());
                                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(restaurantAdapter);


                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<List<Restaurant>> call,
                                              @NonNull Throwable t) {
                            Toast.makeText(getApplicationContext(),"Connection Problem",Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });

                }
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:


                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_profile_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new ProfileFragment())
                    .commit();
            recyclerView.setVisibility(View.INVISIBLE);
            // Handle the camera action
        }else if(id==R.id.nav_user_profile_layout){
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new ProfileFragment())
                    .commit();
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else if (id == R.id.nav_my_restaurants_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new MyRestaurantsFragment())
                    .commit();
            recyclerView.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_explore_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new ExploreFragment())
                    .commit();
            recyclerView.setVisibility(View.VISIBLE);
        }
        else if(id==R.id.visitor_exp_layout) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            recyclerView.setVisibility(View.VISIBLE);

        }
        else if(id==R.id.nav_user_profile_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new ProfileFragment())
                    .commit();
            recyclerView.setVisibility(View.INVISIBLE);

        }
        else if (id == R.id.nav_user_explore_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new ExploreFragment())
                    .commit();
            recyclerView.setVisibility(View.VISIBLE);
        }else if(id==R.id.profile_admin){
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                    ,new ProfileFragment())
                    .commit();
            recyclerView.setVisibility(View.INVISIBLE);
            }
            else if(id==R.id.admin_add_inbox){
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            ,new AnswerAddRestaurantRequestsPage())
                    .commit();
            recyclerView.setVisibility(View.INVISIBLE);
        }else if(id==R.id.admin_delete_inbox){
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            ,new AnswerDeleteRestaurantRequestPage())
                    .commit();
            recyclerView.setVisibility(View.INVISIBLE);
        }else if(id==R.id.admin_update_inbox){
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            ,new AnswerUpdateRestaurantRequestPage())
                    .commit();
            recyclerView.setVisibility(View.INVISIBLE);}
            else if(id ==R.id.nav_my_reservations_layout){

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            ,new MyReservationsFragment())
                    .commit();
                    recyclerView.setVisibility(View.INVISIBLE);
        } else if (id == R.id.explore_admin) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new ExploreFragment())
                        .commit();
                recyclerView.setVisibility(View.VISIBLE);

        }








      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
