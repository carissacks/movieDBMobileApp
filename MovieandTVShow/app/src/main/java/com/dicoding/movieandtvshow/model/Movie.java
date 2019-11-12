package com.dicoding.movieandtvshow.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private String title;
    @SerializedName("id")
    private int movie_id;
    @SerializedName("overview")
    private String description;
    @SerializedName("release_date")
    private String date;
    @SerializedName("vote_average")
    private double userScore;
    @SerializedName("poster_path")
    private String poster;

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.movie_id = in.readInt();
        this.description = in.readString();
        this.date = in.readString();
        this.userScore = in.readDouble();
        this.poster = in.readString();
    }

    public int getMovieId() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public double getUserScore() {
        return userScore;
    }

    public String getPoster() {
        return poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.movie_id);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeDouble(this.userScore);
        dest.writeString(this.poster);
    }
}