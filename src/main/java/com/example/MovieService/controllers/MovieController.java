package com.example.MovieService.controllers;

import com.example.MovieService.classes.Movie;
import com.example.MovieService.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
class CarControllerZD {


    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAll() {
        return ResponseEntity.ok(movieService.getAll());
    }

    @GetMapping("/get/{ID}")
    public ResponseEntity<Movie> getByID(@PathVariable Long ID) {
        int id = movieService.findMovieByID(ID);
        if (id != -1) {
            return ResponseEntity.ok(movieService.getByID(ID));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/movies")
    public ResponseEntity<Movie> postByRequestBody(@RequestBody Movie movie) {
        if(movie.getNazwa() != null && movie.getKategoria() != null){

            Movie newMovie = movieService.postByRequestBody(movie);

            return ResponseEntity.ok(newMovie);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/put/{ID}")
    public ResponseEntity<Movie> putByNameAndBody(@PathVariable Long ID, @RequestBody Movie movie) {
        int id = movieService.findMovieByID(ID);
        if (id != -1) {
            if(movie.getNazwa() != null && movie.getKategoria() != null){

                Movie carEdit = movieService.putByNameAndBody(ID, movie);

                return ResponseEntity.ok(carEdit);
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @DeleteMapping("/movies/{ID}")
    public ResponseEntity<Void> deleteByName(@PathVariable Long ID) {
        int id = movieService.findMovieByID(ID);
        if (id != -1) {
            movieService.deleteByName(ID);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }


    private MovieService movieService;

    public CarControllerZD(MovieService movierService){
        this.movieService = movierService;
    }

}
