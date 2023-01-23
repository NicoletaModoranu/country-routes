package com.country.routes.controller;

import com.country.routes.model.Route;
import com.country.routes.model.Country;
import com.country.routes.service.ClientService;
import com.country.routes.service.PathFinderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/routing")
public class RouteController {

    ClientService clientService;
    PathFinderService pathFinderService;

    @Autowired
    public RouteController(ClientService clientService, PathFinderService pathFinderService) {
        this.clientService = clientService;
        this.pathFinderService = pathFinderService;
    }

    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<Route> getCountries(@PathVariable String origin, @PathVariable String destination) throws JsonProcessingException {

        List<Country> countriesList = clientService.getCountries();

        return new ResponseEntity<>(pathFinderService.findRoute(origin, destination, countriesList), HttpStatus.OK);
    }

}
