package com.example.instagram.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.example.instagram.activity.PictureActivity;
import com.example.instagram.helper.Constants;
import com.example.instagram.models.Comment;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {


    private final Context context;
    private final List<Comment> comments;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }


    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comments, parent,false);
        return new CommentAdapter.ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{


        ImageView ivPhoto;
        TextView tvUsername, tvComment;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.photo);
            tvUsername = itemView.findViewById(R.id.username);
            tvComment = itemView.findViewById(R.id.comment);




        }

        public void bind(Comment comment) {

            String picture_url = comment.getUser().getParseFile(Comment.KEY_USER).getUrl();
            String username = comment.getUser().getUsername();
            String commentaire = comment.getComment();



            tvUsername.setText(username);
            tvComment.setText(commentaire);
            Glide.with(context).load(picture_url)
                    .transform(new RoundedCorners(Constants.ROUNDED_PROFILE)).into(ivPhoto);

        }
    }


}
