package com.example.instagram.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@ParseClassName("Post")
@Parcel(analyze = Post.class)

public class Post extends ParseObject {

    public Post(){}

    public static final String KEY_DESCRIPTION="desciption";
    public static final String KEY_IMAGE="image";
    public static final String KEY_USER="user";
    public static final String CREATED_AT="createdAt";
    public static final String KEY_PROFILE="profile";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }
    public String getDate(){return CREATED_AT;}

    public void setDescription(String description){
            put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }
    public ParseFile getProfile(){
        return getParseFile(KEY_PROFILE);
    }


    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }
    public void setProfile(ParseFile parseFile){
        put(KEY_PROFILE, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser parseUser){
        put(KEY_USER, parseUser);
    }


}
