package com.example.appmovie.adapter.adapter_rcv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmovie.R;
import com.example.appmovie.class_supported.ConvertImageStore;
import com.example.appmovie.model.Actor;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActorViewerAdapter extends RecyclerView.Adapter<ActorViewerAdapter.ActorViewHolder>{
    private List<Actor> actors;
    private ClickItem clickItem;

    public ActorViewerAdapter(ClickItem clickItem) {
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
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_actor_viewer, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        Actor actor = actors.get(position);

        if (actor == null) return;

        holder.bindHolder(actor);
        holder.layout.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_two));
        holder.layout.setOnClickListener(v -> clickItem.showInfo(actor));
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    public class ActorViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        LinearLayout layout;

        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.layoutActorItemViewer);
            layout = itemView.findViewById(R.id.layoutItemActor);
        }

        private void bindHolder(final Actor actor) {
            img.setImageBitmap(
                    ConvertImageStore.convertByArrToImage(actor.getImg())
            );
        }
    }

    public interface ClickItem {
        void showInfo(Actor actor);
    }
}
