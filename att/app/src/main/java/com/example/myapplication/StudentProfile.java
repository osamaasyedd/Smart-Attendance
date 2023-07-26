package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StudentProfile extends AppCompatActivity {

    String sid;
    TextView pro;
    Button update;
    EditText fn,ln,roll,pass,cla,sec;
    String f_name,l_name,rn,ps,cl,sc;
    DBHelper dbHelper;
    Button delete,attendance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        sid=getIntent().getExtras().getString("s_id");
        pro=findViewById(R.id.profile);
        update=findViewById(R.id.update);
        fn=findViewById(R.id.fname);
        ln=findViewById(R.id.lname);
        roll=findViewById(R.id.rn);
        pass=findViewById(R.id.pas);
        cla=findViewById(R.id.cla);
        sec=findViewById(R.id.sec);
        delete=findViewById(R.id.delete);
        loadProfile();
        dbHelper=new DBHelper(this);
        attendance=findViewById(R.id.attendance);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_name=fn.getText().toString();
                l_name=ln.getText().toString();
                rn=roll.getText().toString();
                ps=pass.getText().toString();
                cl=cla.getText().toString();
                sc=sec.getText().toString();
                String sid=sid=getIntent().getExtras().getString("s_id");

                Boolean checkupdateData= dbHelper.updateStudentProfile(sid,f_name,l_name,rn,ps,cl,sc);
                if (checkupdateData==true)
                    Toast.makeText(StudentProfile.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(StudentProfile.this,"Not Updated, Try agin...", Toast.LENGTH_SHORT).show();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sid=sid=getIntent().getExtras().getString("s_id");
                Boolean checkDeleteData =dbHelper.deleteStudentProfile(sid);
                if (checkDeleteData==true)
                    Toast.makeText(StudentProfile.this,"Your Account deleted",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(StudentProfile.this,"Not Deleted,Try Again...", Toast.LENGTH_SHORT).show();
            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sid=sid=getIntent().getExtras().getString("s_id");
                if (dbHelper.countStudentOccurence(sid)>0){
                    Intent intent=new Intent(StudentProfile.this,StudentHome.class);
                    intent.putExtra("s_id",sid);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(StudentProfile.this,"You have no Attendance Record Yet",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadProfile(){
        sid=getIntent().getExtras().getString("s_id");
        DBHelper dbHelper=new DBHelper(this);

        Cursor cursor= dbHelper.getStudentData(sid);
        StringBuilder stringBuilder=new StringBuilder();
        while (cursor.moveToNext()){
            stringBuilder.append("ID: "+cursor.getString(0)+"!"
                    +"\nFirstName: "+cursor.getString(1)+"\nLast Name: "+cursor.getString(2)+"\nRoll No.: "+cursor.getString(3)+"\nPassword: "+cursor.getString(4)+"\nClass: "+cursor.getString(5)+"\nSection: "+cursor.getString(6));

        }
        pro.setText(stringBuilder);

    }


}