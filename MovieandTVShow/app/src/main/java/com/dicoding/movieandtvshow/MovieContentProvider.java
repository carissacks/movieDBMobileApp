package com.dicoding.movieandtvshow;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dicoding.movieandtvshow.db.AppDatabase;
import com.dicoding.movieandtvshow.db.MovieDatabase;
import com.dicoding.movieandtvshow.db.MovieDatabaseDAO;

public class MovieContentProvider extends ContentProvider {
    public static final String AUTHORITY="com.dicoding.movieandtvshow";
//    public static final Uri URI_MOVIE= Uri.parse("content://"+AUTHORITY+"/"+ MovieDatabase.TABLE_NAME);

    //match some items in table
    private static final int CODE_MOVIE_DIR=1;
    //match code for an item
//    private static final int CODE_MOVIE_ITEM=2;

    private static final UriMatcher MATCHER= new UriMatcher(UriMatcher.NO_MATCH);

    static{
        MATCHER.addURI(AUTHORITY, MovieDatabase.TABLE_NAME, CODE_MOVIE_DIR);
//        MATCHER.addURI(AUTHORITY, MovieDatabase.TABLE_NAME, CODE_MOVIE_ITEM);
    }
    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder){
//        final Context context= getContext();
//        MovieDatabaseDAO movieDatabaseDAO = AppDatabase.getDatabase(context).movieDao();

//        Cursor cursor = movieDatabaseDAO.selectAll();

//        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
//        builder.setTables(MovieDatabase.TABLE_NAME);
//        String QUERY = builder.buildQuery(projection, selection, null, null, sortOrder, null);
//
//        Cursor cursor = AppDatabase.getDatabase(context).getOpenHelper().getWritableDatabase().query(QUERY, selectionArgs);
//        cursor.setNotificationUri(context.getContentResolver(), uri);

//        return cursor;
        final int code= MATCHER.match(uri);
        Log.e("MATCHER", uri.getEncodedPath());
        Log.e("MATCHER", uri.getPath());
        if(code== CODE_MOVIE_DIR){
            final Context context= getContext();
            if(context==null){
                return null;
            }
            MovieDatabaseDAO movieDatabaseDAO= AppDatabase.getDatabase(context).movieDao();
            final Cursor cursor;
//            if(code==CODE_MOVIE_DIR){
            cursor= movieDatabaseDAO.selectAll();
//            }else{
//                cursor= movieDatabaseDAO.selectById(ContentUris.parseId(uri));
//            }
            cursor.setNotificationUri(context.getContentResolver(),uri);
            return cursor;
        }else{
            throw new IllegalArgumentException("Unknown URI: "+uri);
        }
    }

    @Override
    public String getType(Uri uri) {
//        switch (MATCHER.match(uri)){
//            case CODE_MOVIE_DIR:
//                return ""
//        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }


}
