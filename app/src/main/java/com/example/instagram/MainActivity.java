package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instagram.fragments.ComposeFragment;
import com.example.instagram.fragments.PostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 69;
    final FragmentManager fragmentManager = getSupportFragmentManager();


    EditText et_description;
    Button btnTakePicture,btnSubmit, btnLogout;
    ImageView ivImage, home, profile, create;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        home = findViewById(R.id.home);
        profile = findViewById(R.id.profil);
        create = findViewById(R.id.plus);

//        btnLogout = findViewById(R.id.btnLogout);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.home:
                        //TODO: Update fragment
                        fragment = new PostFragment();
                        break;
                    case R.id.profil:
                        fragment = new ProfileFragment();

                        break;
                    case R.id.plus:
                        fragment = new ComposeFragment();
                        break;
                    default:
                        //TODO: Update fragment

                        fragment = new ComposeFragment();

                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }

        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.home);







        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTakePicture.setVisibility(View.VISIBLE);

                // hide unused layout
                btnSubmit.setVisibility(View.INVISIBLE);
                ivImage.setVisibility(View.INVISIBLE);
                et_description.setVisibility(View.INVISIBLE);


            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_description.setVisibility(View.INVISIBLE);
                btnTakePicture.setVisibility(View.INVISIBLE);
                btnSubmit.setVisibility(View.INVISIBLE);
                ivImage.setVisibility(View.INVISIBLE);

                btnLogout.setVisibility(View.VISIBLE);

            }
        });

    }


}