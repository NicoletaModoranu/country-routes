package com.country.routes.service;

import com.country.routes.exceptions.RouteException;
import com.country.routes.model.Country;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientServiceTest {


    private ClientService clientService;

    @Mock
    private RestTemplate restTemplate;


    @BeforeEach
    void initService() {
        restTemplate = Mockito.mock(RestTemplate.class);
        clientService = new ClientService(restTemplate);
    }


    @Test
    void test_getCountries_success() {
        //given
        List<Country> actualCountries = getCountries();
        String fooResourceUrl
                = "https://raw.githubusercontent.com/mledoze/countries/master/countries.json";
        Mockito.when(restTemplate.getForEntity(fooResourceUrl, String.class)).thenReturn(getCountriesJson());

        //when
        List<Country> resultList = clientService.getCountries();

        //then
        assertEquals(actualCountries.size(), resultList.size());
    }

    @Test
    void test_getCountries_exception() {
        //given
        String fooResourceUrl
                = "https://raw.githubusercontent.com/mledoze/countries/master/countries.json";
        Mockito.when(restTemplate.getForEntity(fooResourceUrl, String.class)).thenReturn(new ResponseEntity<>("", HttpStatus.BAD_REQUEST));

        //when
        try {
            clientService.getCountries();
        } catch (Exception e) {
            //then
            assertTrue(e instanceof RouteException);
            assertEquals("Could not parse json data.", e.getMessage());
        }
    }

    private ResponseEntity<String> getCountriesJson() {
        String json = new Gson().toJson(getCountries());
        return new ResponseEntity<>(json, HttpStatus.OK);
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
