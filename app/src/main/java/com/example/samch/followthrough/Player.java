package com.example.samch.followthrough;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Player { //representation of data.
    @Id
    long id;
    //@Index for playerName?
    String playerName;
    int playerProfile;
    String localFileURI; // TODO: check entity annotations and see if any apply here
    boolean favorite;
    String info;
    public Player(long id, String playerName, int playerProfile){
        this.id = id;
        this.playerName = playerName;
        this.playerProfile = playerProfile;
        //this.localFileURI = localFileURI;
    }

    public Player(){
        //empty
    }

    public Player(String playerName, int playerProfile, String info){
        this.playerName = playerName;
        this.playerProfile = playerProfile;
        this.info = info;
//        this.localFileURI = localFileURI;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerProfile() { return this.playerProfile; }

    public String getInfo(){ return this.info; }

    public String getLocalFileURI(){
        return this.localFileURI;
    }

    public void setLocalFileURI(String localFileURI) {
        this.localFileURI = localFileURI;
    }

    public boolean isFavorite(){
        return this.favorite;
    }

    public void setFavorite() {
        this.favorite = true;
    }
    public void unsetFavorite(){
        this.favorite = false;
    }
}
