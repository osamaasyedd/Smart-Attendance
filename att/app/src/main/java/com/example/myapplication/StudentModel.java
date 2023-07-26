package com.example.myapplication;

public class StudentModel {

    private Integer ID;
    private String email;
    private String password;
    private String name;
   // private String depart;
    private String year;
    private String section;
    private String lname;

    //Constructors

    public StudentModel(Integer ID, String email, String password, String name, String lname, String year, String section) {
        this.ID = ID;
        this.email = email;
        this.password = password;
        this.name = name;
        //this.depart = depart;
        this.year = year;
        this.section = section;
        this.lname = lname;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}







