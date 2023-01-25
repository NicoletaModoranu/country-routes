# Route Finding 

The application computes the shortest path between two countries.


## Run the app

Prerequisite: _clone the application_

You can either use IntelliJ or Eclipse for this step. Build the application using Maven tool (clean install), then run.

## Test the application

To use it, you can use Postman or a browser. 
The application will be accessible on localhost, port 8080.

Request example:

```
GET http://localhost:8080/routing/ROU/CZE
```

## Some details of the application
- In src/tests you can find the unit tests. The controller and the services are covered with tests.
- The algorithm to find the path is implemented in PathFinderService. It is an adapted version of BFS.
- Exception handling is implemented using Controller Advice

