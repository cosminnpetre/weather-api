# weather-api
[API Endpoint](http://localhost:8080/api/weather)

Spring boot reactive application used to generate a csv file with sorted cities and weather details, also returning the response to the rest call.

* Used Spring Flux for reactive functionality;
* Generated list of city names and queried the Heroku service for weather details;
* In case of exception, only the city name will be completed;
* Sorted the Flux responses based on city names;
* Generated the csv file using a reactive approach of writing in a file (DataBufferUtils);
* Finally, returned the list of cities and weather details in the rest response.

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `de.codecentric.springbootsample.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so: