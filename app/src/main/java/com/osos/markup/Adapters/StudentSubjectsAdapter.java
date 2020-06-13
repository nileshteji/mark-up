package com.osos.markup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.osos.markup.R;
import com.osos.markup.ui.StudentClass1;

import java.util.ArrayList;

public class StudentSubjectsAdapter extends RecyclerView.Adapter<StudentSubjectsAdapter.StudentSubjectHolder> {

    StudentClass1 studentClass1;
    ArrayList<String> list;
    Context c;

    public StudentSubjectsAdapter(Context c, ArrayList<String> list, StudentClass1 studentClass1) {
        this.studentClass1 = studentClass1;
        this.list = list;
        this.c = c;
    }


    @NonNull
    @Override
    public StudentSubjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.classadapter, parent, false);

        return new StudentSubjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentSubjectHolder holder, final int position) {
        holder.obj.setText(list.get(position));
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentClass1.onAttendance(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class StudentSubjectHolder extends RecyclerView.ViewHolder {
        TextView obj;
        CardView card;

        public StudentSubjectHolder(@NonNull View itemView) {
            super(itemView);
            obj = itemView.findViewById(R.id.dateNAme);
            card = itemView.findViewById(R.id.cardView);
        }
    }
}
