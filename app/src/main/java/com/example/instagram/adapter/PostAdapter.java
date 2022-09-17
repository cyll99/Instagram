package com.example.instagram.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.example.instagram.activity.CommentActivity;
import com.example.instagram.activity.DetailActivity;
import com.example.instagram.activity.PictureActivity;
import com.example.instagram.fragments.ProfilFragment;
import com.example.instagram.helper.Constants;
import com.example.instagram.helper.TimeFormatter;
import com.example.instagram.models.Post;
import com.example.instagram.models.User;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.parceler.Parcels;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private static final String TAG = "PostAdapter";
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
        try {
            holder.bind(post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

        TextView tvUsername,tvCreatedAt,tvDescription,tvNumLikes;

        ImageView ivPhoto, ivProfile;


        TextView icon_heart,icon_heart_red, icon_save,icon_comment,icon_send;

        RelativeLayout container, containerForProfile;
        List<String> likers;
        int numlikes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.username);
            tvCreatedAt = itemView.findViewById(R.id.createdAt);
            tvDescription = itemView.findViewById(R.id.description);
            ivPhoto = itemView.findViewById(R.id.photo);
            ivProfile = itemView.findViewById(R.id.profile);

            icon_heart = itemView.findViewById(R.id.heart);
            icon_heart_red = itemView.findViewById(R.id.heart_red);
            icon_save = itemView.findViewById(R.id.save);
            icon_comment = itemView.findViewById(R.id.comment);
            icon_send = itemView.findViewById(R.id.share);
            tvNumLikes = itemView.findViewById(R.id.numLikes);

            container = itemView.findViewById(R.id.container);
            containerForProfile = itemView.findViewById(R.id.container_pro);

        }

        public void bind(Post post) throws JSONException {

            ParseUser currentUser = ParseUser.getCurrentUser();

            String timeDifference = TimeFormatter.getTimeDifference(post.getCreatedAt().toString());
            String username = post.getUser().getUsername();
            String description = post.getDescription();
            String picture_url = post.getImage().getUrl();
            String profile_url = post.getUser().getParseFile(User.KEY_PROFILE).getUrl();

            likers = Post.fromJsonArray(post.getLikers()); // list of likers
            numlikes = likers.size();
            tvNumLikes.setText(String.valueOf(numlikes));


            Constants.display_heart(icon_heart,icon_heart_red,likers, currentUser);//display the icon heart


            containerForProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToProfile(Parcels.wrap(post));
                }
            });

            // user clicks icon to unlike
            icon_heart_red.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Constants.UserDislikes(icon_heart,icon_heart_red,post,likers, currentUser,tvNumLikes);
                    numlikes--;
                post.setNumLikes(numlikes);
                    tvNumLikes.setText(String.valueOf(numlikes));


                }
            });

            // user clicks on icon to like
            icon_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  Constants.UserLikes(icon_heart,icon_heart_red,currentUser,post,TAG,context,likers,tvNumLikes);
                    numlikes++;
                    post.setNumLikes(numlikes);
                    tvNumLikes.setText(String.valueOf(numlikes));


                }
            });
            icon_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i =  new Intent(context, CommentActivity.class);
                    i.putExtra("post", Parcels.wrap(post));
                    context.startActivity(i);

                }
            });
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2 Navigate on new activity on tap

                    Intent i =  new Intent(context, DetailActivity.class);
                    i.putExtra(Constants.DATA, Parcels.wrap(post));




                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, ivPhoto, Constants.TRANSITION);

                    context.startActivity(i, options.toBundle());

                }
            });


            tvDescription.setText(description);
            tvUsername.setText(username);
            tvCreatedAt.setText(timeDifference);
            ParseFile image = post.getImage();

            Glide.with(context).load(profile_url)
                    .transform(new RoundedCorners(Constants.ROUNDED_PROFILE)).into(ivProfile);
            if(image != null){
                Glide.with(context).load(picture_url)
                        .transform(new RoundedCorners(Constants.ROUNDED_PICTURE)).into(ivPhoto);

            }


        }
    }
    // passing data to profile fragment
    private void goToProfile(Parcelable post) {
        Constants.setIconProfileClicked(false);

        Bundle bundle = new Bundle();

        bundle.putParcelable("post", post);


        ProfilFragment profile = new ProfilFragment();
        profile.setArguments(bundle);



    }

}
