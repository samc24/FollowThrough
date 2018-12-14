package com.example.samch.followthrough;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Upload extends AsyncTask<String, Void, String> {
    public String path;
    public Upload(String path){
        super();
        this.path = path;
        Log.d("App:", "Upload: path = "+ this.path);
    }
    @Override
    protected String doInBackground(String... params) {
//        String currentPath = System.getProperty("user.dir");
//        Log.d("App","current Path: " + currentPath);
        String charset = "UTF-8";
        Log.d("App", "before file");
        String root = Environment.getExternalStorageDirectory().toString()+"/raw";
        Log.d("App", root);
        String filePath = "android.resource://com.example.samch.scrollvideo";//Environment.getExternalStorageDirectory().toString() + "/raw";
        String fileName = "test.mp4";
        File uploadFile = new File(path);//"ScrollVideo/app/src/main/res/raw",fileName);//getApplicationContext().getFilesDir(), "android.resource://com.example.samch.scrollvideo/" + R.raw.test);//getResources().getIdentifier("FILENAME_WITHOUT_EXTENSION","raw", getPackageName()));//"android.resource://com.example.samch.scrollvideo/test.mp4");
        Log.d("App", "file made "+uploadFile);
        String requestURL = "https://salty-hamlet-24223.herokuapp.com/poses/vs";//"http://localhost:8000/poses/vs";

        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);

//        multipart.addHeaderField("User-Agent", "CodeJava");
//        multipart.addHeaderField("Test-Header", "Header-Value");

//        multipart.addFormField("description", "Cool Pictures");
//        multipart.addFormField("keywords", "Java,upload,Spring");

            multipart.addFilePart("fileUpload", uploadFile);
            Log.d("App", "added file");
//        multipart.addFilePart("fileUpload", uploadFile2);

            List<String> response = multipart.finish();

            Log.d("App", "SERVER REPLIED:" + response);

            for (String line : response) {
                Log.d("App:", line);
            }
        } catch (IOException ex) {
            Log.e("App:", "" + (ex));
        }
        return null;
    }
}
