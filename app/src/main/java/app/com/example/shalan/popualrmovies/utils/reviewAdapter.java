package app.com.example.shalan.popualrmovies.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.example.shalan.popualrmovies.Model.Review;
import app.com.example.shalan.popualrmovies.R;

/**
 * Created by noura on 20/04/2017.
 */

public class reviewAdapter extends ArrayAdapter {
    private int layoutResource;
    List<Review> reviews = new ArrayList<>() ;
    public reviewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        layoutResource = resource ;
        reviews = objects ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView ==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(layoutResource, null);

        }

        TextView author = (TextView) convertView.findViewById(R.id.author);
        TextView comment = (TextView) convertView.findViewById(R.id.content);

        author.setText(reviews.get(position).getAuthor());
        comment.setText(reviews.get(position).getComment());

        return convertView ;
    }
}
