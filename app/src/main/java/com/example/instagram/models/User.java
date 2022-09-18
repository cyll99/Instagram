package com.example.instagram.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcel;

@ParseClassName("_User")
@Parcel(analyze = User.class)

public class User extends ParseUser
{
    public User(){}

    public static final String KEY_PROFILE="profile";
    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";

    public String getUsername(){
        return getString(KEY_USERNAME);
    }

    public void setUsername(String username){
        put(KEY_USERNAME, username);
    }

    public String getPassword(){
        return getString(KEY_PASSWORD);
    }

    public void setPassword(String password){
        put(KEY_PASSWORD, password);
    }

    public ParseFile getProfile(){
        return getParseFile(KEY_PROFILE);
    }
    public void setProfile(ParseFile parseFile){
        put(KEY_PROFILE, parseFile);
    }



}
