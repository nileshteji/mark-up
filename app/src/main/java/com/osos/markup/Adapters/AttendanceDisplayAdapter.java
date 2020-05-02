package com.osos.markup.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.osos.markup.R;
import com.osos.markup.model.StudentAttendanceModel;

import java.util.ArrayList;

public class AttendanceDisplayAdapter extends RecyclerView.Adapter<AttendanceDisplayAdapter.Holder> {
    Context c;
    ArrayList<StudentAttendanceModel> list;

  public   AttendanceDisplayAdapter(Context c,ArrayList<StudentAttendanceModel> list){
      this.c=c;
      this.list=list;
  }



    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.student_attendance_display,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
      holder.date.setText(list.get(position).getDate());
      holder.subject.setText(list.get(position).getSubject());
      holder.TeacherUserName.setText(list.get(position).getTeacherNumber());
      holder.time.setText(list.get(position).getTime());
      holder.Name.setText(list.get(position).getName());
      holder.RollNumber.setText(list.get(position).getRollNumber());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView date,subject,TeacherUserName,time,Name,RollNumber;
        public Holder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            subject=itemView.findViewById(R.id.textView11);
            TeacherUserName=itemView.findViewById(R.id.textView10);
            time=itemView.findViewById(R.id.time);
            Name=itemView.findViewById(R.id.textView8);
            RollNumber=itemView.findViewById(R.id.textView9);
        }
    }
}
