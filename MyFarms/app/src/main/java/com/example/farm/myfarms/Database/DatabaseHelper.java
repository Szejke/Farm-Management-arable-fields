package com.example.farm.myfarms.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cotam on 24.10.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "plow_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "NAME";
    private static final String COL3 = "SOWING";
    private static final String COL4 = "STATUS";
    private static final String COL5 = "AREA";
    private static final String COL6 = "LOCALITY";
    private static final String COL7 = "PROVINCE";
    private static final String COL8 = "LAT";
    private static final String COL9 = "LNG";
    private static final String COL10 = "CLASSGRAIN";
    private static final String TABLE_NAME2 = "notification";
    private static final String COLN = "DATA";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "NAME" + " TEXT, "+
                "SOWING" + " TEXT, "+
                "STATUS" + " TEXT, "+
                "AREA" + " REAL, "+
                "LOCALITY" + " TEXT, "+
                "PROVINCE" + " TEXT, "+
                "LAT" + " TEXT, "+
                "LNG" + " TEXT, "+
                "CLASSGRAIN" + " TEXT)";
        db.execSQL(createTable);

        String createTable2 = "CREATE TABLE " + TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL2 + " TEXT, " +
                COLN + " TEXT)";
        db.execSQL(createTable2);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public boolean saveDane(String name ,String sowing, String status ,String area , String locality, String province , String lat, String lng ,String classgrain  ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        double areaMeasurement = Double.parseDouble(area);
        cv.put(COL2, name);
        cv.put(COL3, sowing);
        cv.put(COL4, status);
        cv.put(COL5, areaMeasurement);
        cv.put(COL6, locality);
        cv.put(COL7, province);
        cv.put(COL8, lat);
        cv.put(COL9, lng);
        cv.put(COL10, classgrain);
        if ( db.insert(TABLE_NAME,null , cv) == -1){
            return false;
        }else return true;
    }



    public boolean deleteNotff(int numer){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        return  db.delete(TABLE_NAME2,COL1 + "=" + numer, null) >0;

    }
    public boolean setNotific(String name, String data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2, name);
        cv.put(COLN, data);
        if ( db.insert(TABLE_NAME2,null , cv) == -1){
            return false;
        }else return true;
    }

    public Cursor  getNotifica(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    /**
     * Returns all the data from database
     * @return
     */
    public Cursor  getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public SQLiteCursor getProvinces(int numer){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor  kursor = (SQLiteCursor) db.rawQuery("SELECT "+ COL7 +" FROM " + TABLE_NAME + " WHERE ID="+ numer , null);
        return  kursor;
    }



    public SQLiteCursor cordslat(int numer){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor  kursor = (SQLiteCursor) db.rawQuery("SELECT LAT FROM " + TABLE_NAME + " WHERE ID="+ numer , null);
        return  kursor;
    }

    public SQLiteCursor cordslng(int numer){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor  kursor = (SQLiteCursor) db.rawQuery("SELECT LNG FROM " + TABLE_NAME + " WHERE ID="+ numer , null);
        return  kursor;
    }


    public void usunBaze(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE " + TABLE_NAME );
    }

    public SQLiteCursor wyswietlnazw(int numer){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor  kursor = (SQLiteCursor) db.rawQuery("SELECT NAME FROM " + TABLE_NAME + " WHERE ID="+ numer , null);
        return  kursor;
    }

    public SQLiteCursor wyswietlZasiane(int numer) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor  kursor = (SQLiteCursor) db.rawQuery("SELECT SOWING FROM " + TABLE_NAME + " WHERE ID="+ numer , null);
        return  kursor;
    }

    public SQLiteCursor wyswietlStatus(int numer) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor  kursor = (SQLiteCursor) db.rawQuery("SELECT STATUS FROM " + TABLE_NAME + " WHERE ID="+ numer , null);
        return  kursor;
    }
}
