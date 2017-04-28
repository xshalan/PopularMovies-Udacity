package app.com.example.shalan.popualrmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by noura on 05/04/2017.
 */

public class Movie  implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.movieID);
        dest.writeString(this.originalTitle);
        dest.writeDouble(this.userRating);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
        dest.writeString(this.posterUrl);
        dest.writeStringArray(this.images);
    }

    protected Movie(Parcel in) {
        this.movieID = in.readInt();
        this.originalTitle = in.readString();
        this.userRating = in.readDouble();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.posterUrl = in.readString();
        this.images = in.createStringArray();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
