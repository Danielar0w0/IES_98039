package pt.ua.deti.ies.lab3.ex3.MovieQuotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.ies.lab3.ex3.MovieQuotes.entity.Movie;
import pt.ua.deti.ies.lab3.ex3.MovieQuotes.entity.Quote;
import pt.ua.deti.ies.lab3.ex3.MovieQuotes.repository.MovieRepository;
import pt.ua.deti.ies.lab3.ex3.MovieQuotes.repository.QuoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class MovieService {

    private final MovieRepository movies;
    private final QuoteRepository quotes;

    @Autowired
    public MovieService(MovieRepository movies, QuoteRepository quotes) {
        this.movies = movies;
        this.quotes = quotes;
    }

    public Movie saveMovie(Movie movie) {
        return movies.save(movie);
    }

    public List<Movie> saveMovies(List<Movie> movies) {
        return this.movies.saveAll(movies);
    }

    public List<Movie> getMovies() {
        return movies.findAll();
    }

    public Optional<Movie> getMovieById(int id) {
        return movies.findById(id);
    }

    public List<Movie> getMoviesByTitle(String title) {
        return movies.findByTitle(title);
    }

    public List<Quote> getMovieQuotes(int id) {

        List<Quote> res = new ArrayList<>();
        List<Quote> allQuotes = getQuotes();
        int otherId;

        for (Quote quote: allQuotes) {
            otherId = quote.getMovie().getId();
            if (otherId == id)
                res.add(quote);
        }

        return res;
    }

    public Quote saveQuote(Quote quote) {
        Movie movie = quote.getMovie();
        List<Movie> allMovies = movies.findAll();

        if (!allMovies.contains(movie))
            return null;

        return quotes.save(quote);
    }

    public List<Quote> getQuotes() {
        return quotes.findAll();
    }

    public Optional<Quote> getQuoteById(int id) {
        return quotes.findById(id);
    }

    public Quote randomQuote() {
        List<Quote> allQuotes = quotes.findAll();

        if (allQuotes.isEmpty())
            return null;

        Random random = new Random();
        return allQuotes.get(random.nextInt(allQuotes.size()));
    }

}
