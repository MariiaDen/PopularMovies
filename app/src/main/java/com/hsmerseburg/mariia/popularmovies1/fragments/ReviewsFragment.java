package com.hsmerseburg.mariia.popularmovies1.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsmerseburg.mariia.popularmovies1.R;
import com.hsmerseburg.mariia.popularmovies1.adapters.ReviewAdapter;
import com.hsmerseburg.mariia.popularmovies1.pojo.Movie;
import com.hsmerseburg.mariia.popularmovies1.pojo.Review;
import com.hsmerseburg.mariia.popularmovies1.pojo.ReviewWrapper;
import com.hsmerseburg.mariia.popularmovies1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 2mdenyse on 29.03.2017.
 */
public class ReviewsFragment extends Fragment {
    Review[] reviews;
    Movie movie;
    @BindView(R.id.reviews_view)RecyclerView rView;
    private ReviewAdapter rcAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reviews_fragment, container, false);
        ButterKnife.bind(this, rootView);
        Bundle b = this.getArguments();
        movie = (Movie) b.getSerializable("Movie");
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        executeStart(movie.getId());
        return rootView;
    }

    private void executeStart(String id) {
        URL reviewsUrl = NetworkUtils.buildReviewUrl(id);
        new DownloadTask().execute(reviewsUrl);
    }

    public class DownloadTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String results = null;
            try {
                results = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        public boolean isOnline() {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }

        @Override
        protected void onPostExecute(String s) {
            ObjectMapper mapper = new ObjectMapper();
            ReviewWrapper wrapperPojo;
            if (isOnline()) {
                try {
                    wrapperPojo = mapper.readValue(s, ReviewWrapper.class);
                    reviews = new Review[wrapperPojo.getReview().length];
                    for (int i = 0; i < wrapperPojo.getReview().length; i++) {
                        reviews[i] = new Review();
                        reviews[i] = wrapperPojo.getReview()[i];
                    }
                    rcAdapter = new ReviewAdapter(reviews);
                    rView.setAdapter(rcAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
                Toast.makeText(getActivity(),
                        "Oops, something went wrong. Please, check your internet connection",
                        Toast.LENGTH_SHORT).show();
        }
    }
}
