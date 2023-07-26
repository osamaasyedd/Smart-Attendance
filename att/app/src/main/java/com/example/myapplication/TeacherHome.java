package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TeacherHome extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    classAdapter classAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Classitem> classitems=new ArrayList<>();
    Toolbar toolbar;
    DBHelper dbHelper;


    EditText class_edt,course_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        dbHelper=new DBHelper(this);

        fab=(FloatingActionButton) findViewById(R.id.fab_main);
        fab.setOnClickListener(view -> showDialog());

        loadData();

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        classAdapter= new classAdapter(this, classitems);
        recyclerView.setAdapter(classAdapter);
        classAdapter.setOnItemClickListener(position ->gotoItemActivity(position));

        setToolbar();
        String c_id;

    }

    //this method loads the data related to classes on Teacher's home activity
    private void loadData() {
        Cursor cursor= dbHelper.getClassTable();
        classitems.clear();
        while (cursor.moveToNext()){
            int id= cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLASS_ID));
            String className=cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLASS_NAME));
            String section=cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CLASS_SECTION));

            classitems.add(new Classitem(id,className,section));

        }

    }

    private void setToolbar() {

        toolbar= findViewById(R.id.toolbar);
        TextView title=toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle=toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back=toolbar.findViewById(R.id.back);
        ImageButton save=toolbar.findViewById(R.id.save);

        title.setText("Smart Attendance App");
        subtitle.setVisibility(View.GONE);
        back.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);

    }

    private void gotoItemActivity(int position) {
        Intent intent = new Intent(this, StudentActivity.class);

        intent.putExtra("className", classitems.get(position).getClassName());
        intent.putExtra("courseName", classitems.get(position).getCourseName());
        //Classid k liye intent idhar
        intent.putExtra("class_id",String.valueOf(classitems.get(position).getId()));

        intent.putExtra("position", position);
        startActivity(intent);
    }

    private void showDialog(){
        MyDialog dialog=new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.CLASS_ADD_DIALOG);
        dialog.setListener((className, courseName) -> addClass(className,courseName));


    }

    //This method stores the class entry in the database
    private void addClass(String className, String courseName) {
        long id= dbHelper.addClass(className,courseName);
        Classitem classitem=new Classitem(id, className,courseName);
        classitems.add(classitem);
        classAdapter.notifyDataSetChanged();
    }

    //For  Editing and deleting class row
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0:                                  //This case is about editing class row
                showUpdateDialog(item.getGroupId());
                break;
            case 1:                                  //This case is about deleting the class row
                deleteClass(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position) {
        MyDialog dialog=new MyDialog();
        dialog.show(getSupportFragmentManager(),MyDialog.CLASS_UPDATE_DIALOG);
        dialog.setListener((className, section)-> updateClass(position, className, section));
    }

    //This method updates the class entry
    private void updateClass(int position, String className, String section) {

        dbHelper.updateClass(classitems.get(position).getId(),className,section);
        classitems.get(position).setClassName(className);
        classitems.get(position).setCourseName(section);
        classAdapter.notifyItemChanged(position);
    }

    //This method deletes the class selected
    private void deleteClass(int position) {
        //Will delete from database
        dbHelper.deleteClass(classitems.get(position).getId());
        //will remove from UI
        classitems.remove(position);
        classAdapter.notifyItemRemoved(position);

    }
}