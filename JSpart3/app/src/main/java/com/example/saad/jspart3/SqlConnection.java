package com.example.saad.jspart3;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by saad on 3/10/2017.
 */
public class SqlConnection extends SQLiteOpenHelper  {

    public static final String _database_name = "mydb";
    public static final String _table_name = "MyFavourite";
    public static final String col_1 = "_jobID";
    public static final String col_2 = "_jobTitle";
    public static final String col_3 = "_jobCompany";
    public static final String col_4 = "_jobCountry";
    public static final String col_5 = "_jobCity";
    public static final String col_6 = "_jobPostDate";
    public static final String col_7 = "_jobSource";
    public static final String col_8 = "_jobState";
    public static final String col_9 = "_jobFormattedLocation";
    public static final String col_10 = "_jobSnippet";
    public static final String col_11 = "_jobUrl";
    public static final String col_12 = "_jobLatitude";
    public static final String col_13 = "_jobLongitude";
    public static final String col_14 = "_jobKey";
    public static final String col_15 = "_jobSponsored";
    public static final String col_16 = "_jobExpired";
    public static final String col_17 = "_jobFormattedLocationFull";
    public static final String col_19 = "_jobFormattedRelativeTime";
    public static final String col_20 = "_jobCareer";
    public static final String col_21 = "_jobCategory";
    public static final String col_22 = "_jobQualification";
    public static final String col_23 = "_jobNumber";
    public static final String col_24 = "_jobSalary";
    public static final String col_25 = "_jobSkills";
    public static final String col_26 = "_jobMinimumExperience";
    public static final String col_27 = "_jobMaximumExperience";
    public static final String col_28 = "_jobDepartment";
    public static final String col_29 = "_jobComment";

    public SqlConnection(Context context) {
        super(context, _database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //String query = String.format("Create table %s (%s int primary key autoincrement, %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25), %s varchar(25));" ,_table_name,col_1, col_2, col_3, col_4, col_5, col_6, col_7, col_8, col_9, col_10, col_11, col_12, col_13, col_14, col_15, col_16, col_17, col_19, col_20, col_21, col_22, col_23, col_24, col_25, col_26, col_27, col_28, col_29);
        db.execSQL("Create table "+_table_name+" ("+col_1+" integer primary key autoincrement, "+col_2+" varchar(25), "+col_3+" varchar(25), "+col_4+" varchar(25), "+col_5+" varchar(25), "+col_6+" varchar(25), "+col_7+" varchar(25), "+col_8+" varchar(25), "+col_9+" varchar(25), "+col_10+" varchar(25), "+col_11+" varchar(25), "+col_12+" varchar(25), "+col_13+" varchar(25), "+col_14+" varchar(25), "+col_15+" varchar(25), "+col_16+" varchar(25), "+col_17+" varchar(25), "+col_19+" varchar(25), "+col_20+" varchar(25), "+col_21+" varchar(25), "+col_22+" varchar(25), "+col_23+" varchar(25), "+col_24+" varchar(25), "+col_25+" varchar(25), "+col_26+" varchar(25), "+col_27+" varchar(25), "+col_28+" varchar(25), "+col_29+" varchar(25));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table if exists "+_table_name);
        onCreate(db);
    }
    public boolean insertData( String title, String company, String country, String city, String jobPostDate, String source, String state, String location, String snippet, String url, String lat, String lng, String key, String sponsored, String expired, String loc_full, String realtive_time, String career, String category, String qualification, String number, String salary, String skills, String min_experience, String max_experience, String department, String comment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put(col_1, id);
        cv.put(col_2, title);
        cv.put(col_3, company);
        cv.put(col_4, country);
        cv.put(col_5, city);
        cv.put(col_6, jobPostDate);
        cv.put(col_7, source);
        cv.put(col_8, state);
        cv.put(col_9, location);
        cv.put(col_10, snippet);
        cv.put(col_11, url);
        cv.put(col_12, lat);
        cv.put(col_13, lng);
        cv.put(col_14, key);
        cv.put(col_15, sponsored);
        cv.put(col_16, expired);
        cv.put(col_17, loc_full);
        cv.put(col_19, realtive_time);
        cv.put(col_20, career);
        cv.put(col_21, category);
        cv.put(col_22, qualification);
        cv.put(col_23, number);
        cv.put(col_24, salary);
        cv.put(col_25, skills);
        cv.put(col_26, min_experience);
        cv.put(col_27, max_experience);
        cv.put(col_28, department);
        cv.put(col_29, comment);
        long result = db.insert(_table_name, null, cv);
        if(result != -1){
            return true;
        }
        else {
            return false;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+_table_name+" order by "+col_1+" desc;", null);
        return  result;
    }
    public Integer delData(String data){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer result = db.delete(_table_name, "_jobTitle = ?", new String[] {data});
        return result;
    }
}
