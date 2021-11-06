package pt.ua.deti.ies.lab2.movies.MovieQuotes;

public class Quote {

    private String content;

    public Quote(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Quote:" + content;
    }
}
