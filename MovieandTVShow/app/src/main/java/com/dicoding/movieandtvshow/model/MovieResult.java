package com.dicoding.movieandtvshow.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResult {

    @SerializedName("results")
    private List<Movie> movie;

    public List<Movie> getMovie() {
        return movie;
    }
}
