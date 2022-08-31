package com.example.instagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("2ocs4YiHO36Hue88iDP4h9Znmorh8J6dGOOcackP")
                .clientKey("y2uHLCKXQMQPZrPH4e91u2bwaMNqscJl9LujxDZK")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
