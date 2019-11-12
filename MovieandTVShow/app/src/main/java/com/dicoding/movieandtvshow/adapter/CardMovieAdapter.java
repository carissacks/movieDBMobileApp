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
import com.dicoding.movieandtvshow.activity.DetailMovieActivity;
import com.dicoding.movieandtvshow.db.MovieDatabase;

import java.util.ArrayList;
import java.util.List;

public class CardMovieAdapter extends RecyclerView.Adapter<CardMovieAdapter.CardViewHolder> {

    private ArrayList<MovieDatabase> listMovies = new ArrayList<>();
    private final Activity activity;

    public CardMovieAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CardMovieAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_movie, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardMovieAdapter.CardViewHolder holder, int position) {
        holder.tvTitle.setText(listMovies.get(position).getTitle());
        holder.tvDescription.setText(listMovies.get(position).getDescription());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342/" + listMovies.get(position).getPoster())
                .into(holder.imgPoster);

        holder.cvMovie.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, listMovies.get(position).getId());
                activity.startActivity(intent);
            }
        }));

    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public void setListMovies(List<MovieDatabase> listMovies) {
        this.listMovies.clear();
        this.listMovies.addAll(listMovies);
//        Log.e("Notify data", "Changed"+ this.listMovies.size());
        notifyDataSetChanged();
    }

//    public void addItem(MovieDatabase movie) {
//        this.listMovies.add(movie);
//        notifyItemInserted(listMovies.size() - 1);
//    }
//
//    public void removeItem(int position) {
//        this.listMovies.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, listMovies.size());
//    }

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
