package com.example.appmovie.adapter.adapter_rcv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovie.R;
import com.example.appmovie.class_supported.ConvertImageStore;
import com.example.appmovie.model.Movie;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MovieAdapterAd extends RecyclerView.Adapter<MovieAdapterAd.MovieAdaperViewHolder>{
    private ClickItem clickItem;
    private List<Movie> movies;

    public MovieAdapterAd(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieAdaperViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_movie_ad, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdaperViewHolder holder, int position) {
        Movie movie = movies.get(position);

        if (movie == null)  return;

        holder.bindHolder(movie);
        holder.layoutItem.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_one));
        holder.txtDelete.setOnClickListener(v -> clickItem.delete(movie));
        holder.txtUpdate.setOnClickListener(v -> clickItem.update(movie));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieAdaperViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView posterMovie;
        private TextView txtMovieName;
        private RatingBar ratingBar;
        private TextView txtCreatedBy;
        private TextView txtStory;
        private TextView txtUpdate, txtDelete;
        private ConstraintLayout layoutItem;

        public MovieAdaperViewHolder(@NonNull View itemView) {
            super(itemView);

            init(itemView);
        }

        private void init(final View view) {
            posterMovie = view.findViewById(R.id.imageMovieItemAd);
            txtMovieName = view.findViewById(R.id.txtMovieNameItemAd);
            ratingBar = view.findViewById(R.id.ratingBarMovieItemAd);
            txtCreatedBy = view.findViewById(R.id.txtCreatedByMovieItemAd);
            txtStory = view.findViewById(R.id.txtStoryMovieItemAd);
            txtUpdate = view.findViewById(R.id.txtUpdateMovieItemAd);
            txtDelete = view.findViewById(R.id.txtDeleteMovieItemAd);
            layoutItem = view.findViewById(R.id.layoutItemMovieAd);
        }

        private void bindHolder(final Movie movie) {
            posterMovie.setImageBitmap(ConvertImageStore.convertByArrToImage(movie.getPoster()));
            txtMovieName.setText(movie.getMovieName());
            ratingBar.setRating((float) (movie.getPoint()/2));
            txtCreatedBy.setText(movie.getDirectors().toString());
            txtStory.setText(movie.getSummary());
        }
    }

    public interface ClickItem {
        void delete(Movie movie);
        void update(Movie movie);
    }
}
