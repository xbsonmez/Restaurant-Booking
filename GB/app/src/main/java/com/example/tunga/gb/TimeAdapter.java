package com.example.tunga.gb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tunga.gb.services.Time;

import java.util.ArrayList;
import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeViewHolder> {

    private List<Time> times = new ArrayList<>();
    private Context mContext;
    private int selectedPos = RecyclerView.NO_POSITION;
    private int clickedTime;

    public TimeAdapter(List<Time> times, Context context) {
        this.times.addAll(times);
        this.mContext = context;
    }

    public int getClickedTime() {
        return clickedTime;
    }

    public void setClickedTime(int clickedTime) {
        this.clickedTime = clickedTime;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_row_time, viewGroup, false);

        return new TimeViewHolder(view);
    }

    int selectedPosition = -1;
    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder timeViewHolder, final int i) {
        System.out.println();
        Time time = times.get(i);
        timeViewHolder.bind(time);









        timeViewHolder.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Toast.makeText(mContext,"Long Click: " +times.get(position),Toast.LENGTH_SHORT).show();

                }
                else{

                    Toast.makeText(mContext,"Click: " +times.get(position).getTime(),Toast.LENGTH_SHORT).show();

                }

                    clickedTime=times.get(position).getTime();


            }

            });

       /* timeViewHolder.itemView.setSelected(selectedPosition == i);
        timeViewHolder.itemView.setBackgroundColor(Color.parseColor("#00AE2122"));


        if(selectedPosition==i){
            timeViewHolder.itemView.setBackgroundColor(Color.parseColor("#ffAE2122"));

        }
        else{
            timeViewHolder.itemView.setBackgroundColor(Color.parseColor("#00AE2122"));


        }*/



    }

 /*   public void changeColor(TimeViewHolder timeViewHolder,final int i){

        timeViewHolder.itemView.setBackgroundColor(Color.parseColor("#00AE2122"));
        if(selectedPosition==i)
            timeViewHolder.itemView.setBackgroundColor(Color.parseColor("#ffAE2122"));
        else
            timeViewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        timeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=i;
                notifyDataSetChanged();

            }
        });
    }*/



    @Override
    public int getItemCount() {
        return times.size();
    }
}

    class TimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        TextView showTime;
        View view;
        OnItemClickListener itemClickListener;
        TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            this.showTime = itemView.findViewById(R.id.make_reservation_time);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);

        }


        void bind(Time time) {
            showTime.setText(String.valueOf(time.getTime()));

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


        public void setItemClickListener(OnItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;

        }
    }

