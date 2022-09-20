package com.example.instagram.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.R;
import com.example.instagram.activity.LoginActivity;
import com.example.instagram.adapter.ProfileAdapter;
import com.example.instagram.helper.Constants;
import com.example.instagram.models.Post;
import com.example.instagram.models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfilFragment extends Fragment {
    Button btnSignout;
    GridView gridView;
    ImageView ivProfile,edit_icon;
    TextView username;


    public static final String TAG = "ProfileFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 69;

    private File photoFile;
    public String photoFileName = "photo.jpg";

    ProfileAdapter profileAdapter;
    List<ParseFile> allPosts;
    ProgressBar pb;
    String profile_url;
    String UserName;
    ParseUser theUser;

    public static ProfilFragment newInstance(String title) {
        ProfilFragment frag = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.gridView);
        allPosts = new ArrayList<>();
        profileAdapter = new ProfileAdapter(getContext(), allPosts);

        gridView.setAdapter(profileAdapter); //set the adapter

//        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL); // set a grid layout for the pictures

//        rvPosts.setLayoutManager(gridLayoutManager);
        queryPost();

        btnSignout = view.findViewById(R.id.btnSignout);
        ivProfile = view.findViewById(R.id.profile);
        edit_icon = view.findViewById(R.id.edit);
        username = view.findViewById(R.id.username);

        pb = view.findViewById(R.id.pbLoading);

        Glide.with(getContext()).load(profile_url)
                .transform(new RoundedCorners(Constants.ROUNDED_PROFILE)).into(ivProfile); // display the profile picture

        username.setText(UserName);

        // user clicks to edit his profile
        edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });

        //user clicks to logout
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);

            }
        });
        profile_url = ParseUser.getCurrentUser().getParseFile(User.KEY_PROFILE).getUrl();
        UserName = ParseUser.getCurrentUser().getUsername();
        theUser = Constants.CURRENT_USER;

        // condition to display the user profile
        if (Constants.iconProfileClicked){ // current user profile displayed
            profile_url = ParseUser.getCurrentUser().getParseFile(User.KEY_PROFILE).getUrl();
            UserName = ParseUser.getCurrentUser().getUsername();
            theUser = Constants.CURRENT_USER;
        }else{//profile of the user chosen displayed
            Bundle bundle = getArguments();
            Post post = Parcels.unwrap(bundle.getParcelable("post"));

            profile_url = post.getUser().getParseFile(User.KEY_PROFILE).getUrl();
            UserName = post.getUser().getUsername();
            theUser = post.getUser();
            btnSignout.setVisibility(View.INVISIBLE);
            edit_icon.setVisibility(View.INVISIBLE);
        }
    }

    // methode for fetching posts
    public void queryPost() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.whereEqualTo(Post.KEY_USER, theUser);
        query.addDescendingOrder(Post.CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                List<ParseFile> images = new ArrayList<>();

                if(e != null){
                    Log.e(TAG,"issue findind post", e);
                    return;
                }
                for (Post post: posts){
                    Log.i(TAG, "Post: "+ post.getDescription() + " user: " +post.getUser().getUsername());
                    images.add(post.getImage());


                }
                allPosts.addAll(images);
                profileAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            if(resultCode== Activity.RESULT_OK){

                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                updateProfile();
                pb.setVisibility(ProgressBar.INVISIBLE);


            } else { // Result was a failure
                Toast.makeText(getContext(), "Error taking picture", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider1", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }


    }
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);


    }


    // methode to update profile
    public void updateProfile(){
        pb.setVisibility(ProgressBar.VISIBLE);

        User currentUser = (User) Constants.CURRENT_USER;
        // Set custom properties
        currentUser.setProfile(new ParseFile(photoFile));

        currentUser.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                // If successful add file to user and signUpInBackground
                if(null != e)
                    Log.e(TAG, "Error saving");


            }
        });
    }

}