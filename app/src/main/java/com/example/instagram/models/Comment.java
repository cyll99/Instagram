package com.example.instagram.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

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


    public static List<String> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<String> commentList = new ArrayList<String>();

        try {
            for (int i = 0; i < jsonArray.length(); i++){
                commentList.add(jsonArray.getJSONObject(i).getString("objectId"));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return commentList;
    }


}
