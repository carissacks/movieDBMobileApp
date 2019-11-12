package com.dicoding.movieandtvshow.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dicoding.movieandtvshow.JsonPlaceHolder;
import com.dicoding.movieandtvshow.R;
import com.dicoding.movieandtvshow.db.AppDatabase;
import com.dicoding.movieandtvshow.db.TvShowDatabase;
import com.dicoding.movieandtvshow.db.TvShowDatabaseDAO;
import com.dicoding.movieandtvshow.model.TvShow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailTvActivity extends AppCompatActivity {

    public static final String EXTRA_TV_DATA = "extra_data";
    public static final String EXTRA_TV_ID = "extra_id";
    private MenuItem mi_favIcon;
    private TvShow data;

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

        data = getIntent().getParcelableExtra(EXTRA_TV_DATA);

        if (data == null) {
            long id = getIntent().getLongExtra(EXTRA_TV_ID, -1);
            if (id != -1) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.themoviedb.org/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
                Call<TvShow> call = jsonPlaceHolder.getTvShowDetail(id);
                call.enqueue(new Callback<TvShow>() {
                    @Override
                    public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                        if (!response.isSuccessful()) {
                            Log.e("Error load data", response.code() + " ");
                            return;
                        }

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
                                    AppDatabase.getDatabase(getApplicationContext()).tvDao(),
                                    data.getId()
                            ).execute();
                        }
                    }

                    @Override
                    public void onFailure(Call<TvShow> call, Throwable t) {
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
        mi_favIcon= menu.findItem(R.id.menu_add_fav);
        if(data!=null){
            new checkFavAsynctask(
                    AppDatabase.getDatabase(getApplicationContext()).tvDao(),
                    data.getId()
            ).execute();
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add_fav) {

            TvShowDatabase fav_tv = new TvShowDatabase(data.getId(), data.getTitle(), data.getDescription(), data.getPoster());

            new addToFavAsyncTask(
                    AppDatabase.getDatabase(getApplicationContext()).tvDao(),
                    fav_tv
            ).execute();
        }
        return super.onOptionsItemSelected(item);
    }

    private class addToFavAsyncTask extends AsyncTask<Void, Void, Integer> {

        private TvShowDatabaseDAO mAsynctaskDAO;
        private TvShowDatabase mObjectTvShow;

        addToFavAsyncTask(TvShowDatabaseDAO dao, TvShowDatabase objectMovie) {
            mAsynctaskDAO = dao;
            mObjectTvShow = objectMovie;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            if (mAsynctaskDAO.findById(mObjectTvShow.getId()) >= 1) {
                mAsynctaskDAO.delete(mObjectTvShow);
                return -1;
            } else {
                long resultId = mAsynctaskDAO.insert(mObjectTvShow);
                if (resultId != -1) {
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

        private TvShowDatabaseDAO mAsynctaskDAO;
        private long id;

        checkFavAsynctask(TvShowDatabaseDAO dao, long id){
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
