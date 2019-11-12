package com.dicoding.movieandtvshow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dicoding.movieandtvshow.R;
import com.dicoding.movieandtvshow.model.Movie;

import java.util.ArrayList;

public class GridViewMovieAdapter extends RecyclerView.Adapter<GridViewMovieAdapter.GridViewViewHolder> {

    private final ArrayList<Movie> movieList;
    private OnItemClickCallBack onItemClickCallBack;

    public GridViewMovieAdapter(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    public void setOnItemClickCallBack(OnItemClickCallBack onItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack;
    }

    @NonNull
    @Override
    public GridViewMovieAdapter.GridViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_movie, parent, false);
        return new GridViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GridViewMovieAdapter.GridViewViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342/" + movieList.get(position).getPoster())
                .into(holder.imgPoster);
        holder.tvTitle.setText(movieList.get(position).getTitle());
        holder.tvUserRate.setText(String.valueOf(movieList.get(position).getUserScore()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallBack.onItemClicked(movieList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface OnItemClickCallBack {
        void onItemClicked(Movie data);
    }

    public class GridViewViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPoster;
        TextView tvTitle;
        TextView tvUserRate;

        public GridViewViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvUserRate = itemView.findViewById(R.id.tv_userRate);
        }
    }
}
