package app.com.example.shalan.popualrmovies;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import app.com.example.shalan.popualrmovies.Model.Movie;
import app.com.example.shalan.popualrmovies.Model.Review;
import app.com.example.shalan.popualrmovies.Model.Trailer;
import app.com.example.shalan.popualrmovies.data.MovieContract;
import app.com.example.shalan.popualrmovies.utils.Network;
import app.com.example.shalan.popualrmovies.utils.reviewAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static app.com.example.shalan.popualrmovies.utils.Network.buildVideosURL;


public class DetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    ImageView video ;
    ArrayList<Trailer> Trailers = new ArrayList<>() ;
    ArrayList<Review> Reviews = new ArrayList<>() ;
    String key = null ;
    ArrayList<Movie> movie = new ArrayList<>() ;
    Intent intent ;


    public static final String[] MOVIES_COLUMNS = {
            MovieContract.MovieEntry._ID ,
            MovieContract.MovieEntry.COLUMN_TITLE ,
            MovieContract.MovieEntry.COLUMN_PPOSTER ,
            MovieContract.MovieEntry.COLUMN_MOVIE_ID ,
            MovieContract.MovieEntry.COLUMN_OVERVIEW ,
            MovieContract.MovieEntry.COLUMN_RATING
    } ;

    static final int COL_MOVIE_ID       = 0;
    static final int COL_MOVIE_TITLE    = 1;
    static final int COL_MOVIE_POSTER   = 2;
    static final int COL_MOVIE_MOVIEID  = 3;
    static final int COL_MOVIE_OVERVIEW = 4;
    static final int COL_MOVIE_RATING   = 5;
    static final int LOADER = 0;
    private int FAVORITE = 0;

    TextView overview ;
    ImageView cover  ;
    ImageView poster;
    TextView rate ;
    TextView date;
    ListView review_listview ;

    FloatingActionButton fab ;
    String TAG = DetailsActivity.class.getSimpleName() ;
    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("Movies", (ArrayList<? extends Parcelable>) intent.getSerializableExtra("Array"));
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER,null,this) ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        overview = (TextView) view.findViewById(R.id.overview_details);
        cover  = (ImageView) view.findViewById(R.id.poster_details);
        poster  = (ImageView) view.findViewById(R.id.poster_in_details);
        rate = (TextView) view.findViewById(R.id.rate_detail);
        date = (TextView) view.findViewById(R.id.date);
        review_listview = (ListView) view.findViewById(R.id.review_listview);

         fab = (FloatingActionButton) view.findViewById(R.id.fab);





         intent = getActivity().getIntent() ;
        final int Position = intent.getIntExtra("Position",0) ;

        movie = (ArrayList<Movie>) intent.getSerializableExtra("Array");
        if (movie == null) {
           Bundle bundle = this.getArguments()  ;
            if (bundle!=null){
                movie = bundle.getParcelableArrayList("Array") ;

            }
        }
        System.out.println(movie);
        new FetchImagesById().execute(movie.get(Position).getMovieID()) ;
        //new FetchImagesById().execute(321612) ;

        //  new fetchVideosList().execute(movie.get(Position).getMovieID()) ;

        overview.setText(movie.get(Position).getOverview());
        getActivity().setTitle(movie.get(Position).getOriginalTitle());
        cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        rate.setText(Double.toString(movie.get(Position).getUserRating()));

        ImageView  video  = (ImageView) view.findViewById(R.id.Trailer_Img);

        date.setText(movie.get(Position).getReleaseDate());
        Picasso.with(getContext()).load(movie.get(Position).getPosterUrl()).into(poster);

        try {
            Trailers = new Network.fetchVideosList().execute(movie.get(Position).getMovieID()).get();
            Reviews = new Network.fetchReviews().execute(movie.get(Position).getMovieID()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FAVORITE == 0 ){
                    Snackbar.make(view, "Mark as favorite", Snackbar.LENGTH_SHORT).show();
                    fab.setImageResource(R.drawable.heartclicked);
                    //save movie
                    ContentValues FAVmovie = new ContentValues() ;
                    FAVORITE = 1 ;
                    Log.d(TAG, "uri: " + MovieContract.MovieEntry.CONTENT_URI);
                    FAVmovie.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,movie.get(Position).getMovieID());
                    FAVmovie.put(MovieContract.MovieEntry.COLUMN_TITLE,movie.get(Position).getOriginalTitle());
                    FAVmovie.put(MovieContract.MovieEntry.COLUMN_PPOSTER,movie.get(Position).getPosterUrl());
                    FAVmovie.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,movie.get(Position).getOverview());
                    FAVmovie.put(MovieContract.MovieEntry.COLUMN_DATE,movie.get(Position).getReleaseDate());
                    FAVmovie.put(MovieContract.MovieEntry.COLUMN_RATING,movie.get(Position).getUserRating());
                    FAVmovie.put(MovieContract.MovieEntry.COLUMN_FAV,Integer.valueOf(FAVORITE));
                    getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI , FAVmovie) ;
                    Log.d(TAG, "add movie to database");


                }else {
                    Snackbar.make(view, "Mark as favorite", Snackbar.LENGTH_SHORT).show();
                    fab.setImageResource(R.drawable.heart);
                    //delet movie
                    FAVORITE = 0 ;
                    ContentResolver contentResolver = getActivity().getContentResolver() ;
                    contentResolver.delete(MovieContract.MovieEntry.CONTENT_URI,
                                           MovieContract.MovieEntry.COLUMN_MOVIE_ID +"=?",
                                        new String[]{Integer.toString(movie.get(Position).getMovieID())} ) ;

                }
            }
        });
        // System.out.println(Images[0]);



        Picasso.with(getContext()).load( Network.getTrailerImage(Trailers.get(0).getKey()) ).into(video);
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenYoutubeIntent((Trailers.get(0).getKey() ));
            }
        });
        review_listview.setAdapter(new reviewAdapter(getContext(),R.layout.review_listview_row,Reviews));
        return view;
    }

    public  class  FetchImagesById extends AsyncTask<Integer,Void,String[]> {
        String[] array = {"","",""} ;

        @Override
        protected void onPostExecute(String[] strings) {
            // Images = strings ;
            ImageView cover  = (ImageView) getActivity().findViewById(R.id.poster_details);
            Picasso.with(getContext()).load(Network.getImageUrl(780,strings[0])).into(cover);
            super.onPostExecute(strings);
        }

        @Override
        protected String[] doInBackground(Integer... params) {
            OkHttpClient okHttpClient = new OkHttpClient();
            int MovieId = params[0] ;
            URL url = Network.buildImageURL(MovieId) ;
            System.out.println(url.toString());
            Request request = new Request.Builder().url(url).build() ;
            String JSON = null;
            try {
                Response response = okHttpClient.newCall(request).execute();
                JSON = response.body().string() ;
                JSONObject jsonObject = new JSONObject(JSON);
                JSONArray jsonArray = jsonObject.getJSONArray("backdrops") ;
                System.out.println(jsonArray.toString()) ;

                for(int i=0;i<3;i++) {
                    JSONObject imageobj = jsonArray.getJSONObject(i);
                    array[i] = imageobj.getString("file_path");
                    System.out.println(array[i]);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return array;
        }
    }

    public void OpenYoutubeIntent(String Key) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Network.getyoutube(Key))));
        System.out.println(Network.getyoutube(Key));
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                MovieContract.MovieEntry.CONTENT_URI,
                MOVIES_COLUMNS, null , null , null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()){

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }



    public class fetchVideosList extends AsyncTask<Integer,Void,ArrayList<Trailer> > {
        ArrayList<Trailer> arrayList = new ArrayList<>();


        @Override
        protected ArrayList<Trailer> doInBackground(Integer... params) {

            OkHttpClient okHttpClient = new OkHttpClient();

            URL url = buildVideosURL(params[0]);
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
                    String Trailer_name = movie.getString("name");
                    String Trailer_Key = movie.getString("key");

                    arrayList.add(new Trailer(Trailer_name,Trailer_Key));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Trailer> trailers) {
            super.onPostExecute(trailers);
            key = trailers.get(0).getKey() ;
            System.out.println(key);
            ImageView  video  = (ImageView) getActivity().findViewById(R.id.Trailer_Img);

            Picasso.with(getContext()).load( Network.getTrailerImage(trailers.get(0).getKey()) ).into(video);
        }
    }


}
