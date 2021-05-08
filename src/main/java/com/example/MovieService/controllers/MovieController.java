package com.example.MovieService.controllers;

import com.example.MovieService.classes.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
class CarControllerZD {

    List<Movie> listOfMovies = new ArrayList<Movie>() {
        {
            add(new Movie("pierwszy", ("dramat")));
            add(new Movie("drugi", ("komedia")));
        }
    };

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAll() {
        return ResponseEntity.ok(listOfMovies);
    }

    @GetMapping("/get/{ID}")
    public ResponseEntity<Movie> getByName(@PathVariable Long ID) {
        int id = findMovieByID(ID);
        if (id != -1) {
            return ResponseEntity.ok(listOfMovies.get(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/movies")
    public ResponseEntity<Movie> postByRequestBody(@RequestBody Movie movie) {
        if(movie.getNazwa() != null && movie.getKategoria() != null){
            Movie newMovie = new Movie(movie.getNazwa(), movie.getKategoria());
            listOfMovies.add(newMovie);
            return ResponseEntity.ok(newMovie);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/put/{ID}")
    public ResponseEntity<Movie> putByNameAndBody(@PathVariable Long ID, @RequestBody Movie movie) {
        int id = findMovieByID(ID);
        if (id != -1) {
            if(movie.getNazwa() != null && movie.getKategoria() != null){
                Movie carEdit = listOfMovies.get(id);
                carEdit.setNazwa(movie.getNazwa());
                carEdit.setKategoria(movie.getKategoria());
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
        int id = findMovieByID(ID);
        if (id != -1) {
            listOfMovies.remove(listOfMovies.get(id));

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    private int findMovieByID(Long ID) {
        for (int i = 0; i < listOfMovies.size(); i++) {
            if (listOfMovies.get(i).getID() == ID) {
                return i;
            }
        }
        return -1;
    }

}
