package com.patis.wms.android.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "local.db";
    private static final int VERSION = 40;

    Context context;


    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        runScript("schema.sql", db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);


    }

    public void runScript(String filename, SQLiteDatabase db){


        try (InputStreamReader isr = new InputStreamReader(context.getAssets().open(filename));
             BufferedReader reader  = new BufferedReader(isr)){

            String mLine;
            while ((mLine = reader.readLine()) != null) {

                if((!mLine.equals(""))&&(!mLine.startsWith("--"))){
                    db.execSQL(mLine);
                }

            }
        } catch (IOException ignored) {}
    }
}
