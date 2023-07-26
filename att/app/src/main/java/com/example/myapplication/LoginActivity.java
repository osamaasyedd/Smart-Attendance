package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextView registration;
    Button login;
    EditText id,pass;
    String userid,password;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        DBHelper dbHelper=new DBHelper(this);

        registration=findViewById(R.id.reg);
        login=findViewById(R.id.btnlogin);
        id=findViewById(R.id.user_id);
        pass=findViewById(R.id.pass);
       // userid=id.getText().toString();
       // password=pass.getText().toString();

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MainActivity2.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid=id.getText().toString();
                password=pass.getText().toString();



                if (userid.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this,"Please Enter The Credentials To Continue...",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean result1=dbHelper.checkStudent(userid,password);
                    Boolean result2=dbHelper.checkTeacher(userid,password);
                //    if (result1==true){
                //        Toast.makeText(LoginActivity.this,"Successfully Logged In As A Student!",Toast.LENGTH_SHORT).show();
                //        Intent i=new Intent(LoginActivity.this,StudentHome.class);
                //        startActivity(i);
                //    }
                    if (result2==true){
                        Toast.makeText(LoginActivity.this,"Successfully Logged In as A Teacher",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this,TeacherHome.class);
                        startActivity(intent);
                    }
                    else if (result1==true){
                        Toast.makeText(LoginActivity.this,"Successfully Logged In As A Student!",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this,StudentProfile.class);
                        intent.putExtra("s_id",userid);
                        startActivity(intent);

                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "ID And password Not Found...",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });




    }


}
