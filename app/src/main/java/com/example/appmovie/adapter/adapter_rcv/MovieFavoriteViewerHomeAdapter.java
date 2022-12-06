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
import com.example.appmovie.model.MovieFavorite;

import java.util.List;

public class MovieFavoriteViewerHomeAdapter extends RecyclerView.Adapter<MovieFavoriteViewerHomeAdapter.MovieViewHolder>{
    private List<MovieFavorite> movies;
    private ClickItem clickItem;

    public MovieFavoriteViewerHomeAdapter(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public void setMovies(List<MovieFavorite> movies) {
        this.movies = movies;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_viewer_home_all_movie, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieFavorite movie = movies.get(position);

        if (movie == null) return;

        holder.bindHolder(movie);
        holder.poster.setOnClickListener(v -> clickItem.clickItem(movie));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            poster = itemView.findViewById(R.id.posterAllItemMovieViewer);
        }

        private void bindHolder(final MovieFavorite movie) {
            poster.setImageBitmap(
                    ConvertImageStore.convertByArrToImage(movie.getPoster())
            );
        }
    }

    public interface ClickItem {
        void clickItem(MovieFavorite movie);
    }
}
