package com.hsmerseburg.mariia.popularmovies1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsmerseburg.mariia.popularmovies1.adapters.ImageAdapter;
import com.hsmerseburg.mariia.popularmovies1.data.FavoritesContract;
import com.hsmerseburg.mariia.popularmovies1.pojo.Movie;
import com.hsmerseburg.mariia.popularmovies1.pojo.WrapperPojo;
import com.hsmerseburg.mariia.popularmovies1.utilities.NetworkUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String>,
        SharedPreferences.OnSharedPreferenceChangeListener{

    private static final int LOADER_ID = 12;
    private static final String SEARCH_QUERY_URL_EXTRA = "query";

    @BindView (R.id.recycler_view) RecyclerView rView;
    private ImageAdapter rcAdapter;
    @BindView (R.id.progress_bar) ProgressBar progressBar;
    @BindView (R.id.activity_main_layout) LinearLayout linearLayout;

    public WrapperPojo wrapperPojo;
    public Movie[] movies;
    private boolean sort;
    private String msg;

    ArrayList<URL> moviesSearchUrl;
    ArrayList<String> ids = new ArrayList<>();

    private void setupSharedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean(getString(R.string.background_settings_key), true))
            linearLayout.setBackgroundColor(Color.parseColor("#6242f4"));
        else
            linearLayout.setBackgroundColor(Color.parseColor("#2b484b"));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.background_settings_key))) {
            if (sharedPreferences.getBoolean(getString(R.string.background_settings_key), true))
                linearLayout.setBackgroundColor(Color.parseColor("#6242f4"));
            else
                linearLayout.setBackgroundColor(Color.parseColor("#2b484b"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupSharedPreferences();
        rView.setHasFixedSize(true);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        }
        else{
            rView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        }

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        if(savedInstanceState != null && savedInstanceState.containsKey("isSort") && savedInstanceState.containsKey("msg")) {
            sort = savedInstanceState.getBoolean("isSort");
            msg = savedInstanceState.getString("msg");
            if (msg.equals("favorites")){
                ids = getFromDatabase();
                msg = "favorites";
                executeStart2(ids);
            }
            else
                executeStart(sort, msg);
        }
        else{
            sort = true;
            msg = "popular";
            executeStart(sort, msg);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("msg", msg);
        outState.putBoolean("isSort", sort);
    }

    public ArrayList<String> getFromDatabase() {
        ids = new ArrayList<>();
        Cursor cursor = getContentResolver().query(FavoritesContract.FavoritesEntry.CONTENT_URI,
                null, null, null, FavoritesContract.FavoritesEntry.MOVIE_NAME);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ids.add(cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.MOVIE_ID)));
            cursor.moveToNext();
        }
        cursor.close();
        return ids;
    }

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem searchField = menu.findItem(R.id.search_field);
        SearchView searchView = (SearchView) searchField.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    msg = URLEncoder.encode(query, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                searchField.collapseActionView();
                sort = false;
                executeStart(sort, msg);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedMenuItem = item.getItemId();
        if(selectedMenuItem == R.id.action_popular){
            sort = true;
            msg = "popular";
            executeStart(sort, msg);
            return true;
        }
        if(selectedMenuItem == R.id.action_top_rated){
            sort = true;
            msg = "top_rated";
            executeStart(sort, msg);
            return true;
        }
        if(selectedMenuItem == R.id.action_my_favorite_movies){
            sort = false;
            msg = "favorites";
            ids = getFromDatabase();
            executeStart2(ids);
            return true;
        }
        if(selectedMenuItem == R.id.settings_menu){
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void executeStart(boolean isSort, String query) {
        URL moviesSearchUrl = NetworkUtils.buildUrl(isSort, query);
        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, moviesSearchUrl.toString());
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> searchLoader = loaderManager.getLoader(LOADER_ID);
        if (searchLoader == null)
            loaderManager.initLoader(LOADER_ID, queryBundle, this);
        else
            loaderManager.restartLoader(LOADER_ID, queryBundle, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            String mJson;
            @Override
            protected void onStartLoading(){
                super.onStartLoading();
                if (args == null) {
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                if (mJson != null) {
                    deliverResult(mJson);
                } else {
                    forceLoad();
                }
            }
            @Override
            public String loadInBackground() {
                String searchQueryUrlString = args.getString(SEARCH_QUERY_URL_EXTRA);
                if(searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString))
                    return  null;
                try {
                    URL searchUrl = new URL(searchQueryUrlString);
                    return NetworkUtils.getResponseFromHttpUrl(searchUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        ObjectMapper mapper = new ObjectMapper();
        if(isOnline()) {
            try {
                wrapperPojo = mapper.readValue(data, WrapperPojo.class);
                movies = new Movie[wrapperPojo.getMovies().length];
                for (int i = 0; i < wrapperPojo.getMovies().length; i++) {
                    movies[i] = new Movie();
                    movies[i] = wrapperPojo.getMovies()[i];
                }
                rcAdapter = new ImageAdapter(MainActivity.this, movies);
                rView.setAdapter(rcAdapter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            Toast.makeText(MainActivity.this,
                    "Oops, something went wrong. Please, check your internet connection",
                    Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {}

    private void executeStart2(ArrayList<String> query) {
        moviesSearchUrl = NetworkUtils.buildUrl2(query);
        URL [] a = moviesSearchUrl.toArray(new URL[moviesSearchUrl.size()]);
        new DownloadTask2().execute(a);
    }
    private class DownloadTask2 extends AsyncTask<URL, Void, String[]> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(URL... urls) {
            String[] results = null;
            try {
                results = NetworkUtils.getResponseFromHttpUrl2(urls);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String[] s) {
            ObjectMapper mapper = new ObjectMapper();
            if(isOnline()) {
                try {
                    movies = new Movie[moviesSearchUrl.size()];
                    for (int i = 0; i < moviesSearchUrl.size(); i++) {
                        movies[i] = new Movie();
                        movies[i] = mapper.readValue(s[i], Movie.class);
                    }
                    rcAdapter = new ImageAdapter(MainActivity.this, movies);
                    rView.setAdapter(rcAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
                Toast.makeText(MainActivity.this,
                        "Oops, something went wrong. Please, check your internet connection",
                        Toast.LENGTH_SHORT).show();
        }
    }
}