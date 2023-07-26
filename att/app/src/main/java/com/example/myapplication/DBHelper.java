package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Locale;

class DBHelper extends SQLiteOpenHelper{

    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMNN_PASSWORD = "PASSWORD";
    public static final String TEACHERS_TABLE = "TEACHERS_TABLE";
    public static final String COLUMN_TEACHER_ID = "TEACHER_ID";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_PHONE_NO = "PHONE_NO";
    public static final String STUDENTS_TABLE = "STUDENTS_TABLE";
    public static final String COLUMN_S_ID = "STUDENT_ID";
    public static final String COLUMN_STUDENT_ID = "STUDENT_ID";
    public static final String COLUMN_STUDENT_FNAME = "STUDENT_FNAME";
    public static final String COLUMN_STUDENT_LNAME = "STUDENT_LNAME";
    public static final String COLUMN_STUDENT_ROLL = "STUDENT_ROLL";
    public static final String COLUMN_STUDENT_PASSWORD = "STUDENT_PASSWORD";
    public static final String COLUMN_STUDENT_CLASS = "STUDENT_CLASS";
    public static final String COLUMN_STUDENT_SECTION = "STUDENT_SECTION";
    public static final String CLASS_TABLE = "CLASS_TABLE";
    public static final String COLUMN_C_ID = "CLASS_ID";
    public static final String COLUMN_CLASS_ID = COLUMN_C_ID;
    public static final String COLUMN_CLASS_NAME = "CLASS_NAME";
    public static final String COLUMN_CLASS_SECTION = "CLASS_SECTION";
    public static final String SELECT_CLASS_TABLE= "SELECT * FROM "+CLASS_TABLE;
    public static final String COLUMN_STATUS = "STATUS";
    public static final String STATUS_TABLE = COLUMN_STATUS + "_TABLE";
    public static final String COLUMN_STATUS_ID = COLUMN_STATUS + "_ID";
    public static final String COLUMN_DATE = "DATE";


    // Constructor for our new class
    public DBHelper(@Nullable Context context) {
        super(context, "AS.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creating STUDENT_TABLE
        String createStudentTable= "CREATE TABLE " + STUDENTS_TABLE + "(" + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY, " + COLUMN_STUDENT_FNAME + " TEXT NOT NULL, " + COLUMN_STUDENT_LNAME + " TEXT NOT NULL, " + COLUMN_STUDENT_ROLL + " BLOB, " + COLUMN_STUDENT_PASSWORD + " BLOB, " + COLUMN_STUDENT_CLASS + " BLOB NOT NULL," + COLUMN_STUDENT_SECTION + "  BLOB)";
        db.execSQL(createStudentTable);


        //Creating Teacher's table
        String createTeacherTable= "CREATE TABLE " + TEACHERS_TABLE + "(" + COLUMN_TEACHER_ID + " INTEGER PRIMARY KEY, " + COLUMN_EMAIL + " BLOB, " + COLUMNN_PASSWORD + " BLOB, " + COLUMN_FIRST_NAME + " TEXT, " + COLUMN_LAST_NAME + " TEXT, " + COLUMN_PHONE_NO + " INTEGER)";
        db.execSQL(createTeacherTable);

        //Creating Class table
        String createClassTable= "CREATE TABLE " + CLASS_TABLE + "(" + COLUMN_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_CLASS_NAME + " BLOB NOT NULL, " + COLUMN_CLASS_SECTION + " TEXT NOT NULL)";
        db.execSQL(createClassTable);

        //Creating STATUS_TABLE
        String createStatusTable= "CREATE TABLE " + STATUS_TABLE + "(" + COLUMN_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_S_ID + " BLOB NOT NULL, " + COLUMN_DATE + " TEXT NOT NULL, " + COLUMN_STATUS + " TEXT NOT NULL)";
        db.execSQL(createStatusTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE + "");
        db.execSQL("DROP TABLE IF EXISTS " + TEACHERS_TABLE + "");
        db.execSQL("DROP TABLE IF EXISTS " + CLASS_TABLE + "");
        db.execSQL("DROP TABLE IF EXISTS " + STATUS_TABLE + "");
        onCreate(db);
    }

    public boolean addOne(StudentModel studentModel){
        SQLiteDatabase db= this.getWritableDatabase();

        //Content values will store these values and transfer it to data base
        ContentValues cv= new ContentValues();
        cv.put(COLUMN_STUDENT_ID, studentModel.getID());
        cv.put(COLUMN_STUDENT_ROLL, studentModel.getEmail());
        cv.put(COLUMN_STUDENT_PASSWORD, studentModel.getPassword());
        cv.put(COLUMN_STUDENT_FNAME, studentModel.getName());
        cv.put(COLUMN_STUDENT_LNAME, studentModel.getLname());
        cv.put(COLUMN_STUDENT_CLASS, studentModel.getYear());
        cv.put(COLUMN_STUDENT_SECTION,studentModel.getSection());

        // Insert command
        long insert = db.insert(STUDENTS_TABLE, null, cv);
        if (insert==-1){
            return false;
        }
        else{
            return true;
        }
    }

    //Method to add rows to Teacher's tABLE
    public boolean addRow(TeacherModel teacherModel){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv1=new ContentValues();

        cv1.put(COLUMN_TEACHER_ID, teacherModel.getID());
        cv1.put(COLUMN_EMAIL, teacherModel.getEmail());
        cv1.put(COLUMNN_PASSWORD,teacherModel.getPassword());
        cv1.put(COLUMN_FIRST_NAME,teacherModel.getF_name());
        cv1.put(COLUMN_LAST_NAME,teacherModel.getL_name());
        cv1.put(COLUMN_PHONE_NO,teacherModel.getPh_no());

        long insert = db.insert(TEACHERS_TABLE, null, cv1);
        if (insert==-1){
            return false;
        }
        else {
            return true;
        }
    }

    //Method to add values to CLASS_TABLE
    long addClass(String className, String section){
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_CLASS_NAME, className);
        values.put(COLUMN_CLASS_SECTION, section);
        return database.insert(CLASS_TABLE,null,values);
    }

    //To read CLASS_TABLE rows
    Cursor getClassTable(){
        SQLiteDatabase database= this.getReadableDatabase();
        return  database.rawQuery(SELECT_CLASS_TABLE, null);
    }

    //Method to delete class
    int deleteClass(long id){
        SQLiteDatabase database=this.getReadableDatabase();
        return database.delete(CLASS_TABLE, COLUMN_CLASS_ID+"=?", new String[]{String.valueOf(id)});
    }

    //Method to update values to CLASS_TABLE
    long updateClass(long id, String className, String section){
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_CLASS_NAME, className);
        values.put(COLUMN_CLASS_SECTION, section);
        return database.update(CLASS_TABLE, values, COLUMN_CLASS_ID+"=?", new String[]{String.valueOf(id)});
    }

    //To read from STUDENT_TABLE
    Cursor getStudentTable(String t_class, String s_sec){

        SQLiteDatabase database= this.getReadableDatabase();
        String query= "SELECT * FROM " + STUDENTS_TABLE + " WHERE "+ COLUMN_STUDENT_CLASS + "='" + t_class + "' AND " + COLUMN_STUDENT_SECTION + "='" +  s_sec + "'COLLATE NOCASE ;";
        Cursor cursor= database.rawQuery(query,null);
        return cursor;
    }

    long addStatus(String sid, String date, String status){
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_S_ID,sid);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_STATUS,status);
        return database.insert(STATUS_TABLE,null,values);
    }

    long updateStatus(String sid, String date, String status){
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_STATUS,status);
        String whereClause= COLUMN_DATE +"='"+date+"' AND "+COLUMN_S_ID+"='"+sid+"'";
        return database.update(STATUS_TABLE,values,whereClause,null);

    }

    String getStatus(String sid,String date){
        String status=null;
        SQLiteDatabase database= this.getReadableDatabase();
        String whereClause= COLUMN_DATE +"='"+date+"' AND "+COLUMN_S_ID+"='"+sid+"'";
        Cursor cursor=database.query(STATUS_TABLE,null,whereClause,null,null,null, null);
        if (cursor.moveToFirst())
            status= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS));
        return status;
    }

    //To retrieve bio of student for student interface
    Cursor getStudentData(String sid){
        SQLiteDatabase database= this.getReadableDatabase();
        String query= "SELECT * FROM " + STUDENTS_TABLE + " WHERE "+ COLUMN_STUDENT_ID + "='" + sid + "'";
        Cursor cursor= database.rawQuery(query,null);
        return cursor;
    }

    public int countStudentOccurence(String sid){
        SQLiteDatabase database= this.getReadableDatabase();
      //  String query= "SELECT " + COLUMN_S_ID + "  FROM "
      //          + STATUS_TABLE + " WHERE " +
        String query= "SELECT * FROM "+ STATUS_TABLE + " WHERE " + COLUMN_S_ID + "='" + sid + "'";
        Cursor cursor=database.rawQuery(query,null);
        return cursor.getCount();

    }

    public int countPresent(String sid){
        SQLiteDatabase database= this.getReadableDatabase();
        //  String query= "SELECT " + COLUMN_S_ID + "  FROM "
        //          + STATUS_TABLE + " WHERE " +
        String query= "SELECT * FROM "+ STATUS_TABLE + " WHERE " + COLUMN_S_ID + "='" + sid + "' AND " + COLUMN_STATUS + "= 'P'";
        Cursor cursor=database.rawQuery(query,null);
        return cursor.getCount();

    }

    public Boolean checkStudent(String userid,String password){
        SQLiteDatabase database= this.getReadableDatabase();
        String query= "SELECT * FROM "+ STUDENTS_TABLE + " WHERE " + COLUMN_STUDENT_ID + "='" + userid + "' AND " + COLUMN_STUDENT_PASSWORD + "='" + password +"'";
        Cursor cursor=database.rawQuery(query,null);
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;

        }
    }

    public Boolean checkTeacher(String userid,String password){
        SQLiteDatabase database= this.getReadableDatabase();
        String query= "SELECT * FROM "+ TEACHERS_TABLE + " WHERE " + COLUMN_TEACHER_ID + "='" + userid + "' AND " + COLUMNN_PASSWORD + "='" + password +"'";
        Cursor cursor=database.rawQuery(query,null);
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;

        }
    }

    //Updates Student's details
    public Boolean updateStudentProfile(String id, String fname, String lname, String roll, String password, String clas, String section){
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_STUDENT_ROLL, roll);
        values.put(COLUMN_STUDENT_PASSWORD,password);
        values.put(COLUMN_STUDENT_FNAME,fname);
        values.put(COLUMN_STUDENT_LNAME,lname);
        values.put(COLUMN_STUDENT_CLASS, clas);
        values.put(COLUMN_STUDENT_SECTION,section);

        String whereClause= COLUMN_STUDENT_ID +"='"+id+"'";
        long res=database.update(STUDENTS_TABLE,values,whereClause,null);
        if (res==-1){
            return  false;
        }
        else{
            return true;
        }
    }

    //This method deletes student Profile
    public Boolean deleteStudentProfile(String sid){
        SQLiteDatabase database= this.getWritableDatabase();
        String whereClause= COLUMN_STUDENT_ID +"='"+sid+"'";
        String query="SELECT * FROM "+ STUDENTS_TABLE + " WHERE " + COLUMN_STUDENT_ID + "='" + sid + "'";
        Cursor cursor=database.rawQuery(query,null);
        if (cursor.getCount()>0){
            long result=database.delete(STUDENTS_TABLE,whereClause,null);
            if (result==-1){
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    public Boolean checkSID(String sid){

        SQLiteDatabase database= this.getReadableDatabase();
        String query= "SELECT * FROM "+ STUDENTS_TABLE + " WHERE " + COLUMN_STUDENT_ID + "='" + sid + "'";
        Cursor cursor=database.rawQuery(query,null);
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;

        }
    }

    public Boolean checkTID(String tid){

        SQLiteDatabase database= this.getReadableDatabase();
        String query= "SELECT * FROM "+ TEACHERS_TABLE + " WHERE " + COLUMN_TEACHER_ID + "='" + tid + "'";
        Cursor cursor=database.rawQuery(query,null);
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;

        }
    }
}

