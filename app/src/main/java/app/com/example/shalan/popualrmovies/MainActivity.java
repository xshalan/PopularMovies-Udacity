package app.com.example.shalan.popualrmovies;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dgreenhalgh.android.simpleitemdecoration.grid.GridDividerItemDecoration;

import java.util.ArrayList;

import app.com.example.shalan.popualrmovies.Model.Movie;
import app.com.example.shalan.popualrmovies.utils.Network;
import app.com.example.shalan.popualrmovies.utils.RecyclerAdapter;
import app.com.example.shalan.popualrmovies.utils.SetOnMovieClickListener;

public class MainActivity extends AppCompatActivity implements SetOnMovieClickListener{
    RecyclerView recyclerView ;
    public static RecyclerAdapter recyclerAdapter ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater() ;
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        new Network.fetchMovieList().execute(Network.popular);
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId() ;
        if(itemId == R.id.popular){

            new Network.fetchMovieList().execute(Network.popular);

        }else if(itemId == R.id.top_rated){

            new Network.fetchMovieList().execute(Network.high_rated);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        Drawable Hdivider = ContextCompat.getDrawable(this,R.drawable.divider);
        Drawable Vdivider = ContextCompat.getDrawable(this,R.drawable.vdivider);

        recyclerView.addItemDecoration(new GridDividerItemDecoration(Hdivider,Vdivider,2));
        recyclerAdapter = new RecyclerAdapter(MainActivity.this,this) ;
        recyclerView.setAdapter(recyclerAdapter);




        System.out.println("------------------------------------------------------"); ;


    }

    @Override
    public void OnMovieClick(int Position,ArrayList<Movie> list) {

        Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra("Position",Position) ;
        intent.putExtra("Array",list) ;
        startActivity(intent);
    }





}
