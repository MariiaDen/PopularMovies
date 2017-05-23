package com.hsmerseburg.mariia.popularmovies1.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by 2mdenyse on 24.03.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trailer {
    @JsonProperty(value = "id")
    String id;

    @JsonProperty(value = "iso_639_1")
    String iso_639_1;

    @JsonProperty(value = "iso_3166_1")
    String iso_3166_1;

    @JsonProperty(value = "key")
    String key;

    @JsonProperty(value = "name")
    String name;

    @JsonProperty(value = "site")
    String site;

    @JsonProperty(value = "size")
    String size;

    @JsonProperty(value = "type")
    String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
