package com.dicoding.movieappdata.model;

public class MovieDatabase {

    private long id;

    private String title;

    private String description;

    private String poster;

    public MovieDatabase() {
    }

    public MovieDatabase(long id, String title, String description, String poster) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.poster = poster;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
