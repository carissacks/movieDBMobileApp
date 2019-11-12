package com.dicoding.movieappdata.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dicoding.movieappdata.R;
import com.dicoding.movieappdata.model.MovieDatabase;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private final Activity activity;
    private ArrayList<MovieDatabase> listMovies = new ArrayList<>();

    public CardAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListMovies(ArrayList<MovieDatabase> listMovies) {
        this.listMovies.clear();
        this.listMovies.addAll(listMovies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.tvTitle.setText(listMovies.get(position).getTitle());
        holder.tvDescription.setText(listMovies.get(position).getDescription());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342/" + listMovies.get(position).getPoster())
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle, tvDescription;
        final ImageView imgPoster;
        final CardView cvMovie;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            imgPoster = itemView.findViewById(R.id.img_item_poster);
            cvMovie = itemView.findViewById(R.id.cv_fav);
        }
    }
}
