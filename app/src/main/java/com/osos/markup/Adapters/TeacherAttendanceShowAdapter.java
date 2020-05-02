package com.osos.markup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osos.markup.R;

import java.util.ArrayList;

public class TeacherAttendanceShowAdapter  extends RecyclerView.Adapter<TeacherAttendanceShowAdapter.Holder>{
Context c;
ArrayList<String> list;

public TeacherAttendanceShowAdapter(Context c, ArrayList<String> list){
    this.c=c;
    this.list=list;

}
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View v= LayoutInflater.from(c).inflate(R.layout.teacher_attendance_show,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
    String a=list.get(position);
    String[] array=new String[3];
    array=a.split(" ");
    holder.number.setText(String.valueOf(array[0]));
    holder.RollNumber.setText(String.valueOf(array[1]));
    holder.Name.setText(String.valueOf(array[2]));

    }

    @Override
    public int getItemCount() {
    return list.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView number,RollNumber,Name;
        public Holder(@NonNull View itemView) {
            super(itemView);
            number=itemView.findViewById(R.id.textView10);
            RollNumber=itemView.findViewById(R.id.textView9);
            Name=itemView.findViewById(R.id.textView8);
        }
    }
}
