package com.country.routes.service;

import com.country.routes.exceptions.RouteException;
import com.country.routes.model.Country;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ClientService {

    public List<Country> getCountries() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "https://raw.githubusercontent.com/mledoze/countries/master/countries.json";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl, String.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Country> countries;
        try {
            countries = mapper.readValue(response.getBody(), new TypeReference<>() {
            });
        } catch (JsonProcessingException ex) {
            throw new RouteException("Could not read json data.");
        }
        return countries;
    }
}
