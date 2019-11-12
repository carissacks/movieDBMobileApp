//package com.dicoding.movieandtvshow.db;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.dicoding.movieandtvshow.model.Movie;
//
//import java.util.ArrayList;
//
//import static com.dicoding.movieandtvshow.db.DatabaseContract.MovieColumns.DESCRIPTION;
//import static com.dicoding.movieandtvshow.db.DatabaseContract.MovieColumns.ID;
//import static com.dicoding.movieandtvshow.db.DatabaseContract.MovieColumns.TITLE;
//import static com.dicoding.movieandtvshow.db.DatabaseContract.TABLE_MOVIE;
//
//public class MovieHelper {
//    private static final String DATABASE_TABLE= TABLE_MOVIE;
//    private static DatabaseHelper databaseHelper;
//    private static MovieHelper INSTANCE;
//
//    private static SQLiteDatabase database;
//
//    private MovieHelper(Context context){
//        databaseHelper= new DatabaseHelper(context);
//    }
//    public static MovieHelper getInstance(Context context){
//        if(INSTANCE==null){
//            synchronized (SQLiteOpenHelper.class){
//                if(INSTANCE==null){
//                    INSTANCE= new MovieHelper(context);
//                }
//            }
//        }
//        return INSTANCE;
//    }
//    public void open() throws SQLException{
//        database= databaseHelper.getWritableDatabase();
//    }
//
//    public void close(){
//        databaseHelper.close();
//        if(database.isOpen())
//            database.close();
//    }
//
//    public ArrayList<Movie> getAllMovies(){
//        ArrayList<Movie> arrayList= new ArrayList<>();
//        Cursor cursor= database.query(DATABASE_TABLE, null,
//                null,
//                null,
//                null,
//                null,
//                ID+"ASC",
//                null);
//        cursor.moveToFirst();
//        Movie movie;
//        if(cursor.getCount()>0){
//            do{
//                movie= new Movie();
//                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
//                movie.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
//
//                arrayList.add(movie);
//                cursor.moveToNext();
//            } while(!cursor.isAfterLast());
//        }
//        cursor.close();
//        return arrayList;
//    }
//
//    public long insertMovie(Movie movie){
//        ContentValues args= new ContentValues();
//        args.put(TITLE, movie.getTitle());
//        args.put(DESCRIPTION, movie.getDescription());
////        args.put(ID, movie)
//        return database.insert(DATABASE_TABLE, null, args);
//    }
//
//    public int updateMovie (Movie movie){
//        ContentValues args= new ContentValues();
//        args.put(TITLE, movie.getTitle());
//        args.put(DESCRIPTION, movie.getDescription());
//        return database.update(DATABASE_TABLE, args, ID+" = '"+1+"'", null);
//    }
//
//    public int deleteMovie(int id){
//        return database.delete(TABLE_MOVIE, ID+" = '"+1+"'", null);
//    }
//}
