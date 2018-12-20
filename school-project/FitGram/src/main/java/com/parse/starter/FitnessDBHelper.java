package com.parse.starter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.parse.starter.FitnessContract.*;

public class FitnessDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FITNESSLIST.db";
    public static final int DATABASE_VERSION = 1;

    public FitnessDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FITNESSLIST_TABLE = "CREATE TABLE " +
              FitnessEntry.TABLE_NAME + " (" +
                FitnessEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FitnessEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                FitnessEntry.COLUMN_AMOUNT + " INTEGER NOT NULL, " +
                FitnessEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
               ");";

        db.execSQL(SQL_CREATE_FITNESSLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FitnessEntry.TABLE_NAME);
        onCreate(db);

    }
}
