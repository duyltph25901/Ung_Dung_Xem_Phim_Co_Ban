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
import com.example.appmovie.model.Actor;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ActorAdapterAd extends RecyclerView.Adapter<ActorAdapterAd.ActorViewHolder>{
    private ClickItem clickItem;
    private List<Actor> actors;

    public ActorAdapterAd(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActorViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_director_actor_ad, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        Actor actor = actors.get(position);

        if (actor == null) return;

        holder.bindHolder(actor);
        holder.txtDelete.setOnClickListener(v -> clickItem.delete(actor));
        holder.txtUpdate.setOnClickListener(v -> clickItem.update(actor));
        holder.layoutItem.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_one));
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    public class ActorViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView img;
        private TextView txtFullName, txtDateOfBirth, txtUpdate, txtDelete;
        private ConstraintLayout layoutItem;

        public ActorViewHolder(@NonNull View itemView) {
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

        private void bindHolder(final Actor actor) {
            img.setImageBitmap(
                    ConvertImageStore.convertByArrToImage(actor.getImg())
            );
            txtFullName.setText(actor.getFullName());
            txtDateOfBirth.setText(actor.getDOB());
        }
    }

    public interface ClickItem{
        void update(Actor actor);
        void delete(Actor actor);
    }
}
