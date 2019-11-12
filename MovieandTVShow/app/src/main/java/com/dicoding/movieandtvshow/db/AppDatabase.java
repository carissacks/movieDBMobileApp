package com.dicoding.movieandtvshow.db;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MovieDatabase.class, TvShowDatabase.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "MovieandTvDatabase";
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void switchToInMemory(Context context) {
        INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                AppDatabase.class).build();
    }

    //    public abstract class RoomDatabase{
    public abstract MovieDatabaseDAO movieDao();

    public abstract TvShowDatabaseDAO tvDao();
//    };
}
