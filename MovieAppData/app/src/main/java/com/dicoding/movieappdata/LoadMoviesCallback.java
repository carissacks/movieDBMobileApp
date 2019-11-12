package com.dicoding.movieappdata;

import android.database.Cursor;

public interface LoadMoviesCallback {
    void postExecute(Cursor movies);
}
