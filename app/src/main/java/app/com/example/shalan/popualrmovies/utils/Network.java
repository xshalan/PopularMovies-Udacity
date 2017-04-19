package app.com.example.shalan.popualrmovies.utils;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import app.com.example.shalan.popualrmovies.Model.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static app.com.example.shalan.popualrmovies.MainActivity.recyclerAdapter;

/**
 * Created by noura on 06/04/2017.
 */

public class Network  {
    private static String TAG = Network.class.getSimpleName() ;

    /* URL Path to request
    * https://api.themoviedb.org/3/discover/movie
    * @param api_key
    * @param sort_by
    * @param page
    * */
    private static String baseURL = "https://api.themoviedb.org/3" ;
    private static String imageBaseUrl = "https://image.tmdb.org/t/p/" ;
    private static String imageRequestUrl = "https://api.themoviedb.org/3/movie/" ;
    private static String discoverMovie = "discover" ;
    private static String Movie = "movie" ;
    private static String sort_param ="sort_by" ;
    public static String popularity = "popularity.desc" ;
    public static String top_rated = "vote_count.desc" ;
    private static String page_param = "page" ;
    private static String Image_path = "images" ;
    private static String API_param = "api_key" ;

    //theMovieD API key
    private static String APIkey = "ad0fe420d1b89fe509648e261f973441" ;




    /**
     * JSON Param to get the information about Movie
     * */
    public static String movieID = "id" ;
    public static String title = "title" ;
    public static String vote = "vote_average" ;
    public static String releaseDate = "release_date" ;
    public static String posterPath = "poster_path" ;
    public static String desc = "overview" ;

    public static URL buildURL(String sortBy){
        URL url = null ;
        Uri uri = Uri.parse(baseURL).buildUpon().appendPath(discoverMovie)
                                                .appendPath(Movie)
                                                .appendQueryParameter(sort_param,sortBy)
                                                .appendQueryParameter(API_param,APIkey)
                                                .build() ;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url ;
    }
    public static URL buildImageURL(int id)   {
        URL url = null ;
        Uri uri = Uri.parse(imageRequestUrl).buildUpon()
                                            .appendPath(Integer.toString(id))
                                            .appendPath(Image_path)
                                            .appendQueryParameter(API_param,APIkey)
                                            .build() ;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url ;
    }

    public static String getImageUrl(int size,String image_path){
        return imageBaseUrl+"/w" + Integer.toString(size) + image_path ;
    }


    public  static class fetchMovieList extends AsyncTask<String,Void,ArrayList<Movie> > {
        ArrayList<Movie> arrayList = new ArrayList<>();


        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            OkHttpClient okHttpClient = new OkHttpClient();
            URL url = buildURL(params[0]);
            Request request = new Request.Builder().url(url).build();
            String JSON = null;
            try {
                Response response = okHttpClient.newCall(request).execute();
                JSON = response.body().string();
                System.out.println(JSON);
                JSONObject jsonObject = new JSONObject(JSON);
                JSONArray moviesArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject movie = moviesArray.getJSONObject(i);
                    String movieTitle = movie.getString(Network.title);
                    String overview = movie.getString(Network.desc);
                    String release_dat = movie.getString(Network.releaseDate);
                    int movieID = movie.getInt(Network.movieID);
                    Double rating = movie.getDouble(Network.vote);
                    String imagePath = movie.getString(Network.posterPath);
                    arrayList.add(new Movie(movieID, movieTitle, rating, release_dat, overview, imagePath));
                    System.out.println(movieTitle);
                }
                JSONObject movie = moviesArray.getJSONObject(0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

            recyclerAdapter.setMoviesList(movies);
            recyclerAdapter.notifyDataSetChanged();

        }
    }

}
