package com.dicoding.movieandtvshow.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dicoding.movieandtvshow.CustomOnItemClickListener;
import com.dicoding.movieandtvshow.R;
import com.dicoding.movieandtvshow.activity.DetailTvActivity;
import com.dicoding.movieandtvshow.db.TvShowDatabase;

import java.util.ArrayList;
import java.util.List;

public class CardTvAdapter extends RecyclerView.Adapter<CardTvAdapter.CardViewHolder> {

    private final Activity activity;
    private ArrayList<TvShowDatabase> listTvShows = new ArrayList<>();

    public CardTvAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CardTvAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_movie, parent, false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardTvAdapter.CardViewHolder holder, int position) {
        holder.tvTitle.setText(listTvShows.get(position).getTitle());
        holder.tvDescription.setText(listTvShows.get(position).getDescription());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342/" + listTvShows.get(position).getPoster())
                .into(holder.imgPoster);
        holder.cvTvShow.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailTvActivity.class);
                intent.putExtra(DetailTvActivity.EXTRA_TV_ID, listTvShows.get(position).getId());
                activity.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listTvShows.size();
    }

//    public ArrayList<TvShowDatabase> getListTvShows(){
//        return listTvShows;
//    }

    public void setListTvShows(List<TvShowDatabase> listTvShows) {
        this.listTvShows.clear();
        this.listTvShows.addAll(listTvShows);
        notifyDataSetChanged();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDescription;
        final ImageView imgPoster;
        final CardView cvTvShow;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            imgPoster = itemView.findViewById(R.id.img_item_poster);
            cvTvShow = itemView.findViewById(R.id.cv_fav);
        }
    }
}
