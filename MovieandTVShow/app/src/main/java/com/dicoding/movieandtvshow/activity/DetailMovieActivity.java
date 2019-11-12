package com.dicoding.movieandtvshow.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dicoding.movieandtvshow.JsonPlaceHolder;
import com.dicoding.movieandtvshow.R;
import com.dicoding.movieandtvshow.db.AppDatabase;
import com.dicoding.movieandtvshow.db.MovieDatabase;
import com.dicoding.movieandtvshow.db.MovieDatabaseDAO;
import com.dicoding.movieandtvshow.model.Movie;
import com.dicoding.movieandtvshow.widget.MovieWidget;
//import com.dicoding.movieandtvshow.widget.MovieWidget.UPDATE_

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_DATA = "extra_data";
    public static final String EXTRA_MOVIE_ID = "extra_id";
    private static final String UPDATE_WIDGET = "com.dicoding.movieandtvshow.UPDATE_WIDGET";
    private MenuItem mi_favIcon;
    private Movie data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle(getString(R.string.detail));

        final TextView tvTitleDetail = findViewById(R.id.tv_title_detail);
        final TextView tvDateDetail = findViewById(R.id.tv_date_detail);
        final TextView tvUserScoreDetail = findViewById(R.id.tv_user_score_detail);
        final TextView tvDescriptionDetail = findViewById(R.id.tv_description_detail);
        final ImageView imgPosterDetail = findViewById(R.id.img_poster_detail);
        final ProgressBar loadProgress = findViewById(R.id.progressBar);


        data = getIntent().getParcelableExtra(EXTRA_MOVIE_DATA);

        if (data == null) {
            long id = getIntent().getLongExtra(EXTRA_MOVIE_ID, -1);
            if (id != -1) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.themoviedb.org/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
                Call<Movie> call = jsonPlaceHolder.getMovieDetail(id);
                call.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (!response.isSuccessful()) {
                            Log.e("Error load data", response.code() + " ");
                            return;
                        }
                        loadProgress.setVisibility(View.GONE);
                        data = response.body();

                        if(data!=null) {
                            tvTitleDetail.setText(data.getTitle());
                            tvDateDetail.setText(data.getDate());
                            tvUserScoreDetail.setText(getString(R.string.user_score, data.getUserScore()));
                            tvDescriptionDetail.setText(data.getDescription());
                            Glide.with(getApplicationContext())
                                    .load("https://image.tmdb.org/t/p/w342/" + data.getPoster())
                                    .into(imgPosterDetail);
                            new checkFavAsynctask(
                                    AppDatabase.getDatabase(getApplicationContext()).movieDao(),
                                    data.getMovieId()
                            ).execute();
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        Log.e("onFailureRetrofit", t.getMessage());
                    }
                });
            }
        } else {
            tvTitleDetail.setText(data.getTitle());
            tvDateDetail.setText(data.getDate());
            tvUserScoreDetail.setText(getString(R.string.user_score, data.getUserScore()));
            tvDescriptionDetail.setText(data.getDescription());
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w342/" + data.getPoster())
                    .into(imgPosterDetail);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu, menu);
        mi_favIcon = menu.findItem(R.id.menu_add_fav);
        if(data!=null) {
            new checkFavAsynctask(
                    AppDatabase.getDatabase(getApplicationContext()).movieDao(),
                    data.getMovieId()
            ).execute();
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add_fav) {
            MovieDatabase fav_movie = new MovieDatabase(data.getMovieId(), data.getTitle(), data.getDescription(), data.getPoster());

            new addToFavAsyncTask(
                    AppDatabase.getDatabase(getApplicationContext()).movieDao(),
                    fav_movie
            ).execute();
            Context context= this;
//            AppWidgetManager appWidgetManager= AppWidgetManager.getInstance(context);
            RemoteViews remoteViews= new RemoteViews(context.getPackageName(), R.layout.movie_widget);
            ComponentName widget= new ComponentName(this, MovieWidget.class);
            AppWidgetManager manager= AppWidgetManager.getInstance(this);
            manager.updateAppWidget(widget, remoteViews);
            Log.e("insert", "onOptionsItemSelected: ok");

//            Intent intent = new Intent(getApplicationContext(), MovieWidget.class);
//            intent.setAction(UPDATE_WIDGET);
//            int[] ids = AppWidgetManager.getInstance(getApplication())
//                    .getAppWidgetIds(new ComponentName(getApplication(), MovieWidget.class));
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
//            sendBroadcast(intent);
//            getApplicationContext().sendBroadcast(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private class addToFavAsyncTask extends AsyncTask<Void, Void, Integer> {

        private MovieDatabaseDAO mAsynctaskDAO;
        private MovieDatabase mObjectMovie;

        addToFavAsyncTask(MovieDatabaseDAO dao, MovieDatabase objectMovie) {
            mAsynctaskDAO = dao;
            mObjectMovie = objectMovie;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            if (mAsynctaskDAO.findById(mObjectMovie.getId()) >= 1) {
                mAsynctaskDAO.delete(mObjectMovie);
                return -1;
            } else {
                long resultId = mAsynctaskDAO.insert(mObjectMovie);
                if (resultId != -1) {
                    //kalo masuk
//                    Log.e("insert ok masuk", "ya");
                }
                return 1;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 1) {
                Toast.makeText(getApplicationContext(), "Added to favourite", Toast.LENGTH_SHORT).show();
                mi_favIcon.setIcon(R.drawable.ic_favourite_filled);
            } else {
                Toast.makeText(getApplicationContext(), "Deleted from favourite", Toast.LENGTH_SHORT).show();
                mi_favIcon.setIcon(R.drawable.ic_favourite_border);
            }
        }
    }

    private class checkFavAsynctask extends AsyncTask<Void, Void, Integer>{

        private MovieDatabaseDAO mAsynctaskDAO;
        private long id;

        checkFavAsynctask(MovieDatabaseDAO dao, long id){
            this.mAsynctaskDAO = dao;
            this.id= id;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            int result= mAsynctaskDAO.findById(id);
//            if(result>0){
//                Log.e("Do in bg ", result+"");
//            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 1) {
                mi_favIcon.setIcon(R.drawable.ic_favourite_filled);
            } else {
                mi_favIcon.setIcon(R.drawable.ic_favourite_border);
            }

        }

    }
}
//    private class insertAsyncTask extends AsyncTask<Void, Void, Void> {
//
//        private MovieDatabaseDAO mAsynctaskDAO;
//        private MovieDatabase mObjectMovie;
//
//        insertAsyncTask(MovieDatabaseDAO dao, MovieDatabase objectMovie) {
//            mAsynctaskDAO = dao;
//            mObjectMovie = objectMovie;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            long resultId = mAsynctaskDAO.insert(mObjectMovie);
//
//            if (resultId != -1) {
//                Log.e("inser ok", "ya");
//            }
//            return null;
//        }
//    }

//
//    private class deleteAsynctask extends AsyncTask<Void, Void, Void>{
//
//        private MovieDatabaseDAO movieDatabaseDAO;
//        private MovieDatabase movieDatabase;
//
//        deleteAsynctask(MovieDatabaseDAO dao, MovieDatabase movie){
//            this.movieDatabase= movie;
//            this.movieDatabaseDAO= dao;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            movieDatabaseDAO.delete(movieDatabase);
//            return null;
//        }
//    }
//}