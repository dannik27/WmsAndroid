package com.patis.wms.android.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by dani0318 on 30.03.2018.
 */

public class LocalData {

    SqliteHelper sqliteHelper;

    public LocalData(SqliteHelper sqliteHelper){
        this.sqliteHelper = sqliteHelper;
    }


    public void remove(String name){
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        db.execSQL("delete from userdata where name = \""+name+"\"");

    }

    public void putString(String name, String value){
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        remove(name);
        db.execSQL("insert into userdata(name, value) values (\""+name+"\", \"" + value + "\")");

    }

    public String getString(String name){
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        String val = null;
        Cursor cursor = db.rawQuery("select value from userdata where name = \""+name+"\"", null);
        if(cursor.moveToFirst()){
            val =  cursor.getString(0);
        }
        cursor.close();

        return val;
    }

    public long getLong(String name){
        String val = getString(name);
        if(val == null){
            return -1;
        }else{
            return Long.valueOf(getString(name));
        }

    }

    public void putLong(String name, long value){
        putString(name, String.valueOf(value));
    }


}
