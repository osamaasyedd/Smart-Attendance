package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationS extends AppCompatActivity {
    public Button s_register;
    public EditText student_id, email, password, f_name, l_name, year, section;
    String sid,mail,pass,fn,ln,cl,sc;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        s_register=findViewById(R.id.btnRegister);
        f_name=findViewById(R.id.s_fname);
        l_name=findViewById(R.id.s_l_name);
        //depart=findViewById(R.id.d_name);
        email=findViewById(R.id.s_roll);
        student_id=findViewById(R.id.user_id);
        password=findViewById(R.id.pass);
        year=findViewById(R.id.s_class);
        section=findViewById(R.id.section);
        dbHelper=new DBHelper(this);


        s_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sid=student_id.getText().toString();
                mail=email.getText().toString();
                pass=password.getText().toString();
                fn=f_name.getText().toString();
                ln=l_name.getText().toString();
                cl=year.getText().toString();
                sc=section.getText().toString();

                if(sid.equals("") || mail.equals("") || pass.equals("") || fn.equals("") || ln.equals("") || cl.equals("") || sc.equals("")){
                    Toast.makeText(RegistrationS.this,"Please fill all the entries to continue",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean res=dbHelper.checkSID(sid);
                    if (res==false){

                        StudentModel studentModel;
                        studentModel = new StudentModel(Integer.parseInt(student_id.getText().toString()), email.getText().toString(), password.getText().toString(), f_name.getText().toString(), l_name.getText().toString(), year.getText().toString(), section.getText().toString());
                        Toast.makeText(RegistrationS.this, "ADDED", Toast.LENGTH_SHORT).show();


                        // Creating class for reference to database
                        DBHelper dataBaseHelper = new DBHelper(RegistrationS.this);
                        boolean success = dataBaseHelper.addOne(studentModel);
                        Toast.makeText(RegistrationS.this, "You have successfully registered as a student now. Continue using your app", Toast.LENGTH_SHORT).show();
                        sid=student_id.getText().toString();
                        Intent intent=new Intent(RegistrationS.this, StudentProfile.class);
                        intent.putExtra("s_id",String.valueOf(studentModel.getID()));
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(RegistrationS.this,"ID Already exists",Toast.LENGTH_SHORT).show();
                    }
                }


               // StudentModel studentModel;
               // studentModel = new StudentModel(Integer.parseInt(student_id.getText().toString()), email.getText().toString(), password.getText().toString(), f_name.getText().toString(), l_name.getText().toString(), year.getText().toString(), section.getText().toString());
               // Toast.makeText(RegistrationS.this, "ADDED", Toast.LENGTH_SHORT).show();


                // Creating class for reference to database
               // DBHelper dataBaseHelper = new DBHelper(RegistrationS.this);
               // boolean success = dataBaseHelper.addOne(studentModel);
                //Toast.makeText(RegistrationS.this, "You have successfully registered as a student now. Continue using your app", Toast.LENGTH_SHORT).show();
               // sid=student_id.getText().toString();
               // Intent intent=new Intent(RegistrationS.this, StudentProfile.class);
              //  intent.putExtra("s_id",String.valueOf(studentModel.getID()));
               // startActivity(intent);
            }

        });


    }
    //public void lg(View V) {startActivity(new Intent(RegistrationS.this,Login.class));}
   // public void su(View V) {startActivity(new Intent(RegistrationS.this,Login.class));}
}