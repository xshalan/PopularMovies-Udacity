package app.com.example.shalan.popualrmovies.Model;

import java.io.Serializable;

/**
 * Created by noura on 05/04/2017.
 */

public class Movie  implements Serializable {

    private int movieID ;
    private String originalTitle;
    private double userRating;
    private String releaseDate;
    private String overview;
    private String posterUrl;
    private String[] images ;

    public Movie(int movieID,String originalTitle, double userRating ,
                 String releaseDate, String overview, String posterUrl ){
        this.movieID = movieID ;
        this.originalTitle = originalTitle ;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterUrl = "https://image.tmdb.org/t/p/w342" + posterUrl ;

    }

    public int getMovieID(){
        return this.movieID ;
    }

    public double getUserRating(){
        return userRating ;
    }

    public String getOriginalTitle(){
        return originalTitle ;
    }

    public String getReleaseDate(){
        return releaseDate ;
    }

    public String getOverview(){
        return overview;
    }

    public String getPosterUrl(){
        return posterUrl ;
    }
    public String[] getImages(){return images;}
    public void setImages(String[] array){
        this.images = array ;
    }

}
