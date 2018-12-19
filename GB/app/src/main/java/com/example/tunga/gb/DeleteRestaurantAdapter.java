package com.example.tunga.gb;



import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tunga.gb.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class DeleteRestaurantAdapter extends RecyclerView.Adapter<DeleteRestaurantViewHolder> {

    private List<Restaurant> restaurants = new ArrayList<>();
    private Context mContext;


    public DeleteRestaurantAdapter(List<Restaurant> restaurants, Context context){
        this.restaurants.addAll(restaurants);
        mContext = context;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }


    @NonNull
    @Override
    public DeleteRestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_row_restaurant, viewGroup, false);
        return new DeleteRestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteRestaurantViewHolder restaurantViewHolder, int position) {


        final Restaurant restaurant = restaurants.get(position);

        System.out.println(restaurant.getRate());
        restaurantViewHolder.bind(restaurant);

        restaurantViewHolder.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Toast.makeText(mContext,"Long Click: " +restaurants.get(position),Toast.LENGTH_SHORT).show();

                }
                else{
                    Intent i = new Intent(mContext,DeleteRestaurantPage.class);
                    i.putExtra("RestaurantId",restaurants.get(position).restaurantID);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                    Toast.makeText(mContext,"Click: " +restaurants.get(position).restaurantID,Toast.LENGTH_SHORT).show();
                }
                int resID = restaurants.get(position).getRestaurantID();



            }

        });}



    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
}


class DeleteRestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
    private List<Restaurant> restaurants = new ArrayList<>();

    private OnItemClickListener itemClickListener;
    TextView restName;
    TextView restRate;
    View view;
    RelativeLayout parentLayout;
    RestaurantAdapter restaurantAdapter;

    DeleteRestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        parentLayout  =itemView.findViewById(R.id.recylerView);

        view = itemView;
        this.restName = itemView.findViewById(R.id.rv_restaurant_name);
        this.restRate =itemView.findViewById(R.id.rv_restaurant_rate);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }


    public void setItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;

    }


    void bind(Restaurant restaurant){
        restName.setText(restaurant.getRestaurantName());
        restRate.setText(restaurant.getLocation());


    }

    @Override
    public void onClick(View v) {
        itemClickListener.onItemClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onItemClick(v,getAdapterPosition(),true);
        return false;
    }
}









