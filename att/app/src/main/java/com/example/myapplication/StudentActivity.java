package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.ArrayList;


public class StudentActivity extends AppCompatActivity {

    Toolbar toolbar;
    private  String className;
    private String courseName;
    private  int position;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<StudentItem> studentItems=new ArrayList<>();

    String c_id;

    //ArrayList<String> id,name;
    String id, name;
    DBHelper dbHelper;
    private StudentAdapter s_adapter;
    String t_id,s_sec;
    private MyCalendar calendar;
    private  TextView subtitle;
    String s;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);



        calendar=new MyCalendar();

        Intent intent=getIntent();
        className= intent.getStringExtra("className");
        courseName=intent.getStringExtra("courseName");
        position=intent.getIntExtra("position", -1);

        dbHelper=new DBHelper(this);
        //id=new ArrayList<>();
        //name=new ArrayList<>();


        setToolbar();
        recyclerView=findViewById(R.id.student_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new StudentAdapter(this,studentItems);
        //t_id=getIntent().getExtras().getString("className");
        loadStudents();


        //s_adapter=new StudentAdapter(this, id, name);
        //recyclerView.setAdapter(s_adapter);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> changeStatus(position));
        loadStatusdata();
    }

    private void loadStudents(){
        t_id=getIntent().getExtras().getString("className");
        s_sec=getIntent().getExtras().getString("courseName");
        //s=t_id.toUpperCase();
        Cursor cursor = dbHelper.getStudentTable(t_id, s_sec);
        if (cursor.getCount()==0)
        {
            Toast.makeText(StudentActivity.this,"No data exists",Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()){
            id=cursor.getString(0);
            name=cursor.getString(1);
            //studentItems.addStudent(id,name);
            studentItems.add(new StudentItem(id, name));

        }

    }

    private void changeStatus(int position) {
        String status=studentItems.get(position).getStatus();

        if (status.equals("P"))status="A";
        else  status="P";

        studentItems.get(position).setStatus(status);
        adapter.notifyItemChanged(position);
    }

    private void setToolbar() {

        toolbar= findViewById(R.id.toolbar);
        TextView title=toolbar.findViewById(R.id.title_toolbar);
        subtitle=toolbar.findViewById(R.id.subtitle_toolbar);
       // TextView subtitle=toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back=toolbar.findViewById(R.id.back);
        ImageButton save=toolbar.findViewById(R.id.save);

        save.setOnClickListener(v -> saveStatus());

        title.setText(className);
        subtitle.setText(courseName+" | "+calendar.getDate());
        back.setOnClickListener(v-> onBackPressed());

        toolbar.inflateMenu(R.menu.student_menu);

        toolbar.setOnMenuItemClickListener(menuItem->onMenuItemClick(menuItem));

    }

    //This method will save the status to the STATUS_TABLE as well as update it
    private void saveStatus() {
        for(StudentItem studentItem : studentItems){
            String status= studentItem.getStatus();
            if(status!="P") status="A";
            long value= dbHelper.addStatus(studentItem.getId(),calendar.getDate(),status);
            Toast.makeText(StudentActivity.this,String.valueOf(value),Toast.LENGTH_SHORT).show();
            //if (value==-1)dbHelper.updateStatus(studentItem.getId(),calendar.getDate(),status);
            if (value==-1)dbHelper.updateStatus(studentItem.getId(),calendar.getDate(),status);
        }
    }



    private void loadStatusdata(){
        for(StudentItem studentItem : studentItems){
            String status= dbHelper.getStatus(studentItem.getId(), calendar.getDate());
            if(status!=null)studentItem.setStatus(status);
            else studentItem.setStatus("");
        }
        adapter.notifyDataSetChanged();

    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.add_student){
            showAddStudentDialog();
        }
        else if(menuItem.getItemId()==R.id.show_Calendar){
            showCalendar();
        }
        return true;
    }

    private void showCalendar() {

        calendar.show(getSupportFragmentManager(),"");
        calendar.setOnCalendarOkClickListener(this::onCalendarOkClicked);
    }

    private void onCalendarOkClicked(int year, int month, int day) {
        calendar.setDate(year, month, day);
        subtitle.setText(courseName+" | "+calendar.getDate());
        loadStatusdata();

    }

    private void showAddStudentDialog() {
        MyDialog dialog=new MyDialog();
        dialog.show(getSupportFragmentManager(),MyDialog.STUDENT_ADD_DIALOG);
        dialog.setListener((id, name)->addStudent(id, name));
    }

    private void addStudent(String id, String name) {
        studentItems.add(new StudentItem(id, name));
        adapter.notifyDataSetChanged();
    }
}