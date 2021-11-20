package pt.ua.deti.ies.lab3.ex3.MovieQuotes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MOVIE_TBL")
public class Movie {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String title;

    @Column
    private int year;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() { return year; }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Movie" + title + "\n";
    }
}