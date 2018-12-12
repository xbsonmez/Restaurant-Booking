package com.example.lenovo.glassbookingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.glassbookingapp.Model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private List<Person> persons = new ArrayList<>();
    private Context context;


    public PersonAdapter(List<Person> persons, Context context){
        this.persons.addAll(persons);
        this.context = context;
    }



    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.item_row_person, viewGroup, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int i) {
        System.out.println(i + " <--- i " + persons.size());
        Person person = persons.get(i);
        System.out.println(person.getEmail());
        System.out.println(person.getName());
        personViewHolder.bind(person);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView personName;
        TextView personEmail;
        View view;

        PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            this.personName = itemView.findViewById(R.id.person_name);
            this.personEmail =itemView.findViewById(R.id.person_email);
        }

        void bind(Person person){
            personName.setText(person.getName());
            personEmail.setText(person.getEmail());

        }
    }

}
