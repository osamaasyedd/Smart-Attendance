package com.example.myapplication;

public class Classitem {
    long id;
    String className;
    String courseName;

    public Classitem(long id, String className, String courseName) {
        this.id = id;
        this.className = className;
        this.courseName = courseName;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Classitem(String className, String courseName) {
        this.className = className;
        this.courseName= courseName;


    }
}
