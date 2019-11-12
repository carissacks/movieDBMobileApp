package com.dicoding.movieandtvshow.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResult {

    @SerializedName("results")
    private List<TvShow> tvShow;

    public List<TvShow> getTvShow() {
        return tvShow;
    }
}
