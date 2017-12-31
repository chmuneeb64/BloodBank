package com.example.muneeb.bloodbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Muneeb on 14/12/2017.
 */

public class DataBase {

    private DataBaseHelper dataBaseHelper;
    ModelClass modelClass;

    // context of activity
    private Context context;

    //Object of sqlite
    private SQLiteDatabase database;

    //constructor
    public DataBase(Context c) {
        context = c;
    }

    // Function to open Database
    public DataBase open() throws SQLException {
        dataBaseHelper = new DataBaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        //    database = dataBaseHelper.getReadableDatabase();
        return this;
    }

    // Function to Close Database
    public void close() {
        dataBaseHelper.close();
    }

    // For insertion in database
   /* public void insert(String name, String email, String passsword, String phone, String city,
                       String age, String bloodGroup, String gender) {

        ContentValues contentValue = new ContentValues();

        contentValue.put(DataBaseHelper.KEY_NAME, name);
        contentValue.put(DataBaseHelper.KEY_EMAIL, email);
        contentValue.put(DataBaseHelper.KEY_PASSWORD, passsword);
        contentValue.put(DataBaseHelper.KEY_PHONE, phone);
        contentValue.put(DataBaseHelper.KEY_CITY, city);
        contentValue.put(DataBaseHelper.KEY_AGE, age);
        contentValue.put(DataBaseHelper.KEY_BLOODGROUP, bloodGroup);
        contentValue.put(DataBaseHelper.KEY_GENDER, gender);

        long result = database.insert(DataBaseHelper.TABLE_NAME, null, contentValue);
    }*/

    public void insert(ModelClass modelClass) {

        ContentValues contentValue = new ContentValues();

        contentValue.put(DataBaseHelper.KEY_NAME, modelClass.getName());
        contentValue.put(DataBaseHelper.KEY_EMAIL, modelClass.getEmail());
        contentValue.put(DataBaseHelper.KEY_PASSWORD, modelClass.getPassword());
        contentValue.put(DataBaseHelper.KEY_PHONE, modelClass.getPhone());
        contentValue.put(DataBaseHelper.KEY_CITY, modelClass.getCity());
        contentValue.put(DataBaseHelper.KEY_AGE, modelClass.getAge());
        contentValue.put(DataBaseHelper.KEY_BLOODGROUP, modelClass.getBloodGroup());
        contentValue.put(DataBaseHelper.KEY_GENDER, modelClass.getGender());

        long result = database.insert(DataBaseHelper.TABLE_NAME, null, contentValue);
    }

    public int update(String key_id, ModelClass modelClass) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DataBaseHelper.KEY_NAME, modelClass.getName());
        contentValues.put(DataBaseHelper.KEY_EMAIL, modelClass.getEmail());
        contentValues.put(DataBaseHelper.KEY_PASSWORD, modelClass.getPassword());
        contentValues.put(DataBaseHelper.KEY_PHONE, modelClass.getPhone());
        contentValues.put(DataBaseHelper.KEY_CITY, modelClass.getCity());
        contentValues.put(DataBaseHelper.KEY_AGE, modelClass.getAge());
        contentValues.put(DataBaseHelper.KEY_BLOODGROUP, modelClass.getBloodGroup());
        contentValues.put(DataBaseHelper.KEY_GENDER, modelClass.getGender());

        int i = database.update(DataBaseHelper.TABLE_NAME, contentValues,
                DataBaseHelper.KEY_ID + " = " + key_id, null);
        return i;
    }

    public Cursor fetch() {

        String[] columns = new String[]{DataBaseHelper.KEY_ID, DataBaseHelper.KEY_NAME, DataBaseHelper.KEY_EMAIL,
                DataBaseHelper.KEY_PASSWORD, DataBaseHelper.KEY_PHONE, DataBaseHelper.KEY_CITY, DataBaseHelper.KEY_AGE,
                DataBaseHelper.KEY_BLOODGROUP, DataBaseHelper.KEY_GENDER};

        Cursor cursor = database.query(DataBaseHelper.TABLE_NAME, columns, null, null, null, null, null);
/*
        cursor.moveToFirst();
        ArrayList<String> daata  =  new ArrayList<>();

        int iRow = cursor.getColumnIndex(DataBaseHelper.KEY_ID);
        int iName = cursor.getColumnIndex(DataBaseHelper.KEY_NAME);
        int iEmail = cursor.getColumnIndex(DataBaseHelper.KEY_EMAIL);
        int iPassword = cursor.getColumnIndex(DataBaseHelper.KEY_PASSWORD);
        int iPhone = cursor.getColumnIndex(DataBaseHelper.KEY_PHONE);
        int iCity = cursor.getColumnIndex(DataBaseHelper.KEY_CITY);
        int iAge = cursor.getColumnIndex(DataBaseHelper.KEY_AGE);
        int iBloodGroup = cursor.getColumnIndex(DataBaseHelper.KEY_BLOODGROUP);
        int iGender = cursor.getColumnIndex(DataBaseHelper.KEY_GENDER);

        int i = 0;
        do
        {
            if (cursor != null) {

                i++;
                daata.add(cursor.getString(iRow));
                daata.add(cursor.getString(iName));
                daata.add(cursor.getString(iEmail));
                daata.add(cursor.getString(iPassword));
                daata.add(cursor.getString(iPhone));
                daata.add(cursor.getString(iCity));
                daata.add(cursor.getString(iAge));
                daata.add(cursor.getString(iBloodGroup));
                daata.add(cursor.getString(iGender));


            }
        } while(cursor.moveToNext());*/

        return cursor;

     /*
        String fResult = "";

        int iRow = cursor.getColumnIndex(DataBaseHelper.KEY_ID);
        int iName = cursor.getColumnIndex(DataBaseHelper.KEY_NAME);
        int iEmail = cursor.getColumnIndex(DataBaseHelper.KEY_EMAIL);
        int iPassword = cursor.getColumnIndex(DataBaseHelper.KEY_PASSWORD);
        int iPhone = cursor.getColumnIndex(DataBaseHelper.KEY_PHONE);
        int iCity = cursor.getColumnIndex(DataBaseHelper.KEY_CITY);
        int iAge = cursor.getColumnIndex(DataBaseHelper.KEY_AGE);
        int iBloodGroup = cursor.getColumnIndex(DataBaseHelper.KEY_BLOODGROUP);
        int iGender = cursor.getColumnIndex(DataBaseHelper.KEY_GENDER);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){

            fResult = fResult + cursor.getString(iRow) + "" + cursor.getString(iName) + "" + cursor.getString(iEmail) + ""
                    + cursor.getString(iPassword) + "" + cursor.getString(iPhone) + "\n" + cursor.getString(iCity) + ""
                    + cursor.getString(iAge) + "" + cursor.getString(iBloodGroup) + "" + cursor.getString(iGender);
        }
        return fResult;*/
    }

    public String searchPass(String email) {

        database = dataBaseHelper.getReadableDatabase();
        //  String query = "SELECT email, password FROM "+DataBaseHelper.TABLE_NAME +"WHERE email = "+email+" AND password = ";
        String query = "SELECT email, password From " + DataBaseHelper.TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);

        int iEmail = cursor.getColumnIndex(DataBaseHelper.KEY_EMAIL);
        int iPassword = cursor.getColumnIndex(DataBaseHelper.KEY_PASSWORD);
        String a, b;
        b = "not found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(iEmail);
                if (a.equals(email)) {
                    b = cursor.getString(iPassword);
                    break;
                }
            } while (cursor.moveToNext());
        }
        return b;
    }

    public ArrayList<ModelClass> searchData(String email) {
        String query = "SELECT * From " + DataBaseHelper.TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);

        int iKey = cursor.getColumnIndex(DataBaseHelper.KEY_ID);
        int iName = cursor.getColumnIndex(DataBaseHelper.KEY_NAME);
        int iEmail = cursor.getColumnIndex(DataBaseHelper.KEY_EMAIL);
        int iPassword = cursor.getColumnIndex(DataBaseHelper.KEY_PASSWORD);
        int iPhone = cursor.getColumnIndex(DataBaseHelper.KEY_PHONE);
        int iCity = cursor.getColumnIndex(DataBaseHelper.KEY_CITY);
        int iAge = cursor.getColumnIndex(DataBaseHelper.KEY_AGE);
        int iBloodGroup = cursor.getColumnIndex(DataBaseHelper.KEY_BLOODGROUP);
        int iGender = cursor.getColumnIndex(DataBaseHelper.KEY_GENDER);

        ArrayList<ModelClass> data = new ArrayList<>();
        String a;
        if (cursor.moveToFirst()) {
            do {
                modelClass = new ModelClass();

                a = cursor.getString(iEmail);

                if (a.equals(email)) {

                    modelClass.setId(cursor.getString(iKey));
                    modelClass.setName(cursor.getString(iName));
                    modelClass.setEmail(cursor.getString(iEmail));
                    modelClass.setPassword(cursor.getString(iPassword));
                    modelClass.setPhone(cursor.getString(iPhone));
                    modelClass.setCity(cursor.getString(iCity));
                    modelClass.setAge(cursor.getString(iAge));
                    modelClass.setBloodGroup(cursor.getString(iBloodGroup));
                    modelClass.setGender(cursor.getString(iGender));

                    data.add(modelClass);


                   /* data.add(cursor.getString(iKey));
                    data.add(cursor.getString(iName));
                    data.add(cursor.getString(iEmail));
                    data.add(cursor.getString(iPassword));
                    data.add(cursor.getString(iPhone));
                    data.add(cursor.getString(iCity));
                    data.add(cursor.getString(iAge));
                    data.add(cursor.getString(iBloodGroup));
                    data.add(cursor.getString(iGender));*/
                    break;
                }
            } while (cursor.moveToNext());
        }
        return data;
    }

    public ArrayList<ModelClass> receiverSearch(String genderValue, String bloodGroupValue) {

        String query = "SELECT * From " + DataBaseHelper.TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);

        int iKey = cursor.getColumnIndex(DataBaseHelper.KEY_ID);
        int iName = cursor.getColumnIndex(DataBaseHelper.KEY_NAME);
        int iEmail = cursor.getColumnIndex(DataBaseHelper.KEY_EMAIL);
        int iPassword = cursor.getColumnIndex(DataBaseHelper.KEY_PASSWORD);
        int iPhone = cursor.getColumnIndex(DataBaseHelper.KEY_PHONE);
        int iCity = cursor.getColumnIndex(DataBaseHelper.KEY_CITY);
        int iAge = cursor.getColumnIndex(DataBaseHelper.KEY_AGE);
        int iBloodGroup = cursor.getColumnIndex(DataBaseHelper.KEY_BLOODGROUP);
        int iGender = cursor.getColumnIndex(DataBaseHelper.KEY_GENDER);

        ArrayList<ModelClass> data = new ArrayList<>();
        String a, b;
        cursor.moveToFirst();
        int i = 0;
        do {
            modelClass = new ModelClass();

            a = cursor.getString(iGender);
            b = cursor.getString(iBloodGroup);

            if (a.equals(genderValue)){
                if ( b.equals(bloodGroupValue)){
                i++;
                modelClass = new ModelClass();

                modelClass.setName(cursor.getString(iName));
                modelClass.setEmail(cursor.getString(iEmail));
                modelClass.setPhone(cursor.getString(iPhone));
                modelClass.setCity(cursor.getString(iCity));
                modelClass.setAge(cursor.getString(iAge));
                modelClass.setBloodGroup(cursor.getString(iBloodGroup));
                modelClass.setGender(cursor.getString(iGender));
                modelClass.setImg(R.drawable.avatar3);
                data.add(modelClass);
            }
            }

        } while (cursor.moveToNext());

        return data;
    }
}
