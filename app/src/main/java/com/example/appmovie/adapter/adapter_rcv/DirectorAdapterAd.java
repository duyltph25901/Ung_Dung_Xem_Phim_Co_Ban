package com.example.appmovie.adapter.adapter_rcv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovie.R;
import com.example.appmovie.class_supported.ConvertImageStore;
import com.example.appmovie.model.Director;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class DirectorAdapterAd extends RecyclerView.Adapter<DirectorAdapterAd.DirectorViewHolder>{
    private List<Director> directors;
    private ClickItem clickItem;

    public DirectorAdapterAd(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DirectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DirectorViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_director_actor_ad, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DirectorViewHolder holder, int position) {
        Director director = directors.get(position);

        if (director == null) return;

        holder.bindHolder(director);
        holder.txtDelete.setOnClickListener(v -> clickItem.delete(director));
        holder.txtUpdate.setOnClickListener(v -> clickItem.update(director));
        holder.layoutItem.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_one));
    }

    @Override
    public int getItemCount() {
        return directors.size();
    }

    public class DirectorViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView img;
        private TextView txtFullName, txtDateOfBirth, txtUpdate, txtDelete;
        private ConstraintLayout layoutItem;

        public DirectorViewHolder(@NonNull View itemView) {
            super(itemView);

            init(itemView);
        }

        private void init(View itemView) {
            img = itemView.findViewById(R.id.imgDirectorActorItemAd);
            txtFullName = itemView.findViewById(R.id.txtFullNameDirectorActorItemAd);
            txtDateOfBirth = itemView.findViewById(R.id.txtDOBDirectorActorItemAd);
            txtUpdate = itemView.findViewById(R.id.txtUpdateDirectorActorItem);
            txtDelete = itemView.findViewById(R.id.txtDeleteDirectorActorItem);
            layoutItem = itemView.findViewById(R.id.layoutDirectorActorItemAd);
        }

        private void bindHolder(final Director director) {
            img.setImageBitmap(
                    ConvertImageStore.convertByArrToImage(director.getImage())
            );
            txtFullName.setText(director.getFullName());
            txtDateOfBirth.setText(director.getDOB());
        }
    }

    public interface ClickItem {
        void update(Director director);
        void delete(Director director);
    }
}
