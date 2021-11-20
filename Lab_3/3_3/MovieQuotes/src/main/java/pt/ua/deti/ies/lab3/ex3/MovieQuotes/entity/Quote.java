package pt.ua.deti.ies.lab3.ex3.MovieQuotes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QUOTE_TBL")
public class Quote {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Movie movie;

    @Column
    private String content;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Quote from movie " + movie.getTitle() + ": " + content;
    }

}
