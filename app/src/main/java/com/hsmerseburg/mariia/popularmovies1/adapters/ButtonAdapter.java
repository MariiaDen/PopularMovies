package com.hsmerseburg.mariia.popularmovies1.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hsmerseburg.mariia.popularmovies1.R;
import com.hsmerseburg.mariia.popularmovies1.pojo.Trailer;

/**
 * Created by 2mdenyse on 24.03.2017.
 */
public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.RecyclerViewHolders> {
    public String[] trailersUrls;
    private Trailer[] trailers;

    public ButtonAdapter(Trailer[] trailers) {
        trailersUrls = new String[trailers.length];
        this.trailers = new Trailer [trailers.length];
        for(int i = 0; i < trailers.length; i++) {
            trailersUrls[i] = trailers[i].getKey();
            this.trailers[i] = trailers[i];
        }
    }

    @Override
    public ButtonAdapter.RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        Trailer trailer = trailers[position];
        if (!TextUtils.isEmpty(trailer.getKey())) {
            holder.trailerName.setText(trailers[position].getName());
        }
    }

    @Override
    public int getItemCount() {
        return (null != trailers ? trailers.length : 0);
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView trailerName;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            trailerName = (TextView)itemView.findViewById(R.id.trailer_name);
        }

        @Override
        public void onClick(View view) {
            Uri webpage = Uri.parse("https://www.youtube.com/watch?v=" + trailers[this.getLayoutPosition()].getKey());
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            view.getContext().startActivity(intent);
        }
    }
}
