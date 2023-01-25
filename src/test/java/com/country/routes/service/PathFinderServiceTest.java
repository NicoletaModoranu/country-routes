package com.country.routes.service;

import com.country.routes.exceptions.RouteException;
import com.country.routes.model.Country;
import com.country.routes.model.PathResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathFinderServiceTest {

    PathFinderService pathFinderService;

    @BeforeEach
    void initService() {
        pathFinderService = new PathFinderService();
    }


    @Test
    void test_findRoute_success() {

        PathResponse pathResponse = pathFinderService.findRoute("MDA", "HUN", getCountries());

        assertEquals(3, pathResponse.getRoute().size());
    }

    @Test
    void test_findRoute_routeNotFound() {
        try {
            pathFinderService.findRoute("ROU", "GBR", getCountries());
        } catch (RouteException re) {
            assertEquals("No route found", re.getMessage());
        }
    }

    private List<Country> getCountries() {
        Country c1 = new Country();
        c1.setIdentifier("MDA");
        c1.setBorders(Arrays.asList("ROU", "UKR"));

        Country c2 = new Country();
        c2.setIdentifier("ROU");
        c2.setBorders(Arrays.asList("BGR", "HUN", "MDA", "SRB", "UKR"));

        Country c3 = new Country();
        c3.setIdentifier("HUN");
        c3.setBorders(Arrays.asList("AUT", "HRV", "ROU", "SRB", "SVK", "SVN", "UKR"));

        return Arrays.asList(c1, c2, c3);
    }
}
