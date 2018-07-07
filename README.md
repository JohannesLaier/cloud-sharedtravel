# Java EE webservice to plan trips

Sharedtravel is Jetty based Java EE application that allows you to plan and book your next city trip by using the APIs of BlaBlaCar, Bessermitfahren, AirBnB and 9Flat. This application was designed so that it is also available as a web service and can be used via a REST interface.

## Technology ##

### Fontend ####

The frontend of the application was realized using web technologies, HTML5 and CSS3. In order to efficiently implement the application, proven frameworks were used. With the help of bootstrap, the graphical user interface could be realized according to the "responsive design" principle. As a result, the GUI of the application automatically adapts to all devices (smartphone, tablet, PC). In addition, AngularJS were used for the functions within the web application and AJAX for the data transfer between browser and server.

### Backend ###

The backend has been implementet with typical Java EE technologies such as JaxRS. The SharedTravel web service was developed using the Jersey and Jackson libraries. Jersey (Core Server) was used as the framework for the REST servlets. As a JSON Object Mapper, the Jackson API was used, which implemented the parsing of JSON requests and JSON responses to Java objects.
For addressing the integrated web services, Jersey (Core Client) was used in the application. Simple JSON was used to parse the Requests & Responses.

## REST Service ##

| REST-Service Path     |  Description                                                                                                               |
|-----------------------|----------------------------------------------------------------------------------------------------------------------------|
| rest/room/find        | Returns all available rooms in the given city sorted by price in ascending order.                                          |
| rest/ride/find        | Returns all available trips between the transferred cities sorted in ascending order of price.                             |
| rest/trip/find        | Returns trips between the given cities are sorted by price in ascending order.  The user is able to build his own journey. |
| rest/trip/find/europe | Look for the cheapest trips in the desired period and from the desired starting point in the most popular European cities. |	

Typical JSON request to the API looks like:

```json
{
    "departureDate" : 1490362200,
    "arrivalDate" : 1490535000,
    "departure" : {
        "name" : "München"
    },
    "arrival": {
         "name" : "Innsbruck" 
    },
     "seats " : 1
}
```

## Screenshots ##

![Book a trip](docs/imgs/page_home.png?raw=true "Book a trip")
![Select a result](docs/imgs/page_result_1.png?raw=true "Select a result")
![Display Trip](docs/imgs/page_result_2.png?raw=true "Display Trip")

## Cache ##

![Caching](docs/imgs/cache.png?raw=true "Caching")

In order to improve the performance of the web service, a separate cache was developed and implemented, which keeps requests in cache for five minutes. In this case, every request sent to the service checks whether results for the request are already in the cache. If so, they are retrieved and output to the user. If not, requests are sent to the external APIs and the results are sent to the user and cached at the same time.

## Performance ##

In order to test the performance of the web service in regular operation, about 380 requests were sent to each service and the response time was measured. The table below shows the minimum, maximum and average values of the requests. There are currently no values for the interface to search for a trip to one of the most popular cities in Europe, as the number of requests to external APIs is limited. When searching for these trips, too many requests to the different APIs would have to be sent simultaneously and this would be repeated for many major cities.

| REST-Service Path     | Min(ms) | Max(ms) | Avg(ms) |
|-----------------------|---------|---------|---------|
| rest/room/find        |  69     | 5111    | 207     |
| rest/ride/find        | 56      | 2063    | 228     |
| rest/trip/find        | 84      | 3843    | 214     |
| rest/trip/find/europe | -       | -       | -       |
