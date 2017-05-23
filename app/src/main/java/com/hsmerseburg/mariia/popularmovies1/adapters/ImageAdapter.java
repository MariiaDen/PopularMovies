package com.hsmerseburg.mariia.popularmovies1.adapters;

/**
 * Created by 2mdenyse on 21.03.2017.
 */
import com.hsmerseburg.mariia.popularmovies1.MovieDetailsActivity;
import com.hsmerseburg.mariia.popularmovies1.R;
import com.hsmerseburg.mariia.popularmovies1.pojo.Movie;
import com.hsmerseburg.mariia.popularmovies1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.RecyclerViewHolders> {
    private Context mContext;
    public String[] posterUrls;
    private Movie[] movies;

    public ImageAdapter(Context c, Movie[] movies) {
        mContext = c;
        posterUrls = new String[movies.length];
        this.movies = new Movie[movies.length];
        for(int i = 0; i < movies.length; i++) {
            posterUrls[i] = movies[i].getPosterPath();
            this.movies[i] = movies[i];
        }
    }

    public String getItem(int position) {
        return posterUrls[position];
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        Movie movie = movies[position];
        String url = getItem(position);
        if (!TextUtils.isEmpty(movie.getPosterPath())) {
            Picasso.with(mContext).load(NetworkUtils.buildImageUrl(url).toString())
                    .error(R.drawable.loader)
                    .placeholder(R.drawable.loader)
                    .fit().centerCrop()
                    .into(holder.poster);
        }
        holder.movieName.setText(movies[position].getTitle());
    }

    @Override
    public int getItemCount() {
        return (null != movies ? movies.length : 0);
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView movieName;
        public ImageView poster;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            movieName = (TextView)itemView.findViewById(R.id.movie_name);
            poster = (ImageView)itemView.findViewById(R.id.poster);
        }

        @Override
        public void onClick(View view) {
            Class childActivity = MovieDetailsActivity.class;
            Context context = view.getContext();
            Intent intent = new Intent(context, childActivity);
            intent.putExtra("Movie", movies[getAdapterPosition()]);
            context.startActivity(intent);
        }
    }
}