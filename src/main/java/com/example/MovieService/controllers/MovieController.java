package com.example.MovieService.controllers;

import com.example.MovieService.classes.Kategoria;
import com.example.MovieService.classes.Movie;
import com.example.MovieService.exception.BadDataException;
import com.example.MovieService.exception.MovieNotFoundException;
import com.example.MovieService.services.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
class MovieController {


//    @GetMapping("/movies")
//    public ResponseEntity<List<Movie>> getAll() {
//        return ResponseEntity.ok(movieService.getAll());
//    }

    @GetMapping("/moviesRep")
    public ResponseEntity<List<Movie>> getAllFromRepository() {

        return ResponseEntity.ok(movieService.getAllFromRepository());
    }

    @GetMapping("/getN")
    public ResponseEntity<List<Movie>> getByKategoria(@RequestBody Movie movie) throws MovieNotFoundException, BadDataException {
        List<Movie> movieFound = movieService.findMovieByKategoria(movie.getKategoria());
        if (movieFound != null) {
            return ResponseEntity.ok(movieFound);
        } else {
            throw new MovieNotFoundException("Nie można wyświetlić, żaden obiekt nie istnieje");
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/get/{ID}")
    public ResponseEntity<Movie> getByID(@PathVariable Long ID) throws MovieNotFoundException {
        Movie movieFound = movieService.findMovieByID(ID);
        if (movieFound != null) {
            return ResponseEntity.ok(movieFound);
        } else {
            throw new MovieNotFoundException("Nie można wyświetlić, obiekt nie istnieje");
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Movie> getByID(@RequestBody Movie movie) throws MovieNotFoundException, BadDataException {
        Movie movieFound = movieService.getByNazwa(movie.getNazwa());
        if (movieFound != null) {
            return ResponseEntity.ok(movieFound);
        } else {
            throw new MovieNotFoundException("Nie można wyświetlić, obiekt nie istnieje");
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/movies")
    public ResponseEntity<Movie> postByRequestBody(@RequestBody Movie movie) throws BadDataException {
        if(movie.getNazwa() != null && movie.getKategoria() != null){

            Movie newMovie = movieService.postByRequestBody(movie);

            return ResponseEntity.ok(newMovie);
        }
        else{
           // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            throw new BadDataException("Podane złe dane");
        }
    }

    @PutMapping("/changeValue/{ID}")
    public ResponseEntity<Movie> changeIsAvailableValue(@PathVariable Long ID) throws MovieNotFoundException {
        Movie movieFound = movieService.findMovieByID(ID);
        if (movieFound != null) {
            Movie movieEdit = movieService.postChangeAvailableTrue(movieFound);

            return ResponseEntity.ok(movieEdit);

        } else {
            throw new MovieNotFoundException("Nie można edytować, obiekt nie istnieje");
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @PutMapping("/put/{ID}")
    public ResponseEntity<Movie> putByNameAndBody(@PathVariable Long ID, @RequestBody Movie movie) throws MovieNotFoundException, BadDataException {
        Movie movieFound = movieService.findMovieByID(ID);
        if (movieFound != null) {
            if(movie.getNazwa() != null && movie.getKategoria() != null){

                Movie carEdit = movieService.putByNameAndBody(movieFound, movie);

                return ResponseEntity.ok(carEdit);
            }
            else{
                //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                throw new BadDataException("Podane złe dane");
            }

        } else {
            throw new MovieNotFoundException("Nie można edytować, obiekt nie istnieje");
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @DeleteMapping("/movies/{ID}")
    public ResponseEntity<Void> deleteByName(@PathVariable Long ID) throws Exception {
        Movie movieFound = movieService.findMovieByID(ID);

        if (movieFound != null) {
            movieService.deleteByID(ID);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new MovieNotFoundException("Nie można usunąć, obiekt nie istnieje");//ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping("/slow-service-tweets")
    private List<Movie> getAllTweets() throws InterruptedException {
        Thread.sleep(2000L); // delay
        return Arrays.asList(
                new Movie("RestTemplate rules", Kategoria.dramat),
                new Movie("WebClient is better", Kategoria.dramat),
                new Movie("OK, both are useful", Kategoria.dramat));
    }

    @GetMapping("/tweets-blocking")
    public List<Movie> getTweetsBlocking() {
        //log.info("Starting BLOCKING Controller!");
        final String uri = "http://localhost:8080/slow-service-tweets";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Movie>> response = restTemplate.exchange(
                uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Movie>>(){});

        List<Movie> result = response.getBody();
       // result.forEach(tweet -> log.info(tweet.toString()));
       // log.info("Exiting BLOCKING Controller!");
        return result;
    }

    @GetMapping(value = "/tweets-non-blocking",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Movie> getTweetsNonBlocking() {
       // log.info("Starting NON-BLOCKING Controller!");
        Flux<Movie> tweetFlux = WebClient.create()
                .get()
                .uri("http://localhost:8080//slow-service-tweets")
                .retrieve()
                .bodyToFlux(Movie.class);

       // tweetFlux.subscribe(tweet -> log.info(tweet.toString()));
       // log.info("Exiting NON-BLOCKING Controller!");
        return tweetFlux;
    }


//    void sdsdsds() throws JsonProcessingException {
//        RestTemplate restTemplate = new RestTemplate();
//        String fooResourceUrl
//                = "http://localhost:8080/spring-rest/foos";
//        ResponseEntity<String> response
//                = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
//        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
//
//
//
//
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode root = mapper.readTree(response.getBody());
//        JsonNode name = root.path("name");
//        Assert.notNull(name.asText());
//
//
//        Movie foo = restTemplate
//                .getForObject(fooResourceUrl + "/1", Movie.class);
//        assertThat(foo.getNazwa(), notNullValue());
//        assertThat(foo.getID(), is(1L));
//
//
//
//
//        HttpHeaders httpHeaders = restTemplate.headForHeaders(fooResourceUrl);
//        assertTrue(httpHeaders.getContentType().includes(MediaType.APPLICATION_JSON));
//
//
//
//        String entityUrl = fooResourceUrl + "/" + existingResource.getId();
//        restTemplate.delete(entityUrl);
//
//
//
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpEntity<Movie> request = new HttpEntity<>(new Movie("bar", Kategoria.komedia));
//        Movie foo5 = restTemplate.postForObject(fooResourceUrl, request, Movie.class);
//        assertThat(foo5, notNullValue());
//        assertThat(foo5.getNazwa(), is("bar"));
//
//
//
//
//
//        Movie updatedInstance = new Movie("newName", Kategoria.komedia);
//        updatedInstance.setId(createResponse.getBody().getId());
//        String resourceUrl =
//                fooResourceUrl + '/' + createResponse.getBody().getId();
//        HttpEntity<Movie> requestUpdate = new HttpEntity<>(updatedInstance, headers);
//        template.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Void.class);
//
//
//
//
//        HttpEntity<Movie> request4 = new HttpEntity<>(new Movie("bar", Kategoria.komedia));
//        URI location = restTemplate
//                .postForLocation(fooResourceUrl, request4);
//        assertThat(location, notNullValue());
//
//
//
//        RestTemplate restTemplate3 = new RestTemplate();
//        HttpEntity<Movie> request3 = new HttpEntity<>(new Movie("bar", Kategoria.komedia));
//        ResponseEntity<Movie> response3 = restTemplate3
//                .exchange(fooResourceUrl, HttpMethod.POST, request3, Movie.class);
//
//        assertThat(response3.getStatusCode(), is(HttpStatus.CREATED));
//
//        Movie foo3 = response3.getBody();
//
//        assertThat(foo3, notNullValue());
//        assertThat(foo3.getNazwa(), is("bar"));
//
//
//
//
//
//
//        ResponseEntity<Movie> response2 = restTemplate
//                .exchange(fooResourceUrl, HttpMethod.POST, request, Movie.class);
//        assertThat(response2.getStatusCode(), is(HttpStatus.CREATED));
//
//        Movie updatedInstance2 = new Movie("newName", Kategoria.komedia);
//        updatedInstance2.setID(response2.getBody().getID());
//        String resourceUrl2 =fooResourceUrl + '/' + response2.getBody().getID();
//        restTemplate.execute(
//                resourceUrl2,
//                HttpMethod.PUT,
//                requestCallback(updatedInstance2),
//                clientHttpResponse -> null);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        Set<HttpMethod> optionsForAllow = restTemplate.optionsForAllow(fooResourceUrl);
//        HttpMethod[] supportedMethods
//                = {HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};
//        Assert.isTrue(optionsForAllow.containsAll(Arrays.asList(supportedMethods)));
//    }
//
//    RequestCallback requestCallback(final Movie updatedInstance) {
//        return clientHttpRequest -> {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.writeValue(clientHttpRequest.getBody(), updatedInstance);
//            clientHttpRequest.getHeaders().add(
//                    HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//            clientHttpRequest.getHeaders().add(
//                    HttpHeaders.AUTHORIZATION, "Basic " + getBase64EncodedLogPass());
//        };
//    }
//
//
//
//    RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

//    private ClientHttpRequestFactory getClientHttpRequestFactory() {
//        int timeout = 5000;
//        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
//                = new HttpComponentsClientHttpRequestFactory();
//        clientHttpRequestFactory.setConnectTimeout(timeout);
//        return clientHttpRequestFactory;
//    }

//    private ClientHttpRequestFactory getClientHttpRequestFactory() {
//        int timeout = 5000;
//        RequestConfig config = RequestConfig.custom()
//                .setConnectTimeout(timeout)
//                .setConnectionRequestTimeout(timeout)
//                .setSocketTimeout(timeout)
//                .build();
//        CloseableHttpClient client = HttpClientBuilder
//                .create()
//                .setDefaultRequestConfig(config)
//                .build();
//        return new HttpComponentsClientHttpRequestFactory(client);
//    }

    private MovieService movieService;

    public MovieController(MovieService movierService){
        this.movieService = movierService;
    }

}
