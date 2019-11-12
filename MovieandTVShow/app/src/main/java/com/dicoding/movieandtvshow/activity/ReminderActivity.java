package com.dicoding.movieandtvshow.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.movieandtvshow.DailyAlarmReceiver;
import com.dicoding.movieandtvshow.NewMovieAlarmReceiver;
import com.dicoding.movieandtvshow.R;

public class ReminderActivity extends AppCompatActivity {

    Switch sw_dailyReminder;
    Switch sw_newReminder;

    DailyAlarmReceiver dailyAlarmReceiver;
    NewMovieAlarmReceiver newMovieAlarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        sw_dailyReminder = findViewById(R.id.sw_daily_reminder);
        sw_newReminder = findViewById(R.id.sw_new_reminder);
        dailyAlarmReceiver = new DailyAlarmReceiver();
        newMovieAlarmReceiver = new NewMovieAlarmReceiver();

        if (dailyAlarmReceiver.isAlarmSet(this)) {
            sw_dailyReminder.setChecked(true);
        }
        if (newMovieAlarmReceiver.isAlarmSet(this)) {
            sw_newReminder.setChecked(true);
        }


        sw_newReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    newMovieAlarmReceiver.setNewMovieAlarm(getApplicationContext());
                } else {
                    newMovieAlarmReceiver.cancelAlarm(getApplicationContext());
                }
            }
        });

        sw_dailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    dailyAlarmReceiver.setDailyAlarm(getApplicationContext());
                } else {
                    dailyAlarmReceiver.cancelAlarm(getApplicationContext());

                }
            }
        });
    }
}
