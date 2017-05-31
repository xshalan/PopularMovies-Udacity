package app.com.example.shalan.popualrmovies.utils;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by noura on 06/04/2017.
 */

public interface SetOnMovieClickListener {
    public void OnMovieClick(int Position, ArrayList<? extends Parcelable> list) ;
}
