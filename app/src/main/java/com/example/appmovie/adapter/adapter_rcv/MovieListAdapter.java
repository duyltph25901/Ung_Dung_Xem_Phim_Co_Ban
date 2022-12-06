package com.example.appmovie.adapter.adapter_rcv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovie.R;
import com.example.appmovie.class_supported.ConvertImageStore;
import com.example.appmovie.model.Movie;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>{
    private List<Movie> movies;
    private ClickItem clickItem;

    public MovieListAdapter(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieListViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_viewer_home_all_movie, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
        Movie movie = movies.get(position);

        if (movie == null) return;

        holder.binHolder(movie);
        holder.poster.setOnClickListener(v -> clickItem.clickItem(movie));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieListViewHolder extends RecyclerView.ViewHolder {
        private ImageView poster;

        public MovieListViewHolder(@NonNull View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.posterAllItemMovieViewer);
        }

        private void binHolder(final Movie movie) {
            poster.setImageBitmap(
                    ConvertImageStore.convertByArrToImage(movie.getPoster())
            );
        }
    }

    public interface ClickItem {
        void clickItem(Movie movie);
    }
}
