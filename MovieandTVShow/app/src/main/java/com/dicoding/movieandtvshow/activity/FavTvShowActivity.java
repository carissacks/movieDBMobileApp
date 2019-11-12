package com.dicoding.movieandtvshow.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.movieandtvshow.R;
import com.dicoding.movieandtvshow.adapter.CardTvAdapter;
import com.dicoding.movieandtvshow.db.AppDatabase;
import com.dicoding.movieandtvshow.db.TvShowDatabase;
import com.dicoding.movieandtvshow.db.TvShowDatabaseDAO;

import java.util.List;

public class FavTvShowActivity extends AppCompatActivity {

    private static CardTvAdapter cardAdapter;
    private static TextView tv_noContent;
    private RecyclerView rvTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.fav_tvShow);
        setContentView(R.layout.activity_fav);

        tv_noContent = findViewById(R.id.tv_noContent);
        tv_noContent.setVisibility(View.GONE);

        rvTvShow = findViewById(R.id.rv_fav);
        rvTvShow.setLayoutManager(new LinearLayoutManager(this));
        rvTvShow.setHasFixedSize(true);

        cardAdapter = new CardTvAdapter(this);
        rvTvShow.setAdapter(cardAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveTvShows();
    }

    private void retrieveTvShows() {
        new queryAsyncTask(AppDatabase.getDatabase(getApplicationContext()).tvDao()
        ).execute();
    }

    private static class queryAsyncTask extends AsyncTask<TvShowDatabase, Void, List<TvShowDatabase>> {

        private TvShowDatabaseDAO mAsynctaskDAO;

        queryAsyncTask(TvShowDatabaseDAO dao) {
            mAsynctaskDAO = dao;
        }

        @Override
        protected List<TvShowDatabase> doInBackground(TvShowDatabase... tvShowDatabases) {
            List<TvShowDatabase> tvShowDatabase = mAsynctaskDAO.getAll();
            return tvShowDatabase;
        }

        @Override
        protected void onPostExecute(List<TvShowDatabase> tvShowDatabase) {
            super.onPostExecute(tvShowDatabase);
            if (tvShowDatabase != null) {
                cardAdapter.setListTvShows(tvShowDatabase);
            }
            if (tvShowDatabase.size() == 0) tv_noContent.setVisibility(View.VISIBLE);
        }
    }
}
