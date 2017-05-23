package com.hsmerseburg.mariia.popularmovies1.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by 2mdenyse on 30.03.2017.
 */
public class FavoritesContract {

    public static final String AUTHORITY = "com.hsmerseburg.mariia.popularmovies1";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITES = "favorites";

    public static final class FavoritesEntry {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();
        public final static String TABLE_NAME = "favorites";
        public final static String MOVIE_ID = "movieId";
        public final static String MOVIE_NAME = "movieName";
    }
}
