package app.com.example.shalan.popualrmovies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.com.example.shalan.popualrmovies.utils.Network;
import app.com.example.shalan.popualrmovies.utils.RecyclerAdapter;
import app.com.example.shalan.popualrmovies.utils.SetOnMovieClickListener;


public class MainFragment extends Fragment implements SetOnMovieClickListener {
    RecyclerView recyclerView;
    public static RecyclerAdapter recyclerAdapter;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.main_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
       //  Drawable Hdivider = getDrawable(getContext(),R.drawable.divider);
       //  Drawable Vdivider = getDrawable(getActivity(),R.drawable.vdivider);

        // recyclerView.addItemDecoration(new GridDividerItemDecoration(Hdivider,Vdivider,2));
        recyclerAdapter = new RecyclerAdapter(getContext(), this);
        recyclerView.setAdapter(recyclerAdapter);


        System.out.println("------------------------------------------------------");
        ;
        new Network.fetchMovieList().execute(Network.popular);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("Movies", recyclerAdapter.getMoviesList());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.popular) {

            new Network.fetchMovieList().execute(Network.popular);

        } else if (itemId == R.id.top_rated) {

            new Network.fetchMovieList().execute(Network.high_rated);

        } else if (itemId == R.id.favorite) {
            Log.v("Clicked", "Clicked ! Favorite");
            new Network.fetchFavMovies().execute(getContext());

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void OnMovieClick(int Position, ArrayList list) {
        int Tablet_mode = getResources().getConfiguration().orientation;
        if (Tablet_mode != 1) {
            // two fragment
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("Array", list);
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);

            getFragmentManager().beginTransaction()
                    .add(R.id.details_frag, detailsFragment, "Dfrag2")
                    .commit();

        } else {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra("Position", Position);
            intent.putExtra("Array", list);
            startActivity(intent);
        }
    }


}
