package com.example.MovieService.controllers;

import com.example.MovieService.classes.Movie;
import com.example.MovieService.exception.BadDataException;
import com.example.MovieService.exception.MovieNotFoundException;
import com.example.MovieService.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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


    private MovieService movieService;

    public MovieController(MovieService movierService){
        this.movieService = movierService;
    }

}
