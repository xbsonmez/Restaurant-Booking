package com.example.tunga.gb;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tunga.gb.model.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    private List<Restaurant> restaurants = new ArrayList<>();
    private Context mContext;


    public RestaurantAdapter(List<Restaurant> restaurants, Context context){
        this.restaurants.addAll(restaurants);
        mContext = context;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }


    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_row_restaurant, viewGroup, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int position) {


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
                    Intent i = new Intent(mContext,RestaurantPage.class);
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


    class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        private List<Restaurant> restaurants = new ArrayList<>();
        String url = "https://im.haberturk.com/2018/02/07/ver1517997640/1827354_620x410.jpg";

        private OnItemClickListener itemClickListener;
        TextView restName;
        TextView restRate;
        ImageView restIMG;
        View view;
        RelativeLayout parentLayout;
        RestaurantAdapter restaurantAdapter;

        RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout  =itemView.findViewById(R.id.recylerView);

            view = itemView;
            this.restIMG = itemView.findViewById(R.id.loadPictureID);
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
            Picasso.get().load(url).resize(90,90).into(restIMG);


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

  class CustomRVItemTouchListener implements RecyclerView.OnItemTouchListener {

    //GestureDetector to intercept touch events
    GestureDetector gestureDetector;
    private OnItemClickListener clickListener;

    public CustomRVItemTouchListener(Context context, final RecyclerView recyclerView, final OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                //find the long pressed view
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onItemClick(child, recyclerView.getChildLayoutPosition(child),false);
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent e) {

        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onItemClick(child, recyclerView.getChildLayoutPosition(child),false);
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}






