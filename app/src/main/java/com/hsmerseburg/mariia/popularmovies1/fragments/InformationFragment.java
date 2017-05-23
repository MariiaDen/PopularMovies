package com.hsmerseburg.mariia.popularmovies1.fragments;

/**
 * Created by 2mdenyse on 28.03.2017.
 */
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hsmerseburg.mariia.popularmovies1.R;
import com.hsmerseburg.mariia.popularmovies1.data.FavoritesContract;
import com.hsmerseburg.mariia.popularmovies1.pojo.Movie;
import com.hsmerseburg.mariia.popularmovies1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformationFragment extends Fragment {

    @BindView(R.id.information_scroll_view) ScrollView scrollView;
    @BindView(R.id.details_movie_name) TextView movieName;
    @BindView(R.id.details_movie_year) TextView movieYear;
    @BindView(R.id.details_movie_duration) TextView movieDuration;
    @BindView(R.id.details_movie_vote_average) TextView movieVoteAverage;
    @BindView(R.id.details_movie_poster) ImageView moviePoster;
    @BindView(R.id.details_movie_overview) TextView movieOverview;
    @BindView(R.id.add_to_favorites) Button addToFavorites;
    @BindView(R.id.remove_from_favorites) Button removeFromFavorites;

    Movie movie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_information_fragment, container, false);
        ButterKnife.bind(this, rootView);
        Bundle b = this.getArguments();
        movie = (Movie) b.getSerializable("Movie");

        Uri uri = FavoritesContract.FavoritesEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(movie.getId()).build();
        Cursor cursor = this.getContext().getContentResolver()
                .query(uri, null, movie.getId(), null, FavoritesContract.FavoritesEntry.MOVIE_NAME);
        if(cursor.getCount() > 0) {
            addToFavorites.setVisibility(View.GONE);
            removeFromFavorites.setVisibility(View.VISIBLE);
        }
        else{
            addToFavorites.setVisibility(View.VISIBLE);
            removeFromFavorites.setVisibility(View.GONE);
        }
        cursor.close();
        if (b.get("Movie") != null) {
            movieName.setText(movie.getTitle());
            if (movie.getReleaseDate() != null && !movie.getReleaseDate().equals(""))
                movieYear.setText(getYear(movie.getReleaseDate()));
            movieVoteAverage.setText((movie.getVoteAverage()) + "/10");
            movieOverview.setText(movie.getOverview());
            if (!TextUtils.isEmpty(movie.getPosterPath())) {
                Picasso.with(this.getContext()).load(NetworkUtils.buildImageUrl(movie.getPosterPath()).toString())
                        .error(R.drawable.loader)
                        .placeholder(R.drawable.loader)
                        .fit().centerCrop()
                        .into(moviePoster);
            }
        }
        rootView.invalidate();
        return rootView;
    }

    public String getYear(String releaseDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
        Date parse = null;
        try {
            parse = sdf.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(parse);
        return Integer.toString(c.get(Calendar.YEAR));
    }
}