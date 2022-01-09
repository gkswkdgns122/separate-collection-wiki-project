package com.example.a201501978__wiki;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RecycleDatabaseManager {
    static final String DB_RECYCLE = "Recycled.db";   //DB이름
    static final String TABLE_RECYCLES = "Recycles"; //Table 이름
    static final int DB_VERSION = 1;			//DB 버전

    Context myContext = null;

    private static RecycleDatabaseManager myDBManager = null;
    private SQLiteDatabase mydatabase = null;

    //MovieDatabaseManager 싱글톤 패턴으로 구현
   public static RecycleDatabaseManager getInstance(Context context){
       if(myDBManager == null){
           myDBManager = new RecycleDatabaseManager(context);
       }
       return myDBManager;
   }

    private RecycleDatabaseManager(Context context)
    {
        myContext = context;

        //DB Open
        mydatabase = context.openOrCreateDatabase(DB_RECYCLE, context.MODE_PRIVATE,null);

        //Table 생성
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_RECYCLES +
                "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "category TEXT," +
                "information TEXT);");
    }

    public long insert(ContentValues addRowValue) {
        return mydatabase.insert(TABLE_RECYCLES, null, addRowValue);
    }

    public Cursor query(String sql,
                        String[] selectionArgs
)
    {
        return mydatabase.rawQuery(sql,
                selectionArgs);
    }
}
