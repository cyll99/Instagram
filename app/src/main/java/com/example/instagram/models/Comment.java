package com.example.instagram.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@ParseClassName("Comment")
@Parcel(analyze = Comment.class)
public class Comment extends ParseObject {

    public Comment(){}

    public static final String KEY_COMMMENT="comment";
    public static final String KEY_USER="user";


    public String getComment(){
        return getString(KEY_COMMMENT);
    }
    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }





}
