package com.example.instagram.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.parse.ParseFile;

public class DetailActivity extends AppCompatActivity {

    TextView tvUsername,tvCreatedAt,tvDescription;

    ImageView ivPhoto;


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

        icon_heart = findViewById(R.id.heart);
        icon_save = findViewById(R.id.save);
        icon_comment = findViewById(R.id.comment);
        icon_send = findViewById(R.id.share);

        container = findViewById(R.id.container);

        String username = getIntent().getStringExtra("username");
        String description = getIntent().getStringExtra("description");
        String date = getIntent().getStringExtra("date");
        String picture_url = getIntent().getStringExtra("picture");


        tvDescription.setText(description);
        tvUsername.setText(username);
        tvCreatedAt.setText(date);

        Glide.with(DetailActivity.this).load(picture_url)
                .transform(new RoundedCorners(30)).into(ivPhoto);


    }
}