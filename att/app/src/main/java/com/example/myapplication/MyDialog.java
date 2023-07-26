package com.example.myapplication;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {
    public static final String CLASS_ADD_DIALOG="addClass";
    public static final String CLASS_UPDATE_DIALOG="updateClass";
    public static final String STUDENT_ADD_DIALOG="addStudent";

    OnClickListener listener;
    public interface OnClickListener{
        void onClick(String text1, String text2);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog= null;
        if(getTag().equals(CLASS_ADD_DIALOG))dialog=getAddClassDialog();
        if(getTag().equals(STUDENT_ADD_DIALOG))dialog=getAddStudentDialog();
        if(getTag().equals(CLASS_UPDATE_DIALOG))dialog= getUpdateClassDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }

    private Dialog getUpdateClassDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title= view.findViewById(R.id.titleDialog);
        title.setText("Update Class");


        EditText class_edt = view.findViewById(R.id.class_edt);
        EditText course_edt=view.findViewById(R.id.course_edt);

        Button cancel=view.findViewById(R.id.cancel_btn);
        Button add=view.findViewById(R.id.add_btn);
        add.setText("Update");

        cancel.setOnClickListener(v-> dismiss());

        add.setOnClickListener(v-> {
            String className=class_edt.getText().toString();
            String courseName= course_edt.getText().toString();
            listener.onClick(className, courseName);
            dismiss();
        });


        return builder.create();

    }

    private Dialog getAddStudentDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title= view.findViewById(R.id.titleDialog);
        title.setText("Add New Student");


        EditText id_edt = view.findViewById(R.id.class_edt);
        EditText name_edt=view.findViewById(R.id.course_edt);

        id_edt.setHint("Student ID");
        name_edt.setHint("Student Name");

        Button cancel=view.findViewById(R.id.cancel_btn);
        Button add=view.findViewById(R.id.add_btn);

        cancel.setOnClickListener(v-> dismiss());

        add.setOnClickListener(v-> {
            String id=id_edt.getText().toString();
            String name= name_edt.getText().toString();

//            to increment the roll numbers of students automatically
//            id_edt.setText(Integer.parseInt(id)+1);


            listener.onClick(id, name);

        });


        return builder.create();

    }

    private Dialog getAddClassDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title= view.findViewById(R.id.titleDialog);



        EditText class_edt = view.findViewById(R.id.class_edt);
        EditText course_edt=view.findViewById(R.id.course_edt);

        Button cancel=view.findViewById(R.id.cancel_btn);
        Button add=view.findViewById(R.id.add_btn);

        cancel.setOnClickListener(v-> dismiss());

        add.setOnClickListener(v-> {
            String className=class_edt.getText().toString();
            String courseName= course_edt.getText().toString();
            listener.onClick(className, courseName);
            dismiss();
        });


        return builder.create();
    }
}
