package com.hsmerseburg.mariia.popularmovies1.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by 2mdenyse on 29.03.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {
    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "author")
    private String author;
    @JsonProperty(value = "content")
    private String content;
    @JsonProperty(value = "url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
