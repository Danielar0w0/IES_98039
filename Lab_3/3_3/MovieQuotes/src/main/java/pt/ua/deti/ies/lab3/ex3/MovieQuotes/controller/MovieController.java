package pt.ua.deti.ies.lab3.ex3.MovieQuotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.ies.lab3.ex3.MovieQuotes.entity.Movie;
import pt.ua.deti.ies.lab3.ex3.MovieQuotes.entity.Quote;
import pt.ua.deti.ies.lab3.ex3.MovieQuotes.service.MovieService;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class MovieController {

    private MovieService service;

    @Autowired
    public MovieController(MovieService service) {
        this.service = service;
    }

    @PostMapping("/movies")
    public Movie addMovie(@RequestBody Movie movie) {
        return service.saveMovie(movie);
    }

    @GetMapping("/movies")
    public List<Movie> movies() {
        return service.getMovies();
    }

    @GetMapping("/movies/search")
    public List<Movie> getMoviesByTitle(@RequestParam(value = "title", required = true) String title) {
        return service.getMoviesByTitle(title);
    }

    @GetMapping("/movies/{id}")
    public Optional<Movie> getMovieById(@PathVariable int id) {
        return service.getMovieById(id);
    }


    @PostMapping("/quotes")
    public Quote addQuote(@RequestBody Quote quote) {
        return service.saveQuote(quote);
    }

    @GetMapping("/quotes")
    public List<Quote> quotes() {
        return service.getQuotes();
    }

    @GetMapping("/quotes/{id}")
    public Optional<Quote> getQuoteById(@PathVariable int id) {
        return service.getQuoteById(id);
    }

    @GetMapping("/quotes/random")
    public Quote randomQuote() {
        return service.randomQuote();
    }

    @GetMapping("/movies/{id}/quotes")
    public List<Quote> getMovieQuotes(@PathVariable int id) {
        return service.getMovieQuotes(id);
    }
}