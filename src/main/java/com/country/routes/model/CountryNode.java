package com.country.routes.model;

import java.util.List;

public class CountryNode {

    private String identifier;

    private List<String> borders;

    private boolean visited = false;

    private CountryNode prev = null;


    public CountryNode(String identifier, List<String> borders) {
        this.identifier = identifier;
        this.borders = borders;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<String> getBorders() {
        return borders;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public CountryNode getPrev() {
        return prev;
    }

    public void setPrev(CountryNode prev) {
        this.prev = prev;
    }
}
