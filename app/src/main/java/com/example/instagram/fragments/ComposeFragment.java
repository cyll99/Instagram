package com.example.instagram.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.instagram.models.Post;
import com.example.instagram.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class ComposeFragment extends Fragment {

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 69;
    public static final String TAG = "ComposerFragment";



    EditText et_description;
    Button btnTakePicture, btnSubmit;
    ImageView ivImage;

    private File photoFile;
    public String photoFileName = "photo.jpg";
    ProgressBar pb;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_description = view.findViewById(R.id.description);

        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnTakePicture = view.findViewById(R.id.btnCapture);
        ivImage = view.findViewById(R.id.image);
        pb = view.findViewById(R.id.pbLoading);



        btnSubmit.setVisibility(View.INVISIBLE);
        ivImage.setVisibility(View.INVISIBLE);
        et_description.setVisibility(View.INVISIBLE);

        // user takes picture
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });


        // submit posts
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Description = et_description.getText().toString();
                if (Description.isEmpty()) {
                    Toast.makeText(getContext(), "Description cannot be null", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (photoFile == null || ivImage.getDrawable() == null) {
                    Toast.makeText(getContext(), "There is no image", Toast.LENGTH_SHORT).show();
                    return;

                }
                // on some click or some loading we need to wait for...
                pb.setVisibility(ProgressBar.VISIBLE);

                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(Description, currentUser);
            }
        });
    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }


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
                    ivImage.setImageBitmap(takenImage);
                    btnTakePicture.setVisibility(View.INVISIBLE);

                    btnSubmit.setVisibility(View.VISIBLE);
                    ivImage.setVisibility(View.VISIBLE);
                    et_description.setVisibility(View.VISIBLE);

                } else { // Result was a failure
                    Toast.makeText(getContext(), "Error taking picture", Toast.LENGTH_SHORT).show();
                }
            }
        }

        // Returns the File for a photo stored on disk given the fileName
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

        private void savePost(String description, ParseUser currentUser) {
            Post post = new Post();
            post.setDescription(description);
            post.setImage(new ParseFile(photoFile));
            post.setUser(currentUser);
            post.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e != null){
                        Log.e(TAG, "Error while saving", e);

                        Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();

                    }
                    Log.i(TAG, "post was saved succesfully");
                    pb.setVisibility(ProgressBar.INVISIBLE);



                    btnTakePicture.setVisibility(View.VISIBLE);


                    et_description.setText("");
                    ivImage.setImageResource(0);

                }
            });
        }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);

    }
}