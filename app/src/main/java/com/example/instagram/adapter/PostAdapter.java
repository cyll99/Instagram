package com.example.instagram.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.example.instagram.activity.CommentActivity;
import com.example.instagram.activity.DetailActivity;
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
    private static List<String> likers;





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


        TextView  icon_save,icon_comment,icon_send;
        ImageButton icon_heart;

        RelativeLayout container, containerForProfile;
        int numlikes;

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
            tvNumLikes = itemView.findViewById(R.id.numLikes);

            container = itemView.findViewById(R.id.container);
            containerForProfile = itemView.findViewById(R.id.container_pro);

        }

        public void bind(Post post) throws JSONException {

            ParseUser currentUser = ParseUser.getCurrentUser();

            try {
                likers = Post.fromJsonArray(post.getLikers()); // list of likers
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i(TAG, String.valueOf(likers));

            String timeDifference = TimeFormatter.getTimeDifference(post.getCreatedAt().toString());
            String username = post.getUser().getUsername();
            String description = post.getDescription();
            String picture_url = post.getImage().getUrl();
            String profile_url = post.getUser().getParseFile(User.KEY_PROFILE).getUrl();






            // user clicks to this container to go to the profile of the user he clicks
            containerForProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToProfile(Parcels.wrap(post));
                }
            });

            // set color for heart
            try{
                if (likers.contains(currentUser.getObjectId())) {
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_heart);
                    icon_heart.setImageDrawable(drawable);
                }else {
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.heart_outline);
                    icon_heart.setImageDrawable(drawable);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }

            icon_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    numlikes = post.getNumLikes();
                    int index;

                    if (!likers.contains(currentUser.getObjectId())){
                        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_heart);
                        icon_heart.setImageDrawable(drawable);
                        numlikes++;
                        index = -1;

                    }else {
                        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.heart_outline);
                        icon_heart.setImageDrawable(drawable);
                        numlikes--;
                        index = likers.indexOf(currentUser.getObjectId());
                    }

                    tvNumLikes.setText(String.valueOf(numlikes) + " likes");
                    saveLike(post, numlikes, index, currentUser);
                }
            });
            // user clicks to comment a post
            icon_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i =  new Intent(context, CommentActivity.class);
                    i.putExtra("post", Parcels.wrap(post));
                    context.startActivity(i);

                }
            });

            // user clicks this container to go to the detail activity
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
                    .transform(new RoundedCorners(Constants.ROUNDED_PROFILE)).into(ivProfile); //display profile
            if(image != null){
                Glide.with(context).load(picture_url)
                        .transform(new RoundedCorners(Constants.ROUNDED_PICTURE)).into(ivPhoto); //display picture posted

            }


        }
    }
    // passing data to profile fragment
    private void goToProfile(Parcelable post) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        Constants.setIconProfileClicked(false);

        Bundle bundle = new Bundle();

        bundle.putParcelable("post", post);


        ProfilFragment profileFragment = ProfilFragment.newInstance("");
        profileFragment.setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.flContainer, profileFragment).commit();


    }
    // method to save a like
    private void saveLike(Post post, int like, int index, ParseUser currentUser) {
        post.setNumLikes(like);

        if (index == -1){
            post.setListLikers(currentUser);
            likers.add(currentUser.getObjectId());
        }else {
            likers.remove(index);
            post.removeItemListLikers(likers);
        }

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(context, "Error while saving", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, likers.toString());

            }
        });
    }

}
