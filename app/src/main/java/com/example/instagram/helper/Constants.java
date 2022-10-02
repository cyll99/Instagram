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




}
