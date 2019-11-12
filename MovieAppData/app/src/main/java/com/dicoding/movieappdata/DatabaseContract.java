package com.dicoding.movieappdata;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    // Authority yang digunakan
    private static final String AUTHORITY = "com.dicoding.movieandtvshow";
//    private static final String SCHEME = "content";

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static class MovieColumns implements BaseColumns {
        public static final String ID = "id";
        //Note title
        public static final String TITLE = "title";
        //Note description
        public static final String DESCRIPTION = "description";
        //Note date
        public static final String POSTER = "poster";
        private static final String TABLE_NAME = "movie";
        // Base content yang digunakan untuk akses content provider
        public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();

//        public static final Uri CONTENT_URI_MANUAL = Uri.parse("content://com.dicoding.movieandtvshow/movie");
    }
}
