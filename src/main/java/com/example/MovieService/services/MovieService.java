package com.example.MovieService.services;

import com.example.MovieService.classes.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    public int findMovieByID(Long ID) {
        for (int i = 0; i < listOfMovies.size(); i++) {
            if (listOfMovies.get(i).getID() == ID) {
                return i;
            }
        }
        return -1;
    }

    List<Movie> listOfMovies = new ArrayList<Movie>() {
        {
            add(new Movie("pierwszy", ("dramat")));
            add(new Movie("drugi", ("komedia")));
        }
    };


    public List<Movie> getAll() {
        return listOfMovies;
    }

    public Movie getByID(Long ID) {
        int id = findMovieByID(ID);
        if (id != -1) {
            return listOfMovies.get(id);
        } else {
            return null;
        }
    }

    public Movie postByRequestBody(Movie movie) {
        if(movie.getNazwa() != null && movie.getKategoria() != null){
            Movie newMovie = new Movie(movie.getNazwa(), movie.getKategoria());
            listOfMovies.add(newMovie);
            return newMovie;
        }
        else{
            return null;
        }
    }

    public Movie putByNameAndBody(Long ID,Movie movie) {
        int id = findMovieByID(ID);
        if (id != -1) {
            if(movie.getNazwa() != null && movie.getKategoria() != null){
                Movie carEdit = listOfMovies.get(id);
                carEdit.setNazwa(movie.getNazwa());
                carEdit.setKategoria(movie.getKategoria());
                return carEdit;
            }
            else{
                return null;
            }

        } else {
            return null;
        }

    }

    public void deleteByName(@PathVariable Long ID) {
        int id = findMovieByID(ID);
        if (id != -1) {
            listOfMovies.remove(listOfMovies.get(id));

        }

    }

}
