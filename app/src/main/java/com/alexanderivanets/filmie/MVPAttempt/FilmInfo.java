package com.alexanderivanets.filmie.MVPAttempt;

/**
 * Created by root on 11.05.17.
 */

public class FilmInfo {
    private String id;
    private String title;
    private String genre;
    private String overview;
    private String duration;
    private String releaseDate;


    public FilmInfo(String id, String title, String genre, String overview, String duration,
                    String releaseDate){
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.overview = overview;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    public String getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public String getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
