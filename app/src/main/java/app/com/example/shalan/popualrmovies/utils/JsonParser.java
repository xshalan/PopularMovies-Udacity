package app.com.example.shalan.popualrmovies.utils;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import app.com.example.shalan.popualrmovies.Model.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static app.com.example.shalan.popualrmovies.utils.Network.buildURL;

/**
 * Created by noura on 06/04/2017.
 */

public class JsonParser {
    ArrayList<Movie> moviesList = new ArrayList<>();
    private String movieTitle ;
    private String overview ;
    private String release_dat ;
    private int    movieID ;
    private Double rating ;
    private String imagePath ;

    public JsonParser()  {

        moviesList.add(new Movie(151515,"dsds",5.15,"dsds","dsadasqwefds","dsdsds.jpg"));

        System.out.println(moviesList.get(0).getMovieID());
    }



    public ArrayList<Movie> getMoviesList(String sortby){
        moviesList.clear();
        new fetch().execute(sortby) ;

        return moviesList ;
    }

    public class fetch extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient okHttpClient = new OkHttpClient() ;
            URL url = buildURL(params[0]) ;
            Request request = new Request.Builder().url(url).build() ;
            String JSON = null;
            try {
                Response response = okHttpClient.newCall(request).execute();
                JSON = response.body().string() ;
                System.out.println(JSON);
                JSONObject jsonObject = new JSONObject(JSON);
                JSONArray moviesArray =jsonObject.getJSONArray("results") ;

                for(int i=0;i<moviesArray.length();i++) {
                    JSONObject movie = moviesArray.getJSONObject(i);
                    movieTitle = movie.getString(Network.title);
                    overview = movie.getString(Network.desc);
                    release_dat = movie.getString(Network.releaseDate);
                    movieID = movie.getInt(Network.movieID);
                    rating = movie.getDouble(Network.vote);
                    imagePath = movie.getString(Network.posterPath);
                    moviesList.add(new Movie(movieID, movieTitle, rating, release_dat, overview, imagePath));
                    System.out.println(movieTitle);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return JSON ;
        }
    }










}
