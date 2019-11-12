package com.dicoding.movieappdata;

import android.database.Cursor;

import com.dicoding.movieappdata.model.MovieDatabase;

import java.util.ArrayList;

import static com.dicoding.movieappdata.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.dicoding.movieappdata.DatabaseContract.MovieColumns.ID;
import static com.dicoding.movieappdata.DatabaseContract.MovieColumns.POSTER;
import static com.dicoding.movieappdata.DatabaseContract.MovieColumns.TITLE;

public class MappingHelper {
    public static ArrayList<MovieDatabase> mapCursorToArrayList(Cursor movieCursor) {
        ArrayList<MovieDatabase> movieList = new ArrayList<>();

        while (movieCursor.moveToNext()) {
            long id = movieCursor.getLong(movieCursor.getColumnIndexOrThrow(ID));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(TITLE));
            String description = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DESCRIPTION));
            String poster = movieCursor.getString(movieCursor.getColumnIndexOrThrow(POSTER));
            movieList.add(new MovieDatabase(id, title, description, poster));
        }

        return movieList;
    }
}
