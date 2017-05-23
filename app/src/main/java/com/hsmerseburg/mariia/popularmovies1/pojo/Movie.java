package com.hsmerseburg.mariia.popularmovies1.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by 2mdenyse on 22.03.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie implements Serializable {

    @JsonProperty(value = "poster_path")
    String posterPath;
    @JsonProperty(value = "adult")
    boolean isAdult;
    @JsonProperty(value = "overview")
    String overview;

    @JsonProperty(value = "release_date")
    String releaseDate;
    @JsonProperty(value = "genre_ids")
    int[] genreIds;
    @JsonProperty(value = "id")
    String id;

    @JsonProperty(value = "original_title")
    String originalTitle;
    @JsonProperty(value = "original_language")
    String originalLanguage;
    @JsonProperty(value = "title")
    String title;
    @JsonProperty(value = "backdrop_path")
    String backdropPath;

    @JsonProperty(value = "popularity")
    long popularity;
    @JsonProperty(value = "vote_count")
    int voteCount;

    @JsonProperty(value = "video")
    boolean video;
    @JsonProperty(value = "vote_average")
    String voteAverage;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public long getPopularity() {
        return popularity;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }
}
