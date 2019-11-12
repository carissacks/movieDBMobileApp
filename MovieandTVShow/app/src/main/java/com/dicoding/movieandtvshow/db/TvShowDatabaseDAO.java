package com.dicoding.movieandtvshow.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TvShowDatabaseDAO {
    @Query("SELECT * FROM tvShow")
    List<TvShowDatabase> getAll();

    @Query("SELECT COUNT(*) FROM TVSHOW WHERE id LIKE :idInput")
    int findById(long idInput);

    @Insert
    long insert(TvShowDatabase tvShow);

    @Delete
    void delete(TvShowDatabase tvShow);
}
