package app.com.example.shalan.popualrmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by noura on 14/04/2017.
 */

public class MovieContract  {

    public static final String AUTHORITY = "app.com.example.shalan.popualrmovies";

    public static final Uri BASE_CONTENT_URL = Uri.parse("Content://" + AUTHORITY) ;

    public static final String MOVIES_PATH = "movie" ;

    public static class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URL.buildUpon()
                                                .appendPath(MOVIES_PATH)
                                                .build() ;

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "original_title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_PPOSTER= "poster_image";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_DATE = "release_date";
        public static final String COLUMN_COVER = "cover_iamge" ;







    }
}
