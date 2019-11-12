package com.dicoding.movieandtvshow;

import com.dicoding.movieandtvshow.model.Movie;
import com.dicoding.movieandtvshow.model.MovieResult;
import com.dicoding.movieandtvshow.model.TvShow;
import com.dicoding.movieandtvshow.model.TvShowResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolder {
    //    String API_KEY = "0cecc73979c1b18e50c01ce4d3167c38";
    String API_KEY = BuildConfig.TMDB_API_KEY;

    @GET("3/movie/now_playing?api_key=" + API_KEY + "&language=en-US&page=1")
    Call<MovieResult> getMovieResult();

    @GET("3/discover/tv?api_key=" + API_KEY + "&language=en-US&page=1")
    Call<TvShowResult> getTvShowResult();

    @GET("3/movie/{movie_id}?api_key=" + API_KEY + "&language=en-US")
    Call<Movie> getMovieDetail(@Path("movie_id") long id);

    @GET("3/tv/{tvShow_id}?api_key=" + API_KEY + "&language=en-US")
    Call<TvShow> getTvShowDetail(@Path("tvShow_id") long id);

    @GET("3/search/movie?api_key=" + API_KEY + "&language=en-US")
    Call<MovieResult> getSearchMovieResult(@Query("query") String movie_name);

    @GET("3/search/tv?api_key=" + API_KEY + "&language=en-US")
    Call<TvShowResult> getSearchTvShowResult(@Query("query") String tvShow_name);

//    @GET("3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte={date}&primary_release_date.lte={date}")
    @GET("3/discover/movie?api_key=" + API_KEY)
    Call<MovieResult> getNewReleaseMovie(@Query("primary_release_date.gte") String today);
}
