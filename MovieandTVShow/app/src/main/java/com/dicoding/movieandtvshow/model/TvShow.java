package com.dicoding.movieandtvshow.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TvShow implements Parcelable {
    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
    @SerializedName("name")
    private String title;
    private long id;
    @SerializedName("overview")
    private String description;
    @SerializedName("first_air_date")
    private String date;
    @SerializedName("vote_average")
    private double userScore;
    @SerializedName("poster_path")
    private String poster;

    public TvShow() {
    }

    protected TvShow(Parcel in) {
        this.title = in.readString();
        this.id = in.readLong();
        this.description = in.readString();
        this.date = in.readString();
        this.userScore = in.readDouble();
        this.poster = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
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
        dest.writeLong(this.id);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeDouble(this.userScore);
        dest.writeString(this.poster);
    }
}
