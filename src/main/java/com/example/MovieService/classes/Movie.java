package com.example.MovieService.classes;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String nazwa;

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Column(name = "isavailable", nullable = false, columnDefinition = "tinyint (0)")
    public boolean isAvailable;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR")
    private Kategoria kategoria;

    public Movie() {
        this.nazwa = "nazwa"+getID();
        this.kategoria = Kategoria.puste;
    }

    public Movie(String nazwa, Kategoria kategoria) {
        this.nazwa = nazwa;
        this.kategoria = kategoria;
    }

    public Long getID() {
        return ID;
    }

    @JsonIgnore
    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

}
