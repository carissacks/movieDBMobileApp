package com.dicoding.movieandtvshow.fragment;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.movieandtvshow.JsonPlaceHolder;
import com.dicoding.movieandtvshow.R;
import com.dicoding.movieandtvshow.activity.DetailMovieActivity;
import com.dicoding.movieandtvshow.adapter.GridViewMovieAdapter;
import com.dicoding.movieandtvshow.model.Movie;
import com.dicoding.movieandtvshow.model.MovieResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieFragment extends Fragment {

    private final String EXTRA_MOVIE = "extra_local_movie_data";
    private RecyclerView rvMovies;
    private List<Movie> movieList;
    private ProgressBar loadProgress;
    private MovieResult result;

    public MovieFragment() {
        // Required empty public constructor
    }

   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_menu, menu);

        MenuItem searchMenu = menu.findItem(R.id.search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(getContext().SEARCH_SERVICE);

        if (searchManager != null) {
            final SearchView searchView = (SearchView) searchMenu.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
//                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    loadProgress.setVisibility(View.VISIBLE);
                    rvMovies.setVisibility(View.GONE);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.themoviedb.org/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
                    Call<MovieResult> call = jsonPlaceHolder.getSearchMovieResult(s);
                    call.enqueue(new Callback<MovieResult>() {
                        @Override
                        public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                            if (!response.isSuccessful()) {
                                Log.e("Error load data", response.code() + " ");
                                return;
                            }

                            result = response.body();
                            movieList = result.getMovie();
                            loadProgress.setVisibility(View.GONE);
                            rvMovies.setVisibility(View.VISIBLE);
                            setAdapter();
                        }

                        @Override
                        public void onFailure(Call<MovieResult> call, Throwable t) {
                            Log.e("onFailureRetrofit", t.getMessage());
                        }
                    });

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    if (searchView.getQuery().length() == 0) {
                        getData();
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        setHasOptionsMenu(true);
//        String search= getArguments().getString(MainActivity.EXTRA_SEARCH_KEY);
//        Log.e("search", search);

        rvMovies = view.findViewById(R.id.rv_movies);
        loadProgress = view.findViewById(R.id.progressBar);

        if (savedInstanceState == null) {
            getData();

        } else {
            movieList = savedInstanceState.getParcelableArrayList(EXTRA_MOVIE);
            setAdapter();
        }
        return view;
    }

    public void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<MovieResult> call = jsonPlaceHolder.getMovieResult();
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if (!response.isSuccessful()) {
                    Log.e("Error load data", response.code() + " ");
                    return;
                }

                result = response.body();
                movieList = result.getMovie();
                loadProgress.setVisibility(View.GONE);
//                rvMovies.setVisibility(View.VISIBLE);
                Log.e("Start", "Tembak lagi");
                setAdapter();
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.e("onFailureRetrofit", t.getMessage());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_MOVIE, (ArrayList<? extends Parcelable>) movieList);

    }

    private void setAdapter() {
        rvMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
        GridViewMovieAdapter gridViewMovieAdapter = new GridViewMovieAdapter((ArrayList<Movie>) movieList);

        rvMovies.setAdapter(gridViewMovieAdapter);

        gridViewMovieAdapter.setOnItemClickCallBack(new GridViewMovieAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(Movie data) {
                Intent mIntent = new Intent(getActivity(), DetailMovieActivity.class);
                mIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE_DATA, data);
                startActivity(mIntent);
            }
        });
    }
}
