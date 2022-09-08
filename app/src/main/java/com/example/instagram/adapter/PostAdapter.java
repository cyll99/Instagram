package com.example.instagram.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.example.instagram.models.Post;
import com.parse.ParseFile;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private final Context context;
    private final List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvUsername;
        private TextView tvCreatedAt;
        private ImageView ivPhoto;

        private TextView tvDescription;

        private TextView icon_heart;
        private TextView icon_save;
        private TextView icon_comment;
        private TextView icon_send;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.username);
            tvCreatedAt = itemView.findViewById(R.id.createdAt);
            tvDescription = itemView.findViewById(R.id.description);
            ivPhoto = itemView.findViewById(R.id.photo);

            icon_heart = itemView.findViewById(R.id.heart);
            icon_save = itemView.findViewById(R.id.save);
            icon_comment = itemView.findViewById(R.id.comment);
            icon_send = itemView.findViewById(R.id.send);


        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvCreatedAt.setText(post.getCreatedAt().toString());
            ParseFile image = post.getImage();
            if(image != null){
                Glide.with(context).load(post.getImage().getUrl()) .centerCrop() // scale image to fill the entire ImageView
                        .transform(new RoundedCorners(30)).into(ivPhoto);

            }
        }
    }
}
