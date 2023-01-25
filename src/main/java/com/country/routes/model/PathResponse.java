package com.country.routes.model;

import java.util.List;

public class PathResponse {

    List<String> route;

    public PathResponse(List<String> route) {
        this.route = route;
    }

    public List<String> getRoute() {
        return route;
    }
}
