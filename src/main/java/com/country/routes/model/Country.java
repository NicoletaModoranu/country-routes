package com.country.routes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Country {

    @JsonProperty("cca3")
    private String identifier;

    @JsonProperty("borders")
    private List<String> borders;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }
}
