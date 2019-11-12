package com.dicoding.movieandtvshow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.dicoding.movieandtvshow.R;
import com.dicoding.movieandtvshow.adapter.ViewPagerAdapter;
import com.dicoding.movieandtvshow.fragment.MovieFragment;
import com.dicoding.movieandtvshow.fragment.TvShowFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_SEARCH_KEY = "extra_search_key";
    private ViewPager viewPager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.language_setting) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.reminder_setting) {
            Intent intent = new Intent(MainActivity.this, ReminderActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.fav_movie) {
            Intent intent = new Intent(MainActivity.this, FavMovieActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.fav_tvShow) {
            Intent intent = new Intent(MainActivity.this, FavTvShowActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);

        setViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_SEARCH_KEY, "sa");

        MovieFragment movieFragment = new MovieFragment();
        movieFragment.setArguments(bundle);
    }

    private void setViewPager(ViewPager viewpager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MovieFragment(), getString(R.string.tab_text_1));
        adapter.addFragment(new TvShowFragment(), getString(R.string.tab_text_2));
        viewpager.setAdapter(adapter);
    }
}
