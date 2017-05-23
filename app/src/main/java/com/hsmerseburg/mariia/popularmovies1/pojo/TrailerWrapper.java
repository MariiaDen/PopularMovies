package com.hsmerseburg.mariia.popularmovies1.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by 2mdenyse on 24.03.2017.
 */
public class TrailerWrapper {
    @JsonProperty(value = "id")
    private int id;
    @JsonProperty(value = "results")
    private Trailer [] trailers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Trailer[] getTrailers() {
        return trailers;
    }

    public void setTrailers(Trailer[] trailers) {
        this.trailers = trailers;
    }
}
