package app.com.example.shalan.popualrmovies;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import app.com.example.shalan.popualrmovies.utils.Network;

public class MainActivity extends AppCompatActivity    {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater() ;
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
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


        // 1 for portrait
        int Tablet_mode = getResources().getConfiguration().orientation ;
        MainFragment mainFragment = new MainFragment() ;
        if (Tablet_mode !=1) {
            setContentView(R.layout.tablet_main_activity);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
            fragmentTransaction.add(R.id.main_frag,mainFragment, "Frag1");
//            fragmentTransaction.add(R.id.details_fragment,detailsFragment, "Frag2");
           fragmentTransaction.commit();
        }else {
            setContentView(R.layout.activity_main);
        }



    }







}
