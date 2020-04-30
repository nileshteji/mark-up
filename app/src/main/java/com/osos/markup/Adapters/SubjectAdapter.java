package com.osos.markup.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.osos.markup.R;
import com.osos.markup.SubjectActivity;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectHolder> {
    Context c;
    ArrayList<String> arrayList;
    SubjectActivity subjectActivity;

    public SubjectAdapter(Context c, ArrayList<String> arrayList,SubjectActivity subjectActivity) {
      this.c=c;
      this.arrayList=arrayList;
      this.subjectActivity= subjectActivity;

    }


    @NonNull
    @Override
    public SubjectAdapter.SubjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.classadapter,parent,false);

        return new SubjectHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.SubjectHolder holder, int position) {

        holder.obj.setText(arrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class SubjectHolder extends RecyclerView.ViewHolder{
        TextView obj;
        CardView card;
        public SubjectHolder(@NonNull View itemView) {
            super(itemView);
            obj=itemView.findViewById(R.id.dateNAme);
            card=itemView.findViewById(R.id.cardView);
        }
    }
}













