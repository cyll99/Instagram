package com.example.instagram.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.instagram.fragments.ProfilFragment;
import com.example.instagram.R;
import com.example.instagram.fragments.ComposeFragment;
import com.example.instagram.fragments.PostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 69;
    final FragmentManager fragmentManager = getSupportFragmentManager();


    EditText et_description;
    Button btnTakePicture,btnSubmit, btnLogout;
    ImageView ivImage, profile, create;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




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
                        fragment = new ProfilFragment();

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









//        create.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                btnTakePicture.setVisibility(View.VISIBLE);
//
//                // hide unused layout
//                btnSubmit.setVisibility(View.INVISIBLE);
//                ivImage.setVisibility(View.INVISIBLE);
//                et_description.setVisibility(View.INVISIBLE);
//
//
//            }
//        });

//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                et_description.setVisibility(View.INVISIBLE);
//                btnTakePicture.setVisibility(View.INVISIBLE);
//                btnSubmit.setVisibility(View.INVISIBLE);
//                ivImage.setVisibility(View.INVISIBLE);
//
//                btnLogout.setVisibility(View.VISIBLE);
//
//            }
//        });

    }


}