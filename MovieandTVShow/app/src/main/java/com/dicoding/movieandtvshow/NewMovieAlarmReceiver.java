package com.dicoding.movieandtvshow;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.dicoding.movieandtvshow.model.Movie;
import com.dicoding.movieandtvshow.model.MovieResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewMovieAlarmReceiver extends BroadcastReceiver {
//    public static final String TYPE_NEW_MOVIE = "NewMovieAlarm";
//    public static final String EXTRA_TYPE = "type";
//    public static final String EXTRA_MESSAGE = "message";

    private final int ID_NEW_MOVIE = 101;
    private MovieResult result;
    private List<Movie> data;

    public NewMovieAlarmReceiver() {

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final int notifId = ID_NEW_MOVIE;
        Date today = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(today);
        Log.e("tanggal ", formattedDate);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<MovieResult> call = jsonPlaceHolder.getNewReleaseMovie(formattedDate);
        call.enqueue(new Callback<MovieResult>() {

            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (!response.isSuccessful()) {
                    Log.e("Error load data", response.code() + " ");
                    return;
                }
                result = response.body();
                data = result.getMovie();
                for (int i = 0; data.get(i) != null && i < 2; i++) {
                    String title = String.format(context.getString(R.string.new_release), data.get(i).getTitle());
                    String message = context.getString(R.string.check_it_out);
                    showAlarmNotification(context, title, message, notifId + i);
                    Log.e("alarm", "onResponse: " + data.get(i).getTitle());
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.e("Error load data", t.getMessage());
            }
        });
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "New Movie Release Channel";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_live_tv)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }

    public void setNewMovieAlarm(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NewMovieAlarmReceiver.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NEW_MOVIE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(context, R.string.set_new_movie, Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isAlarmSet(Context context) {
        boolean alarmUp = (PendingIntent.getBroadcast(context, ID_NEW_MOVIE,
                new Intent(context, NewMovieAlarmReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);
        return alarmUp;
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NewMovieAlarmReceiver.class);
        int requestCode = ID_NEW_MOVIE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        Toast.makeText(context, R.string.remove_new_movie, Toast.LENGTH_SHORT).show();
    }
}
