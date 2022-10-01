package com.example.instagram.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.example.instagram.activity.PictureActivity;
import com.example.instagram.helper.Constants;
import com.example.instagram.models.Post;
import com.parse.Parse;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class ProfileAdapter extends BaseAdapter {

    public static final String  TAG= "ProfileAdapter";
    private final Context context;
    private final List<ParseFile> posts;
    LayoutInflater inflater;


    public ProfileAdapter(Context context, List<ParseFile> posts) {
        this.context = context;
        this.posts = posts;
    }






    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (view == null){
            view = inflater.inflate(R.layout.item_profile,null);
        }

        ImageView ivPostImage = view.findViewById(R.id.photo);
        ParseFile image = posts.get(i);
        Glide.with(context).load(image.getUrl()).transform(new RoundedCorners(30)).into(ivPostImage);

        return view;    }



}
