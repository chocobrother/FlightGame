package com.example.juhwan.pokeflight;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by Juhwan on 2017-05-30.
 */

 class DBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "PlayerScores.db";
    public static final String PlAYER_COLUMN_NAME = "name";
    public static final String PlAYER_COLUMN_ID = "id";
    public static final String PlAYER_COLUMN_SCORE = "score";
    private static final int DATABASE_VERSION = 2;

//  public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE scores"+ "(_id INTEGER PRIMARY KEY,name TEXT,score TEXT);");
        db.execSQL("CREATE TABLE scores (_id INTEGER PRIMARY KEY" + " AUTOINCREMENT,name TEXT,score TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST scores");
        onCreate(db);
    }

    public boolean insertScore(String name, String score){
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("INSERT INTO scores VALUES(null, '" + name + "', " + score + ");");
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",name);
        contentValues.put("score",score);

        db.insert("scores",null,contentValues);

        return true;
        //db.close();
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from scores where _id=" + id + "",null);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,PlAYER_COLUMN_NAME);
        return numRows;
    }

    public boolean updateScores(Integer id,String name,String score) {
        SQLiteDatabase db = this.getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("score",score);
        db.update("scores",contentValues,"_id=?",new String[]{Integer.toString(id)});
        //db.execSQL("UPDATE scores SET score=" + score + " WHERE name='" + name + "';");
//        db.close();
        return true;
    }

public void deleteScores(String name){
    SQLiteDatabase db = this.getWritableDatabase();
//    return db.delete("scores","_id = ?",new String[]{Integer.toString(id)});
     db.execSQL("DELETE FROM scores WHERE name='" + name + "';");

    //전체삭제  return db.delete("scores",null,null);
}
    public Integer deleteAll(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("scores",null,null);
    }

    public ArrayList getAllScores(){
        ArrayList array_List = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from scores",null);
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_List.add(res.getString(res.getColumnIndex(PlAYER_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_List;
    }
}
