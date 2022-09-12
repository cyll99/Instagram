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
import com.example.instagram.helper.TimeFormatter;
import com.example.instagram.models.Post;
import com.example.instagram.models.User;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    TextView tvUsername,tvCreatedAt,tvDescription;

    ImageView ivPhoto, ivProfile;


    TextView icon_heart,icon_save,icon_comment,icon_send;

    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra(Constants.DATA));


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

        String username = post.getUser().getUsername();
        String description = post.getDescription();
        String picture_url = post.getImage().getUrl();
        String profile_url = post.getUser().getParseFile(User.KEY_PROFILE).getUrl();
        String timestamp =TimeFormatter.getTimeStamp(post.getCreatedAt().toString());


        tvDescription.setText(description);
        tvUsername.setText(username);
        tvCreatedAt.setText(timestamp);

        Glide.with(DetailActivity.this).load(profile_url)
                .transform(new RoundedCorners(Constants.ROUNDED_PROFILE)).into(ivProfile);
        Glide.with(DetailActivity.this).load(picture_url)
                .transform(new RoundedCorners(Constants.ROUNDED_PICTURE)).into(ivPhoto);


    }
}