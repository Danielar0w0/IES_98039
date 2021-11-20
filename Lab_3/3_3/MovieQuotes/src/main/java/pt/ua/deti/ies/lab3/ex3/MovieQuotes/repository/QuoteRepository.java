package pt.ua.deti.ies.lab3.ex3.MovieQuotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ua.deti.ies.lab3.ex3.MovieQuotes.entity.Movie;
import pt.ua.deti.ies.lab3.ex3.MovieQuotes.entity.Quote;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote,Integer> {
    List<Quote> findByMovie(Movie movie);
}