package com.country.routes.controller;

import com.country.routes.exceptions.RouteException;
import com.country.routes.model.Country;
import com.country.routes.model.PathResponse;
import com.country.routes.service.ClientService;
import com.country.routes.service.PathFinderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsIterableContaining.hasItem;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RouteController.class)
class RouteControllerTest {

    @MockBean
    private PathFinderService pathFinderService;

    @MockBean
    private ClientService clientService;

    @Autowired
    private RouteController routeController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void test_getById_success() throws Exception {
        //given
        List<Country> countries = getCountries();
        PathResponse expectedResponse = new PathResponse(Arrays.asList("MDA", "ROU", "HUN"));

        String origin = "MDA";
        String destination = "HUN";

        when(clientService.getCountries()).thenReturn(countries);
        when(pathFinderService.findRoute(origin, destination, countries)).thenReturn(expectedResponse);

        //when; then
        mockMvc.perform(get("/routing/{origin}/{destination}", origin, destination))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.route").isArray())
                .andExpect(jsonPath("$.route", hasSize(3)))
                .andExpect(jsonPath("$.route", hasItem("MDA")))
                .andExpect(jsonPath("$.route", hasItem("HUN")))
                .andExpect(jsonPath("$.route", hasItem("ROU")))
                .andDo(print());
    }


    @Test
    void test_getCountries_throwsException() throws Exception {
        //given
        String origin = "MDA";
        String destination = "HUN";

        when(clientService.getCountries()).thenThrow(RouteException.class);
        //when; then
        mockMvc.perform(get("/routing/{origin}/{destination}", origin, destination))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RouteException));
    }


    @Test
    void test_findRoute_throwsException() throws Exception {
        //given
        List<Country> countries = getCountries();
        String origin = "MDA";
        String destination = "GBR";

        when(clientService.getCountries()).thenReturn(countries);
        when(pathFinderService.findRoute(origin, destination, countries)).thenThrow(RouteException.class);

        //when; then
        mockMvc.perform(get("/routing/{origin}/{destination}", origin, destination))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RouteException));
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
