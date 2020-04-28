package com.osos.markup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.osos.markup.Attendance;
import com.osos.markup.Main2Activity;
import com.osos.markup.R;

import java.util.ArrayList;


public class DateAdapter extends  RecyclerView.Adapter<DateAdapter.ClassHolder> {
    Context c;
    ArrayList<String> arrayList;
  Main2Activity boj;


    public DateAdapter(Context c, ArrayList<String> arrayList, Main2Activity obj){
        this.c=c;
        this.arrayList=arrayList;
        boj=obj;
    }

    @NonNull
    @Override
    public ClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(c).inflate(R.layout.classadapter,parent,false);
        return new ClassHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassHolder holder, final int position) {
        holder.obj.setText(arrayList.get(position));



        holder.card.setOnClickListener(new View.onClic );





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    class ClassHolder extends RecyclerView.ViewHolder {

        TextView obj;
        CardView card;
        public ClassHolder(@NonNull View itemView) {
            super(itemView);
            obj=itemView.findViewById(R.id.dateNAme);
            card=itemView.findViewById(R.id.cardView);
        }
    }


}
