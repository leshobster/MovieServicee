package com.example.MovieService.repository;

import com.example.MovieService.classes.Kategoria;
import com.example.MovieService.classes.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface  MovieRepository extends JpaRepository<Movie, Long> {

    //@Query("SELECT m FROM Movie m WHERE m.ID=?1")
   // Optional<Movie> findById(Long ID);

    Optional<Movie> findByNazwa(String nazwa);

    Optional<Movie> findById(Long ID);

    List<Movie> findByKategoriaOrderByNazwa(Kategoria kategoria);

    List<Movie> findAll();


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Movie (nazwa, kategoria) values(:#{#newMovie.nazwa}, :#{#newMovie.kategoria.toString()})", nativeQuery = true)
    void savee(@Param("newMovie") Movie newMovie);

//    @Query
//    Movie save(Movie movie);

    @Modifying
    @Query("delete from Movie m where m.ID = :#{#newMovie.ID}")
    void delete(@Param("newMovie") Movie newMovie);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Movie SET isAvailable = 1 WHERE Movie.ID = :movieID", nativeQuery = true)
    void updateAvailable(@Param("movieID") Long ID);

//    @Modifying
//    @Transactional
//    @Query(value = "INSERT INTO Movie (nazwa, kategoria) values(:nazwa, :kategoria)", nativeQuery = true)
//    int savee(@Param("nazwa") String nazwa, @Param("kategoria") String kategoria);


}
