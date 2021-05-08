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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    private String nazwa;
    private String kategoria;

    public Movie() {
        this.nazwa = "nazwa"+getID();
        this.kategoria = "";
    }

    public Movie(String nazwa, String kategoria) {
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

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

}
