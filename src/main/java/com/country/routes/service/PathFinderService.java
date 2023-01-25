package com.country.routes.service;

import com.country.routes.model.PathResponse;
import com.country.routes.exceptions.RouteException;
import com.country.routes.model.Country;
import com.country.routes.model.CountryNode;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PathFinderService {

    // BFS traversal that updates the dist and parent vector

    public PathResponse findRoute(String origin, String destination, List<Country> countriesList) {

        Map<String, CountryNode> allCountries = countriesList.stream().collect(Collectors.toMap(Country::getIdentifier, country -> new CountryNode(country.getIdentifier(), country.getBorders())));

        Queue<CountryNode> queue = new LinkedList<>();// queue to store nodes to be visited along the breadth

        CountryNode start = allCountries.get(origin);
        CountryNode end = allCountries.get(destination);
        start.setVisited(true); // mark source node as visitedi
        queue.add(start); // push src node to queue

        while (!queue.isEmpty()) {
            CountryNode countryNode = queue.poll();// traverse all nodes along the breadth
            // traverse along the node's breadth
            for (String nodeName : countryNode.getBorders()) {
                CountryNode node = allCountries.get(nodeName);
                if (node != null && Boolean.FALSE.equals(node.isVisited())) {
                    node.setVisited(true);// // mark it visited
                    queue.add(node);
                    node.setPrev(countryNode);
                    if (node == end) {
                        queue.clear();
                        break;
                    }
                }
            }
        }

        return traceRoute(destination, allCountries);
    }

    private PathResponse traceRoute(String destination, Map<String, CountryNode> allCountries) {
        CountryNode node = allCountries.get(destination);

        List<String> routeList = new ArrayList<>();
        //Loop until node is null to reach start node
        //becasue start.prev == null
        while (node != null) {
            routeList.add(node.getIdentifier());
            node = node.getPrev();
        }
        if (routeList.size() == 1 || routeList.isEmpty()) {
            throw new RouteException("No route found");
        }
        Collections.reverse(routeList);
        return new PathResponse(routeList);
    }
}
