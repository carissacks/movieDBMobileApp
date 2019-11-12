//package com.dicoding.movieandtvshow.db;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class DatabaseHelper extends SQLiteOpenHelper {
//    public static String DATABASE_NAME= "dbMovie";
//    private static final int DATABASE_VERSION= 1;
//    private static final String SQL_CREATER_TABLE_NOTE= String.format("CREATE TABLE %s"
//        + "(%s INTEGER PRIMARY KEY," +
//            "%s TEXT NOT NULL,"+
//            "%s TEXT NOT NULL)",
//            DatabaseContract.TABLE_MOVIE,
//            DatabaseContract.MovieColumns.ID,
//            DatabaseContract.MovieColumns.TITLE,
//            DatabaseContract.MovieColumns.DESCRIPTION
//    );
//
//    public DatabaseHelper(Context context){
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(SQL_CREATER_TABLE_NOTE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DatabaseContract.TABLE_MOVIE);
//        onCreate(sqLiteDatabase);
//    }
//}
