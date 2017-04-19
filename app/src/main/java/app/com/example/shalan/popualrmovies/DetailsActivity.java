package app.com.example.shalan.popualrmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import app.com.example.shalan.popualrmovies.Model.Movie;
import app.com.example.shalan.popualrmovies.utils.Network;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsActivity extends AppCompatActivity {
    static String[] Images = null ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_details,menu) ;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView overview = (TextView) findViewById(R.id.overview_details);
        ImageView cover  = (ImageView) findViewById(R.id.poster_details);
        ImageView poster  = (ImageView) findViewById(R.id.poster_in_details);
        TextView rate = (TextView) findViewById(R.id.rate_detail);
        TextView date = (TextView) findViewById(R.id.date);


        Intent intent = getIntent() ;
        int Position = intent.getIntExtra("Position",0) ;

      ArrayList<Movie> movie = (ArrayList<Movie>) intent.getSerializableExtra("Array");

        new FetchImagesById().execute(movie.get(Position).getMovieID()) ;

        overview.setText(movie.get(Position).getOverview());
        setTitle(movie.get(Position).getOriginalTitle());
        cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        rate.setText(Double.toString(movie.get(Position).getUserRating()));

        date.setText(movie.get(Position).getReleaseDate());
        Picasso.with(this).load(movie.get(Position).getPosterUrl()).into(poster);
       // System.out.println(Images[0]);
        //



    }

    public  class  FetchImagesById extends AsyncTask<Integer,Void,String[]> {
        String[] array = {"","",""} ;

        @Override
        protected void onPostExecute(String[] strings) {
           // Images = strings ;
            ImageView cover  = (ImageView) findViewById(R.id.poster_details);
            Picasso.with(DetailsActivity.this).load(Network.getImageUrl(780,strings[0])).into(cover);
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


}
