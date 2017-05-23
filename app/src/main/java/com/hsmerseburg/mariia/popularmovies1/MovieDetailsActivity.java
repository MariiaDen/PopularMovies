package com.hsmerseburg.mariia.popularmovies1;

/**
 * Created by 2mdenyse on 21.03.2017.
 */
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hsmerseburg.mariia.popularmovies1.adapters.PagerAdapter;
import com.hsmerseburg.mariia.popularmovies1.data.FavoritesContract;
import com.hsmerseburg.mariia.popularmovies1.pojo.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    @BindView(R.id.details_linear_layout) LinearLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra("Movie")) {
            movie = (Movie) getIntent().getSerializableExtra("Movie");
            actionBar.setTitle(movie.getTitle());
            actionBar.show();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("Movie", movie);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Information"));
        tabLayout.addTab(tabLayout.newTab().setText("Trailers"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), bundle);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    public void onClickAddToFavorites(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoritesContract.FavoritesEntry.MOVIE_ID, movie.getId());
        contentValues.put(FavoritesContract.FavoritesEntry.MOVIE_NAME, movie.getOriginalTitle());
        Uri uri = getContentResolver()
                .insert(FavoritesContract.FavoritesEntry.CONTENT_URI, contentValues);
        if(uri != null)
            Toast.makeText(this, "Movie added to favorites!", Toast.LENGTH_SHORT).show();
        refresh();
    }

    public void onClickRemoveFromFavorites(View view) {
        Uri uri = FavoritesContract.FavoritesEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(movie.getId()).build();
        getContentResolver().delete(uri, null, null);
        refresh();
    }

    public void refresh(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}