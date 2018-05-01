package com.example.samch.followthrough;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class App extends Application {
    public static final String TAG = "ObjectBox";
    public static final boolean EXTERNAL_DIR = false;

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("App", "Before ObjectBox ");
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
        Log.d("App", "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
