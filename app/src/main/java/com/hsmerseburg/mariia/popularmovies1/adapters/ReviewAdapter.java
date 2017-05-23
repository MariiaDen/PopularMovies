package com.hsmerseburg.mariia.popularmovies1.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hsmerseburg.mariia.popularmovies1.R;
import com.hsmerseburg.mariia.popularmovies1.pojo.Review;

/**
 * Created by 2mdenyse on 29.03.2017.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.RecyclerViewHolders> {
    private Review[] reviews;

    public ReviewAdapter(Review[] reviews) {
        this.reviews = new Review[reviews.length];
        for(int i = 0; i < reviews.length; i++) {
            this.reviews[i] = reviews[i];
        }
    }

    @Override
    public ReviewAdapter.RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        Review trailer = reviews[position];
        if (!TextUtils.isEmpty(trailer.getUrl())) {
            holder.authorName.setText(reviews[position].getAuthor());
            holder.reviewContent.setText(reviews[position].getContent());
        }
    }

    @Override
    public int getItemCount() {
        return (null != reviews ? reviews.length : 0);
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder{
        public TextView authorName;
        public TextView reviewContent;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            authorName = (TextView)itemView.findViewById(R.id.author_name);
            reviewContent = (TextView)itemView.findViewById(R.id.review_content);
        }
    }
}