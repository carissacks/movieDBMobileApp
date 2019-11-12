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
import com.dicoding.movieandtvshow.activity.DetailTvActivity;
import com.dicoding.movieandtvshow.adapter.GridViewTvAdapter;
import com.dicoding.movieandtvshow.model.TvShow;
import com.dicoding.movieandtvshow.model.TvShowResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {
    private final String EXTRA_TV = "extra_tv_local_data";
    private RecyclerView rvMovies;
    private List<TvShow> tvShowList;
    private ProgressBar loadProgress;
    private TvShowResult result;

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
                    Call<TvShowResult> call = jsonPlaceHolder.getSearchTvShowResult(s);
                    call.enqueue(new Callback<TvShowResult>() {
                        @Override
                        public void onResponse(Call<TvShowResult> call, Response<TvShowResult> response) {
                            if (!response.isSuccessful()) {
                                Log.e("Error load data", response.code() + " ");
                                return;
                            }

                            result = response.body();
                            tvShowList = result.getTvShow();
                            loadProgress.setVisibility(View.GONE);
                            rvMovies.setVisibility(View.VISIBLE);
                            setAdapter();
                        }

                        @Override
                        public void onFailure(Call<TvShowResult> call, Throwable t) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        setHasOptionsMenu(true);

        rvMovies = view.findViewById(R.id.rv_movies);
        loadProgress = view.findViewById(R.id.progressBar);

        if (savedInstanceState == null) {
            getData();
        } else {
            tvShowList = savedInstanceState.getParcelableArrayList(EXTRA_TV);
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
        Call<TvShowResult> call = jsonPlaceHolder.getTvShowResult();

        call.enqueue(new Callback<TvShowResult>() {
            @Override
            public void onResponse(Call<TvShowResult> call, Response<TvShowResult> response) {
                if (!response.isSuccessful()) {
                    Log.e("Error load data", response.code() + " ");
                    return;
                }
                result = response.body();
                tvShowList = result.getTvShow();
                loadProgress.setVisibility(View.GONE);
                setAdapter();
            }

            @Override
            public void onFailure(Call<TvShowResult> call, Throwable t) {
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
        outState.putParcelableArrayList(EXTRA_TV, (ArrayList<? extends Parcelable>) tvShowList);

    }

    private void setAdapter() {
        rvMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
        GridViewTvAdapter gridViewTvAdapter = new GridViewTvAdapter((ArrayList<TvShow>) tvShowList);

        rvMovies.setAdapter(gridViewTvAdapter);

        gridViewTvAdapter.setOnItemClickCallBack(new GridViewTvAdapter.OnItemClickCallBack() {
            @Override
            public void onItemClicked(TvShow data) {
                Intent mIntent = new Intent(getActivity(), DetailTvActivity.class);
                mIntent.putExtra(DetailTvActivity.EXTRA_TV_DATA, data);
                startActivity(mIntent);
            }
        });
    }
}
