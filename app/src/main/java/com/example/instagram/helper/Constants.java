package com.example.instagram.helper;

import android.content.Context;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import com.example.instagram.models.Post;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class Constants {
    public static final int ROUNDED_PROFILE = 100;
    public static final int ROUNDED_PICTURE = 30;
    public static final ParseUser CURRENT_USER =  ParseUser.getCurrentUser();

    public static final String DATA = "data";
    public static final String TRANSITION = "detail";

    public static boolean iconProfileClicked = true;

    public static void setIconProfileClicked(boolean value){iconProfileClicked = value;}


    // methode to display empty or filled heart
    public static void display_heart(TextView blank_heart, TextView filled_heart, List<String> likers, ParseUser currentUser){
        if(likers.contains(currentUser.getObjectId())){
            blank_heart.setVisibility(View.INVISIBLE);
            filled_heart.setVisibility(View.VISIBLE);
        }else{
            blank_heart.setVisibility(View.VISIBLE);
            filled_heart.setVisibility(View.INVISIBLE);
        }
    }

    //ADD USER IN LIST LIKERS WHEN HE LIKES
    public static void UserLikes(TextView blank_heart, TextView filled_heart, ParseUser currentUser, Post post,String TAG, Context context,List<String> likers,TextView tvNumLikes){
        AddThisLiker(post,currentUser,TAG,context);
        blank_heart.setVisibility(View.INVISIBLE);
        filled_heart.setVisibility(View.VISIBLE);
        likers.add(currentUser.getObjectId());

        int numlikes = likers.size();
        numlikes++;
        post.setNumLikes(numlikes);
        tvNumLikes.setText(String.valueOf(numlikes));


    }

    //REMOVE USER IN LIST LIKERS WHEN HE DISLIKES

    public static void UserDislikes(TextView blank_heart, TextView filled_heart,  Post post,List<String> likers, ParseUser currentUser, TextView tvNumLikes){
        likers.remove(currentUser.getObjectId());
        post.removeItemListLikers(likers);
        blank_heart.setVisibility(View.VISIBLE);
        filled_heart.setVisibility(View.INVISIBLE);

        likers.remove(currentUser.getObjectId());

        int numlikes = likers.size();
        if (numlikes > 0)
            numlikes--;


        post.setNumLikes(numlikes);
        tvNumLikes.setText(String.valueOf(numlikes));

    }


//METHODE FOR ADDIG USER IN LIST LIKERS
    private static void AddThisLiker(Post post, ParseUser currentUser, String TAG, Context context) {

        post.setListLikers(currentUser);


        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(context, "Error while saving", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
