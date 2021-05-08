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
public class MovieCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    private String kategoria;

    public MovieCategory() {
        this.kategoria = "kategoria"+getID();
    }

    public MovieCategory(String kategoria) {
        this.kategoria = kategoria;
    }

    public Long getID() {
        return ID;
    }

    @JsonIgnore
    public void setID(Long ID) {
        this.ID = ID;
    }


    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

}
