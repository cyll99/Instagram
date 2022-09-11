package com.example.instagram.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.example.instagram.helper.Constants;
import com.parse.ParseFile;

public class DetailActivity extends AppCompatActivity {

    TextView tvUsername,tvCreatedAt,tvDescription;

    ImageView ivPhoto, ivProfile;


    TextView icon_heart,icon_save,icon_comment,icon_send;

    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvUsername = findViewById(R.id.username);
        tvCreatedAt = findViewById(R.id.createdAt);
        tvDescription = findViewById(R.id.description);
        ivPhoto = findViewById(R.id.photo);
        ivProfile = findViewById(R.id.profile);

        icon_heart = findViewById(R.id.heart);
        icon_save = findViewById(R.id.save);
        icon_comment = findViewById(R.id.comment);
        icon_send = findViewById(R.id.share);

        container = findViewById(R.id.container);

        String username = getIntent().getStringExtra(Constants.USERNAME);
        String description = getIntent().getStringExtra(Constants.DESCRIPTION);
        String date = getIntent().getStringExtra(Constants.DATE);
        String picture_url = getIntent().getStringExtra(Constants.PICTURE_URL);
        String profile_url = getIntent().getStringExtra(Constants.PROFILE_URL);


        tvDescription.setText(description);
        tvUsername.setText(username);
        tvCreatedAt.setText(date);

        Glide.with(DetailActivity.this).load(profile_url)
                .transform(new RoundedCorners(Constants.ROUNDED_PROFILE)).into(ivProfile);
        Glide.with(DetailActivity.this).load(picture_url)
                .transform(new RoundedCorners(Constants.ROUNDED_PICTURE)).into(ivPhoto);


    }
}