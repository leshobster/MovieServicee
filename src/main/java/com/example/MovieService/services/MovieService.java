package com.example.MovieService.services;

import com.example.MovieService.classes.Movie;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    public Movie createCar(Movie car) {
        Movie newCar = new Movie(car.getNazwa(), car.getKategoria());
        return car;
    }

    public void giveException(){
        throw new RuntimeException("testinggn troller adviced");
    }

}
