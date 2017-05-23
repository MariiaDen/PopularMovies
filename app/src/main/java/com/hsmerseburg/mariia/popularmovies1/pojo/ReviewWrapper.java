package com.hsmerseburg.mariia.popularmovies1.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by 2mdenyse on 29.03.2017.
 */
public class ReviewWrapper
{
    @JsonProperty(value = "id")
    private int id;
    @JsonProperty(value = "page")
    private int page;
    @JsonProperty(value = "results")
    private Review[] review;
    @JsonProperty(value = "total_pages")
    private int totalPages;
    @JsonProperty(value = "total_results")
    private int totalResults;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Review[] getReview() {
        return review;
    }

    public void setReview(Review[] review) {
        this.review = review;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
