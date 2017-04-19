package app.com.example.shalan.popualrmovies.Model;

/**
 * Created by noura on 16/04/2017.
 */

public class Review {

    private String id;
    private String author;
    private String comment;

    public Review(String id, String author, String comment) {
        this.id = id;
        this.author = author;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }
}
