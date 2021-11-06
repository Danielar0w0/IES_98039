package pt.ua.deti.ies.lab2.movies.MovieQuotes;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    private static final String template = "Hello, %s!";

    private Movie[] movies = {
        new Movie(1, "Grease", new String[]{"Peachy keen, jelly bean!", "The rules are… there ain't no rules!"}),
        new Movie(2, "The Lord of the Rings", new String[]{"Po-ta-toes! Boil 'em, mash 'em, stick 'em in a stew!", "Not all those who wander are lost.", "My precious!"}),
        new Movie(3, "How the Grinch Stole Christmas", new String[]{"Hate, hate, hate. Double hate. Loathe entirely!", "It's because I'm green, isn't it?"}),
        new Movie(4, "Deadpool 2", new String[]{"Well, that's just lazy writing!", "Shhhh! Shhh..."}),
        new Movie(10, "The Godfather", new String[]{"I'm gonna make him an offer he can't refuse.", "Revenge is a dish best served cold."})
    };

    // Returns a random quote
    @GetMapping("/api/quote")
    public Quote quote() {
        Random random = new Random();
        Movie randomMovie = movies[random.nextInt(movies.length)];
        return new Quote(randomMovie.getRandomQuote());
    }

    // List of all available shows (for which some quote exists). For convenience, a show should have some identifier/code
    @GetMapping("api/shows")
    public Movie[] quote_2() {
        return movies;
    }

    // api/quotes?show=<show_id>
    // Returns a random quote for the specified show/film
    @GetMapping("api/quotes")
    public Quote quote_3(@RequestParam(value = "show", required = true) int show) {
        for (Movie movie: movies) {
            if (movie.getId() == show)
                return new Quote(movie.getRandomQuote());
        }
        return null;
    }

    /*
    @GetMapping("api/quotes")
    public String quote_3(@RequestParam(value = "title", required = true) String title) {
        for (Movie movie: movies) {
            if (movie.getTitle().equalsIgnoreCase(title))
                return new Quote(movie.getRandomQuote());
        }
        return null;
    }
    */

}