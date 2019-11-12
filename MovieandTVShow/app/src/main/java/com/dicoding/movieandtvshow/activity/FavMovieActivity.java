package com.dicoding.movieandtvshow.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.movieandtvshow.R;
import com.dicoding.movieandtvshow.adapter.CardMovieAdapter;
import com.dicoding.movieandtvshow.db.AppDatabase;
import com.dicoding.movieandtvshow.db.MovieDatabase;
import com.dicoding.movieandtvshow.db.MovieDatabaseDAO;

import java.util.List;

public class FavMovieActivity extends AppCompatActivity {

    private static CardMovieAdapter cardAdapter;
    private static TextView tv_noContent;
    private RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.fav_movie));

        setContentView(R.layout.activity_fav);

        tv_noContent = findViewById(R.id.tv_noContent);
        tv_noContent.setVisibility(View.GONE);

        rvMovies = findViewById(R.id.rv_fav);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setHasFixedSize(true);

        cardAdapter = new CardMovieAdapter(this);
        rvMovies.setAdapter(cardAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveMovies();
    }

    private void retrieveMovies() {
        new queryAsyncTask(AppDatabase.getDatabase(getApplicationContext()).movieDao()
        ).execute();
    }

    private static class queryAsyncTask extends AsyncTask<MovieDatabase, Void, List<MovieDatabase>> {

        private MovieDatabaseDAO mAsynctaskDAO;

        queryAsyncTask(MovieDatabaseDAO dao) {
            mAsynctaskDAO = dao;
        }

        @Override
        protected List<MovieDatabase> doInBackground(MovieDatabase... movieDatabases) {
            List<MovieDatabase> movieDatabase = mAsynctaskDAO.getAll();
            return movieDatabase;
        }


        @Override
        protected void onPostExecute(List<MovieDatabase> movieDatabase) {
            super.onPostExecute(movieDatabase);
            if (movieDatabase != null) {
                cardAdapter.setListMovies(movieDatabase);
            }
            if (movieDatabase.size() == 0) tv_noContent.setVisibility(View.VISIBLE);
        }
    }
}
