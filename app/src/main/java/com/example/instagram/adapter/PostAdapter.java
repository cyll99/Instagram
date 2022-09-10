package com.example.instagram.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.example.instagram.activity.DetailActivity;
import com.example.instagram.helper.TimeFormatter;
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

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvUsername,tvCreatedAt,tvDescription;

        ImageView ivPhoto, ivProfile;


        TextView icon_heart,icon_save,icon_comment,icon_send;

        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.username);
            tvCreatedAt = itemView.findViewById(R.id.createdAt);
            tvDescription = itemView.findViewById(R.id.description);
            ivPhoto = itemView.findViewById(R.id.photo);
            ivProfile = itemView.findViewById(R.id.profile);

            icon_heart = itemView.findViewById(R.id.heart);
            icon_save = itemView.findViewById(R.id.save);
            icon_comment = itemView.findViewById(R.id.comment);
            icon_send = itemView.findViewById(R.id.share);

            container = itemView.findViewById(R.id.container);

        }

        public void bind(Post post) {
            String timeDifference = TimeFormatter.getTimeDifference(post.getCreatedAt().toString());
            String username = post.getUser().getUsername();
            String description = post.getDescription();
            String picture_url = post.getImage().getUrl();
            String profile_url = post.getProfile().getUrl();
            String timestamp =TimeFormatter.getTimeStamp(post.getCreatedAt().toString());


            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2 Navigate on new activity on tap

                    Intent i =  new Intent(context, DetailActivity.class);

                    i.putExtra("username",username) ;
                    i.putExtra("date", timestamp);
                    i.putExtra("description", description);
                    i.putExtra("picture", picture_url);



                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, ivPhoto, "detail");

                    context.startActivity(i, options.toBundle());

                }
            });


            tvDescription.setText(description);
            tvUsername.setText(username);
            tvCreatedAt.setText(timeDifference);
            ParseFile image = post.getImage();

            Glide.with(context).load(profile_url).placeholder(R.drawable.profile)
                    .transform(new RoundedCorners(30)).into(ivPhoto);
            if(image != null){
                Glide.with(context).load(picture_url)
                        .transform(new RoundedCorners(30)).into(ivPhoto);

            }
        }
    }
}
