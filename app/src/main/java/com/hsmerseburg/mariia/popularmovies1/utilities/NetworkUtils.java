/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hsmerseburg.mariia.popularmovies1.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    public static final String MOVIE_URL = "https://api.themoviedb.org/3/movie";
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    public static final String KEY = "SOME_KEY";

    public static URL buildUrl(boolean isSort, String query) {
        Uri builtUri;
        if(isSort)
            builtUri = Uri.parse(MOVIE_URL).buildUpon()
                    .appendPath(query)
                    .appendQueryParameter("api_key", KEY)
                    .build();
        else{
            builtUri = Uri.parse("https://api.themoviedb.org/3/search/movie").buildUpon()
                    .appendQueryParameter("api_key", KEY)
                    .appendQueryParameter("query", query)
                    .build();
            Log.e("URI", builtUri.toString());
            //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
        }
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static ArrayList<URL> buildUrl2(ArrayList<String> query) {
        Uri builtUri;
        ArrayList<URL> url = new ArrayList<>();
        for (int i = 0; i < query.size(); i++) {
            builtUri = Uri.parse(MOVIE_URL).buildUpon()
                    .appendPath(query.get(i))
                    .appendQueryParameter("api_key", KEY)
                    .build();
            try {
                url.add(new URL(builtUri.toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.v(TAG, "Built URI " + url);
        }
        return url;
    }


    public static URL buildImageUrl(String posterUrl) {
        Uri builtUri;
        builtUri = Uri.parse(IMAGE_URL).buildUpon()
                .appendEncodedPath("w185")
                .appendEncodedPath(posterUrl)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static URL buildVideoUrl(String id) {
        Uri builtUri;
        builtUri = Uri.parse(MOVIE_URL).buildUpon()
                .appendPath(id)
                .appendPath("videos")
                .appendQueryParameter("api_key", KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built video URI " + url);
        return url;
    }

    public static URL buildReviewUrl(String id) {
        Uri builtUri = Uri.parse(MOVIE_URL).buildUpon()
                .appendPath(id)
                .appendPath("reviews")
                .appendQueryParameter("api_key", KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built reviews URI " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String[] getResponseFromHttpUrl2(URL... url) throws IOException {
        HttpURLConnection[] urlConnection = new HttpURLConnection[url.length];
        String [] result = new String [url.length];
        try {
            InputStream[] in = new InputStream[url.length];
            for (int i = 0; i < url.length; i++) {
                urlConnection[i] =(HttpURLConnection) url[i].openConnection();
                in[i] = urlConnection[i].getInputStream();
                Scanner scanner = new Scanner(in[i]);
                scanner.useDelimiter("\\A");
                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    result[i] = scanner.next();
                } else {
                    result[i] = null;
                }
            }
        } finally {
            for (HttpURLConnection urlC : urlConnection)
                urlC.disconnect();
            return result;
        }
    }
}