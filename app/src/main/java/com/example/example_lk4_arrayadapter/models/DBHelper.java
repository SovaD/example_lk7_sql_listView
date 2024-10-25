package com.example.example_lk4_arrayadapter.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "userdb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(" +
                "_id INTEGER primary key autoincrement," +
                "name TEXT not null," +
                "email TEXT not null," +
                "image TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
        onCreate(db);
    }

    public Boolean insertUser(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", person.name);
        contentValues.put("email", person.email);
        contentValues.put("image", person.image);

        long result = db.insert("users", null, contentValues);
        if (result == -1)
            return false;
        else return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users", null);
        return cursor;
    }
    public boolean checkData(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email=?", new String[]{email});
       return  cursor.getCount()>0?true:false;
    }

    public Cursor getData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where _id = ?", new String[]{id});
        return cursor;
    }
    public List<Person> getListData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users",null);
        List<Person> people = new ArrayList<>();

        while(cursor.moveToNext()){
            Person person = new Person(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
            people.add(person);
        }
        return people;
    }

    public Boolean updateUsers(String id, String name, String email, String image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("image", image);

        Cursor cursor = db.rawQuery("select * from users where _id = ?",
                new String[]{id});
        if (cursor.getCount() > 0) {
            long result = db.update("users", contentValues,
                    "_id=?", new String[]{id});
            if (result == -1)
                return false;
            else return true;
        }
        return false;
    }

    public Boolean deleteUsers(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where _id = ?",
                new String[]{id});
        if (cursor.getCount() > 0) {
            long result = db.delete("users",
                    "_id=?", new String[]{id});
            if (result == -1)
                return false;
            else return true;
        }
        return false;
    }


}
