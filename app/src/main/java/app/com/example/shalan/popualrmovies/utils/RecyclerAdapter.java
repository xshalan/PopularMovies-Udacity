package app.com.example.shalan.popualrmovies.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.com.example.shalan.popualrmovies.Model.Movie;
import app.com.example.shalan.popualrmovies.R;

/**
 * Created by noura on 06/04/2017.
 */

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.myHolder>{
    ArrayList<Movie> movies ;
    String[] ImagesArray = null ;
    Context context ;
    SetOnMovieClickListener movieClickListener ;

    public RecyclerAdapter(Context context ,SetOnMovieClickListener ListenerClick){
        this.context = context ;
        movieClickListener = ListenerClick ;

    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_layout,parent,false);

        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {
            Movie movie = movies.get(position);
        holder.title.setText(movie.getOriginalTitle());
        holder.rating.setText(Double.toString(movie.getUserRating()));
        Picasso.with(context).load(movie.getPosterUrl()).into(holder.poster);

    }



    @Override
    public int getItemCount() {
        if(movies==null)
            return 0 ;

        return movies.size();
    }

    public void setMoviesList(ArrayList<Movie> list){

        movies = list ;
        notifyDataSetChanged();

    }

    public ArrayList getMoviesList(){
        if (movies != null){
            return  movies ;
        }else {
            return null ;
        }

    }
    public void setListener(SetOnMovieClickListener listener){
        movieClickListener = listener ;
    }

    public class myHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title ;
        TextView rating ;
        ImageView poster ;


        public myHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.titleMovie);
            rating = (TextView) view.findViewById(R.id.rating);
            poster = (ImageView) view.findViewById(R.id.poster);
            poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int positoin = getAdapterPosition() ;
            movieClickListener.OnMovieClick(positoin, movies);
        }


    }

    public void ImagesArray(String[] array){
        ImagesArray = array ;
    }




}
