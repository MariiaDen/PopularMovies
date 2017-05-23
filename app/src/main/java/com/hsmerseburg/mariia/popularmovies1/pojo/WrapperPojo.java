package com.hsmerseburg.mariia.popularmovies1.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by 2mdenyse on 22.03.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperPojo {
    @JsonProperty(value = "page")
    private int page;
    @JsonProperty(value = "results")
    private Movie [] movies;
    @JsonProperty(value = "total_results")
    private int totalResults;
    @JsonProperty(value = "total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Movie[] getMovies() {
        return movies;
    }

    public void setMovies(Movie[] movies) {
        this.movies = movies;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
