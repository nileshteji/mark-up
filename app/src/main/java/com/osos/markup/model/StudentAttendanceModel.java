package com.osos.markup.model;

public class StudentAttendanceModel {

    String className;
    String Time;
    String Subject;
    String teacherNumber;
    String Name;
    String RollNumber;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public StudentAttendanceModel(){

    }

    public StudentAttendanceModel(String className, String time, String subject, String teacherNumber, String name, String rollNumber, String date) {
        this.className = className;
        Time = time;
        Subject = subject;
        this.teacherNumber = teacherNumber;
        Name = name;
        RollNumber = rollNumber;
        this.date=date;

    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRollNumber() {
        return RollNumber;
    }

    public void setRollNumber(String rollNumber) {
        RollNumber = rollNumber;
    }
}
