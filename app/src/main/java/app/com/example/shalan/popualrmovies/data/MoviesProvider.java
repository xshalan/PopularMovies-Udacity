package app.com.example.shalan.popualrmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by noura on 15/04/2017.
 */

public class MoviesProvider extends ContentProvider {
    public static final int Movie_Code = 100 ;

    private MovieDBHelper movieDBHelper ;

    private static final UriMatcher sUriMatcher = buileUriMatcher();

    public static UriMatcher buileUriMatcher(){
            UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH) ;
            uriMatcher.addURI(MovieContract.AUTHORITY,MovieContract.MOVIES_PATH,Movie_Code);

    return uriMatcher ;
    }
    @Override
    public boolean onCreate() {
        movieDBHelper = new MovieDBHelper(getContext()) ;
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase movieDB = movieDBHelper.getWritableDatabase() ;
        final int match = sUriMatcher.match(uri) ;

        Uri returnUri ;
        switch (match) {
            case Movie_Code :
                long id = movieDB.insert(MovieContract.MovieEntry.TABLE_NAME,null,values) ;
                    if(id > 0){
                        returnUri = ContentUris.withAppendedId(uri,id) ;
                    }else {
                        throw new SQLException("Failed to insert") ;
                    }
                break;
            default:
                throw new UnsupportedOperationException("Unknow Uri " + uri)  ;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase movieDB = movieDBHelper.getWritableDatabase() ;
        final int match = sUriMatcher.match(uri) ;
        int rowsDeleted;
        switch (match) {
            case Movie_Code :
                rowsDeleted = movieDB.delete(MovieContract.MovieEntry.TABLE_NAME,selection,selectionArgs) ;

                break;
            default:
                throw new UnsupportedOperationException("Unknow Uri " + uri)  ;
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
