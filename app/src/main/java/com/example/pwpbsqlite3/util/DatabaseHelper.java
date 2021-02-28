package com.example.pwpbsqlite3.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pwpbsqlite3.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DB_VERSION=1;
    private static final String DB_NAME="notes_app";
    private static final String TABLE_NAME="notes";
    private static final String KEY_DATE="date";
    private static final String KEY_TITLE="title";
    private static final String KEY_BODY="body";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d("fadil", "hai");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "Create Table "+TABLE_NAME+ "(" + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_TITLE + " TEXT," + KEY_BODY + " TEXT," +  KEY_DATE + " TEXT" + ")";
        db.execSQL(createUserTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql=("drop table if exists " +TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(NoteModel note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_DATE, note.date);
        values.put(KEY_TITLE, note.title);
        values.put(KEY_BODY, note.body);

        db.insert(TABLE_NAME,null, values);
    }

    public ArrayList<NoteModel> selectAll() {
        ArrayList<NoteModel> notes = new ArrayList<NoteModel>();
        SQLiteDatabase db= getReadableDatabase();

        String[] columns={"id", KEY_DATE, KEY_TITLE, KEY_BODY};

        Cursor c = db.query(TABLE_NAME,columns,null,null,null,null,null);
        while (c.moveToNext()){
            int id = c.getInt(0);
            String date = c.getString(1);
            String title = c.getString(2);
            String body = c.getString(3);

            NoteModel note = new NoteModel();
            note.id = id;
            note.date = date;
            note.title = title;
            note.body = body;

            notes.add(note);
        }
        return notes;
    }

    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id" + "=" + id;
        db.delete(TABLE_NAME,whereClause,null);
    }

    public void update(NoteModel note){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, note.date);
        values.put(KEY_TITLE, note.title);
        values.put(KEY_BODY, note.body);

        String whereClause = "id" + "=" + note.id;
        db.update(TABLE_NAME,values,whereClause,null);
    }

}