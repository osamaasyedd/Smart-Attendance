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

public class StudentHome extends AppCompatActivity {

    TextView name, id, attendance, percentage;
  //  DBHelper dbHelper;
    String sid;
    String i,n;
    int total_count;
    Button pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

       // DBHelper dbHelper=new DBHelper(this);

        name=findViewById(R.id.n);
        id=findViewById(R.id.id);
        attendance=findViewById(R.id.att);
        percentage=findViewById(R.id.percent);
        pr=findViewById(R.id.pro);
        loadSData();
        count_total();
        findPercentage();
        sid=getIntent().getExtras().getString("s_id");

        pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StudentHome.this,StudentProfile.class);
                i.putExtra("s_id",sid);
                startActivity(i);
            }
        });
    }

    private void loadSData(){
        sid=getIntent().getExtras().getString("s_id");
        DBHelper dbHelper=new DBHelper(this);

        Cursor cursor= dbHelper.getStudentData(sid);
        StringBuilder stringBuilder=new StringBuilder();
        //if (cursor.getCount()==0)
        //{
        //    Toast.makeText(StudentHome.this,"No data exists",Toast.LENGTH_SHORT).show();
        //    return;
        //}
        while (cursor.moveToNext()){
            //i=cursor.getString(0);
            //n=cursor.getString(1);
           // id.setText(i);
           // name.setText(n);
            stringBuilder.append("Hello "+cursor.getString(1)+"!"
                    +"\nRoll: "+cursor.getString(3)+"\nClass: "+cursor.getString(5)+"\nSection: "+cursor.getString(6));

        }
        name.setText(stringBuilder);

    }

    private void count_total(){
        sid=getIntent().getExtras().getString("s_id");
        DBHelper dbHelper=new DBHelper(this);
        StringBuilder stringBuilder=new StringBuilder();
        //stringBuilder.append("Your Attendance"+"\n     "+ dbHelper.countPresent(sid)+"/"+dbHelper.countStudentOccurence(sid)+" days");
        if (dbHelper.countPresent(sid)==0){
            stringBuilder.append("Your Attendance"+"\n     "+"0/0 days");


        }
        else{
            stringBuilder.append("Your Attendance"+"\n     "+ dbHelper.countPresent(sid)+"/"+dbHelper.countStudentOccurence(sid)+" days");
        }
        attendance.setText(stringBuilder);
    }

    public int percent(){
        DBHelper dbHelper=new DBHelper(this);
        sid=getIntent().getExtras().getString("s_id");
        int percent=(dbHelper.countPresent(sid)/ dbHelper.countStudentOccurence(sid))*100;
        return percent;
    }

    private void findPercentage(){
        DBHelper dbHelper=new DBHelper(this);
        sid=getIntent().getExtras().getString("s_id");
        StringBuilder stringBuilder=new StringBuilder();
       // stringBuilder.append((dbHelper.countPresent(sid)/ dbHelper.countStudentOccurence(sid))*100+"%");
        stringBuilder.append(percent()+"%");
        percentage.setText(stringBuilder);

    }



}