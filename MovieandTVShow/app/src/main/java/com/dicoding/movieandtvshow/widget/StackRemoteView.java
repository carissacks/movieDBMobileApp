package com.dicoding.movieandtvshow.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.dicoding.movieandtvshow.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.dicoding.movieandtvshow.MovieContentProvider.AUTHORITY;
import static com.dicoding.movieandtvshow.db.MovieDatabase.TABLE_NAME;
import static com.dicoding.movieandtvshow.widget.MovieWidget.EXTRA_ITEM;

public class StackRemoteView implements RemoteViewsService.RemoteViewsFactory {

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
    private static List<String> posterLink = new ArrayList<>();
    private final Context mContext;
    private Cursor cursor;

    public StackRemoteView(Context mContext, Intent intent) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.e("stackremote", "onDataSetChanged: ok");
        if (cursor != null)
            cursor.close();
        final long id = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null);
        Binder.restoreCallingIdentity(id);

//        if (cursor == null) {
//            Log.e("stackview", "onDataSetChanged: NULL");
//        } else {
//            Log.e("stackview", "onDataSetChanged: NOT NULL");
//            Log.e("stackview", "onDataSetChanged:" + cursor.getColumnName(3));
//        }

        int i = 0;
        while (cursor.moveToNext()) {
            posterLink.add(cursor.getString(cursor.getColumnIndexOrThrow("poster")));
//            Log.e("poster", "onDataSetChanged: " + posterLink.get(i));
            i++;
        }

    }

    @Override
    public void onDestroy() {
        if (cursor != null)
            cursor.close();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(final int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        getItem(rv, i);

        Bundle extras = new Bundle();
        extras.putInt(EXTRA_ITEM, i);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.widget_poster, fillIntent);
        return rv;
    }

    public void getItem(RemoteViews rv, int i) {
        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w342/" + posterLink.get(i))
//                        .error(new Bitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.poster_aquaman)))
                    .submit()
                    .get();
            if (bitmap != null) {
                rv.setImageViewBitmap(R.id.widget_poster, bitmap);
                Log.e("test", "test: OK");
            }

//            Log.e("bitmap", "getViewAt: ok");
//            Log.e("bitmap", "getViewAt:" + posterLink.get(i));

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.e("glide", e.getMessage());
        }

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return cursor.moveToPosition(i) ? cursor.getLong(0) : i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
