package app.com.example.shalan.popualrmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by noura on 14/04/2017.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    final static  String DATABASE_NAME = "movies.db";
    final static  int    DATABASE_VERSION = 1;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_MOVIES_TABLE = "CREATE TABLE " +
                    MovieContract.MovieEntry.TABLE_NAME + " (" +
                    MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                    MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                    MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                    MovieContract.MovieEntry.COLUMN_PPOSTER + "TEXT," +
                    MovieContract.MovieEntry.COLUMN_RATING + " INTEGER, " +
                    MovieContract.MovieEntry.COLUMN_DATE + " TEXT," +
                    MovieContract.MovieEntry.COLUMN_COVER + "TEXT);";
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+ MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
