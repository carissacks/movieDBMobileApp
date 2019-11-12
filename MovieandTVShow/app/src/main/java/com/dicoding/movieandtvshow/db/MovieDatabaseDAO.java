package com.dicoding.movieandtvshow.db;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDatabaseDAO {
    @Query("SELECT * FROM " + MovieDatabase.TABLE_NAME)
    List<MovieDatabase> getAll();

    @Query("SELECT COUNT(*) FROM " + MovieDatabase.TABLE_NAME + " WHERE id LIKE :idInput")
    int findById(long idInput);

    @Insert
    long insert(MovieDatabase movie);

    @Delete
    void delete(MovieDatabase movie);

    @Query("SELECT * FROM " + MovieDatabase.TABLE_NAME)
    Cursor selectAll();

    @Query("SELECT * FROM " + MovieDatabase.TABLE_NAME + " WHERE id LIKE :idInput")
    Cursor selectById(long idInput);
}
