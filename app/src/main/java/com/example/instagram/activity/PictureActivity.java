package com.example.instagram.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.example.instagram.helper.Constants;
import com.example.instagram.models.Post;

import org.parceler.Parcels;

public class PictureActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Post post = Parcels.unwrap(getIntent().getParcelableExtra(Constants.DATA));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        imageView = findViewById(R.id.photo);

        Glide.with(PictureActivity.this).load(post.getImage().getUrl())
                .into(imageView);
    }
}