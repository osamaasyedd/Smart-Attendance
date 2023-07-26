package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void student(View v) {
        startActivity(new Intent(MainActivity2.this,RegistrationS.class));
    }

    public void teacher(View v) {
        startActivity(new Intent(MainActivity2.this, RegistrationT.class));
    }
}