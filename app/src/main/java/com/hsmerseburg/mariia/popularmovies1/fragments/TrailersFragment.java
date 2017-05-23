package com.hsmerseburg.mariia.popularmovies1.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsmerseburg.mariia.popularmovies1.R;
import com.hsmerseburg.mariia.popularmovies1.adapters.ButtonAdapter;
import com.hsmerseburg.mariia.popularmovies1.pojo.Movie;
import com.hsmerseburg.mariia.popularmovies1.pojo.Trailer;
import com.hsmerseburg.mariia.popularmovies1.pojo.TrailerWrapper;
import com.hsmerseburg.mariia.popularmovies1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 2mdenyse on 28.03.2017.
 */
public class TrailersFragment extends Fragment {
    Trailer[] trailers;
    Movie movie;
    @BindView(R.id.trailers_view)RecyclerView rView;
    private ButtonAdapter rcAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trailers_fragment, container, false);
        ButterKnife.bind(this, rootView);
        Bundle b = this.getArguments();
        movie = (Movie) b.getSerializable("Movie");
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        executeStart(movie.getId());
        return rootView;
    }

    private void executeStart(String id) {
        URL trailersUrl = NetworkUtils.buildVideoUrl(id);
        new DownloadTask().execute(trailersUrl);
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
            TrailerWrapper wrapperPojo;
            if (isOnline()) {
                try {
                    wrapperPojo = mapper.readValue(s, TrailerWrapper.class);
                    trailers = new Trailer[wrapperPojo.getTrailers().length];
                    for (int i = 0; i < wrapperPojo.getTrailers().length; i++) {
                        trailers[i] = new Trailer();
                        trailers[i] = wrapperPojo.getTrailers()[i];
                        Log.v("TRAILERS:",trailers[i].getName());
                    }
                    rcAdapter = new ButtonAdapter(trailers);
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
