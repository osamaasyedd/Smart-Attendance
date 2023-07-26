package com.example.myapplication;

public class TeacherModel {
    private Integer ID;
    private String email;
    private String password;
    private String f_name;
    private String l_name;
    private Integer ph_no;

    public TeacherModel(Integer ID, String email, String password, String f_name, String l_name, Integer ph_no) {
        this.ID = ID;
        this.email = email;
        this.password = password;
        this.f_name = f_name;
        this.l_name = l_name;
        this.ph_no = ph_no;
    }

    //to string method


    @Override
    public String toString() {
        return "TeacherModel{" +
                "ID=" + ID +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                ", ph_no=" + ph_no +
                '}';
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

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public Integer getPh_no() {
        return ph_no;
    }

    public void setPh_no(Integer ph_no) {
        this.ph_no = ph_no;
    }
}
