package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationT extends AppCompatActivity {

    public Button register_btn;
    public EditText t_id, t_email, t_pass, t_fname, t_lname, t_ph_no;
    String id,fn,ln,mail,pass,pn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_t);

        register_btn = findViewById(R.id.btnRegister);
        t_id = findViewById(R.id.t_id);
        t_email = findViewById(R.id.user_id);
        t_pass = findViewById(R.id.pass);
        t_fname = findViewById(R.id.s_fname);
        t_lname = findViewById(R.id.t_lname);
        t_ph_no = findViewById(R.id.phoneNumber);
        dbHelper=new DBHelper(this);


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=t_id.getText().toString();
                fn=t_fname.getText().toString();
                ln=t_lname.getText().toString();
                mail=t_email.getText().toString();
                pass=t_pass.getText().toString();
                pn=t_ph_no.getText().toString();

                if (id.equals("") || fn.equals("") || ln.equals("") || mail.equals("") || pass.equals("") || pn.equals("")){
                    Toast.makeText(RegistrationT.this,"Please fill in all the entries to continue...",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean res=dbHelper.checkTID(id);
                    if (res==false){
                        TeacherModel teacherModel;
                        teacherModel = new TeacherModel(Integer.parseInt(t_id.getText().toString()), t_email.getText().toString(), t_pass.getText().toString(), t_fname.getText().toString(), t_lname.getText().toString(), Integer.parseInt(t_ph_no.getText().toString()));
                        Toast.makeText(RegistrationT.this, teacherModel.toString(), Toast.LENGTH_SHORT).show();

                        //Reference to database
                        DBHelper dbHelper = new DBHelper(RegistrationT.this);
                        boolean success1 = dbHelper.addRow(teacherModel);
                        Toast.makeText(RegistrationT.this, "You have successfully registered as teacher. Enjoy using your app", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(RegistrationT.this,TeacherHome.class);
                        startActivity(i);


                    }
                    else{
                        Toast.makeText(RegistrationT.this,"This ID Already exists", Toast.LENGTH_SHORT).show();
                    }
                }




                //TeacherModel teacherModel;
                //teacherModel = new TeacherModel(Integer.parseInt(t_id.getText().toString()), t_email.getText().toString(), t_pass.getText().toString(), t_fname.getText().toString(), t_lname.getText().toString(), Integer.parseInt(t_ph_no.getText().toString()));
                //Toast.makeText(RegistrationT.this, teacherModel.toString(), Toast.LENGTH_SHORT).show();

                //Reference to database
                //DBHelper dbHelper = new DBHelper(RegistrationT.this);
                //boolean success1 = dbHelper.addRow(teacherModel);
                //Toast.makeText(RegistrationT.this, "You have successfully registered as teacher. Enjoy using your app", Toast.LENGTH_SHORT).show();
                //Intent i= new Intent(RegistrationT.this,TeacherHome.class);
                //startActivity(i);

            }
        });
    }
}