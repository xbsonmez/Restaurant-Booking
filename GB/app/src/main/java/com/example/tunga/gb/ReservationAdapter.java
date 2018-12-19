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

import com.example.tunga.gb.model.Reservation;

import java.util.ArrayList;
import java.util.List;


public class ReservationAdapter extends RecyclerView.Adapter<ReservationViewHolder> {

    private List<Reservation> reservations = new ArrayList<>();
    private Context mContext;

    public ReservationAdapter(List<Reservation> reservations, Context context){
        this.reservations.addAll(reservations);
        mContext = context;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_row_reservation, viewGroup, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder reservationViewHolder, int i) {
        final Reservation reservation = reservations.get(i);

        reservationViewHolder.bind(reservation);

       reservationViewHolder.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Toast.makeText(mContext,"Long Click: " +reservations.get(position),Toast.LENGTH_SHORT).show();

                }
                else{
                    Intent i = new Intent(mContext,ReservationPage.class);
                    i.putExtra("RestaurantId",reservations.get(position).getRestaurantID());
                    i.putExtra("ReservationId",reservations.get(position).getId());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                    Toast.makeText(mContext,"Click: " +reservations.get(position).restaurantID,Toast.LENGTH_SHORT).show();
                }
                int resID = reservations.get(position).getRestaurantID();
            }
        });
    }



    @Override
    public int getItemCount() {
        return reservations.size();
    }
}

    class ReservationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private List<Reservation> reservations = new ArrayList<>();


        TextView reservationDate;
        TextView reservationTime;
        TextView reservationName;
        View view;
        private OnItemClickListener itemClickListener;
        RelativeLayout parentLayout;


        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            this.reservationName = itemView.findViewById(R.id.reservation_name);
            this.reservationDate = itemView.findViewById(R.id.reservation_date);
            this.reservationTime = itemView.findViewById(R.id.reservation_time);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            }

        @Override
    public void onClick(View v) {itemClickListener.onItemClick(v,getAdapterPosition(),false);

    }
        public void setItemClickListener(OnItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;

        }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onItemClick(v,getAdapterPosition(),true);
        return false;
    }

    void bind(Reservation reservation){
            reservationName.setText("Reservation ID:" +reservation.getId());
            reservationDate.setText("Reservation Date: " + reservation.getDate());
            reservationTime.setText(String.valueOf("Reservation Time: "+reservation.getTime()));


        }


}

