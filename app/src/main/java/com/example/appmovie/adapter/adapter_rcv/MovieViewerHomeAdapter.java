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
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MovieViewerHomeAdapter extends RecyclerView.Adapter<MovieViewerHomeAdapter.MovieViewHolder>{
    private List<Movie> movies;
    private ClickItem clickItem;

    public MovieViewerHomeAdapter(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_viewer_home, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

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

            poster = itemView.findViewById(R.id.posterItemMovieViewer);
        }

        private void bindHolder(final Movie movie) {
            poster.setImageBitmap(
                    ConvertImageStore.convertByArrToImage(movie.getPoster())
            );
        }
    }

    public interface ClickItem {
        void clickItem(Movie movie);
    }
}
