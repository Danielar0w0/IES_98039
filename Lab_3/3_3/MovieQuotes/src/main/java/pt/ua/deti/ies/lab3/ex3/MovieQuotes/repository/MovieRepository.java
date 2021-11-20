package pt.ua.deti.ies.lab3.ex3.MovieQuotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ua.deti.ies.lab3.ex3.MovieQuotes.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
    List<Movie> findByTitle(String title);
}