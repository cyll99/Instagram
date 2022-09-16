package com.example.instagram.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.example.instagram.helper.Constants;
import com.example.instagram.models.Comment;
import com.example.instagram.models.Post;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

public class CommentActivity extends AppCompatActivity {

    TextView tvUserName;
    ImageView profile;
    EditText etComment;
    Button btnComment;
    Post post = Parcels.unwrap(getIntent().getParcelableExtra(Constants.DATA));
    public static final String TAG = "CommentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        tvUserName = findViewById(R.id.username);
        profile = findViewById(R.id.profileImage);
        etComment = findViewById(R.id.etComment);
        btnComment = findViewById(R.id.btnComment);

        ParseUser currentUser = ParseUser.getCurrentUser();
        tvUserName.setText(currentUser.getUsername());

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = etComment.getText().toString();
                if (comment.isEmpty()){
                    Toast.makeText(CommentActivity.this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                }

                ParseUser currentUser = ParseUser.getCurrentUser();
                CreateComment(comment, currentUser);
            }
        });

    }
    // method for saving comments

    private void CreateComment(String description, ParseUser currentUser) {
        Comment comment = new Comment();
        comment.put("user", currentUser);
        comment.put("comment", description);
        post.setListComment(comment);

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(CommentActivity.this, "Error while saving", Toast.LENGTH_SHORT).show();
                    return;
                }

                etComment.setText("");
            }
        });
    }
}