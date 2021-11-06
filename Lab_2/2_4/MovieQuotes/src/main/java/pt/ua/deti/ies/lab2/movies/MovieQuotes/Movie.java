package pt.ua.deti.ies.lab2.movies.MovieQuotes;

import java.util.Random;

public class Movie {

    private final int id;
    private final String title;
    private final String[] quotes;

    public Movie(int id, String title, String[] quotes) {
        this.id = id;
        this.title = title;
        this.quotes = quotes;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String[] getQuotes() {
        return quotes;
    }

    public String getRandomQuote() {
        Random random = new Random();
        return quotes[random.nextInt(quotes.length)];
    }

    @Override
    public String toString() {
        return "Movie" + title + "\n" +
                "Quotes:" + quotes;
    }
}