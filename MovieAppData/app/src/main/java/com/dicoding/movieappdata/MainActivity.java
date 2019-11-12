package com.dicoding.movieappdata;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.movieappdata.adapter.CardAdapter;
import com.dicoding.movieappdata.model.MovieDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.dicoding.movieappdata.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.dicoding.movieappdata.MappingHelper.mapCursorToArrayList;

public class MainActivity extends AppCompatActivity implements LoadMoviesCallback {
//public class MainActivity extends AppCompatActivity {

    private CardAdapter cardAdapter;
    private DataObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Movie Data");

//        Log.e("TESTING1", CONTENT_URI.getAuthority());
//        Log.e("TESTING2", CONTENT_URI.getPath());
//        Log.e("TESTING3", CONTENT_URI.getHost());
//
//        Cursor c = getContentResolver().query(CONTENT_URI_MANUAL, null, null, null, null);
//        if(c != null) {
//            if(c.moveToNext()) {
//                Log.e("ISITABLE", c.getString(c.getColumnIndex("title")) );
//            }
//        }
//        else {
//            Log.e("ISITABLE", "NULL OI");
//        }

        RecyclerView rvMovie = findViewById(R.id.rv_movies);
        cardAdapter = new CardAdapter(this);
        rvMovie.setLayoutManager(new LinearLayoutManager(this));
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(cardAdapter);
        HandlerThread handlerThread = new HandlerThread("Data observer");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());
        observer = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, observer);
        new getData(this, this).execute();
    }

    @Override
    public void postExecute(Cursor movies) {
        ArrayList<MovieDatabase> listMovies = mapCursorToArrayList(movies);
        if (listMovies.size() > 0) {
            cardAdapter.setListMovies(listMovies);
        } else {
            Toast.makeText(this, "Tidak ada data:", Toast.LENGTH_SHORT).show();
            cardAdapter.setListMovies(new ArrayList<MovieDatabase>());
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        public getData(Context context, LoadMoviesCallback callback) {
            this.weakContext = new WeakReference<>(context);
            this.weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

    static class DataObserver extends ContentObserver {

        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (MainActivity) context).execute();
        }
    }
}
