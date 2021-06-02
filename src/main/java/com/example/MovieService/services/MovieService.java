package com.example.MovieService.services;

import com.example.MovieService.classes.Kategoria;
import com.example.MovieService.classes.Movie;
import com.example.MovieService.exception.BadDataException;
import com.example.MovieService.exception.MovieNotFoundException;
import com.example.MovieService.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findMovieByKategoria(Kategoria kategoria) throws MovieNotFoundException, BadDataException {
        if(kategoria != null) {
            List<Movie> movie = movieRepository.findByKategoriaOrderByNazwa(kategoria);
            if (movie.size() > 0) {
                return movie;
            } else {
                throw new MovieNotFoundException("Nie znaleziono filmów, nie");
            }
        }
        else{
            throw new BadDataException("Podane złe dane");
        }
    }

    public Movie findMovieByID(Long ID) {
        Optional<Movie> movie = movieRepository.findById(ID);
        if (movie.isPresent()) {
            return movie.get();
        } else {
            return null;
        }
    }


    public List<Movie> getAllFromRepository() {
        return movieRepository.findAll();
    }

    public Movie getByID(Long ID) {
        Optional<Movie> movie = movieRepository.findById(ID);
        if (movie.isPresent()) {
            return movie.get();
        } else {
            return null;
        }
    }

    public Movie postChangeAvailableTrue(Movie movie) {
        if(movie.isAvailable() == false){
            movieRepository.updateAvailable(movie.getID());
            return movie;
        }
        else{
            return movie;
        }
    }

    public Movie postByRequestBody(Movie movie) {
        if(movie.getNazwa() != null && movie.getKategoria() != null){
            Movie newMovie = new Movie(movie.getNazwa(), movie.getKategoria());

            System.out.println(newMovie.getNazwa()+"  ---  "+ newMovie.getKategoria());
            movieRepository.savee(newMovie);//newMovie.getNazwa(), newMovie.getKategoria());//newMovie);//newMovie);

            Optional<Movie> movieFind = movieRepository.findById(newMovie.getID());
            if (movieFind.isPresent()) {
                return movieFind.get();
            } else {
                return null;
            }
        }
        else{
            return null;
        }
    }

    public Movie putByNameAndBody(Movie movieOld, Movie movie) {
            if(movie.getNazwa() != null && movie.getKategoria() != null){
                Movie carEdit = movieOld;
                carEdit.setNazwa(movie.getNazwa());
                carEdit.setKategoria(movie.getKategoria());
                return carEdit;
            }
            else {
                return null;
            }
    }

    public void deleteByID(@PathVariable Long ID) throws Exception {
        Optional<Movie> movie = movieRepository.findById(ID);
        if (movie.isPresent()) {
            movieRepository.delete(movie.get());

        }
        else{
            throw new MovieNotFoundException("Nie istnieje film, nie usunę");
        }

    }

}
