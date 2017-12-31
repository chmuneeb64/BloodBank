package com.example.muneeb.bloodbank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Muneeb on 14/12/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "BLOODBANK.DB";

    static final int DATABASE_VERSION = 1;

    static final String TABLE_NAME = "Donor";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_CITY = "city";
    public static final String KEY_AGE = "age";
    public static final String KEY_BLOODGROUP = "bloodGroup";
    public static final String KEY_GENDER = "gender";

    // DDL data defining language.
    String CREATE_DONOR_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
            + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT," + KEY_PHONE + " TEXT,"
            + KEY_CITY + " TEXT," + KEY_AGE + " TEXT," + KEY_BLOODGROUP + " TEXT," + KEY_GENDER + " TEXT" + ")";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_DONOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
