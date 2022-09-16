package com.example.instagram.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.example.instagram.activity.DetailActivity;
import com.example.instagram.activity.PictureActivity;
import com.example.instagram.helper.Constants;
import com.example.instagram.models.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {


    private final Context context;
    private final List<Post> posts;

    public ProfileAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }


    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile, parent,false);
        return new ProfileAdapter.ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{


        ImageView ivPhoto;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.photo);




        }

        public void bind(Post post) {

            String picture_url = post.getImage().getUrl();


            ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2 Navigate on new activity on tap

                    Intent i =  new Intent(context, PictureActivity.class);
                    i.putExtra(Constants.DATA, Parcels.wrap(post));




                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) context, ivPhoto, Constants.TRANSITION);

                    context.startActivity(i, options.toBundle());

                }
            });


            ParseFile image = post.getImage();


            if(image != null){
                Glide.with(context).load(picture_url)
                        .transform(new RoundedCorners(Constants.ROUNDED_PICTURE)).into(ivPhoto);

            }
        }
    }


}
