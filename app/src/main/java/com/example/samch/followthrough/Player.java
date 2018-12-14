package com.example.samch.followthrough;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Player { //representation of data.
    @Id
    long id;
    //@Index for playerName?
    String playerName;
    String localFileURI; // TODO: check entity annotations and see if any apply here
    boolean favorite;

    public Player(long id, String playerName){
        this.id = id;
        this.playerName = playerName;
        //this.localFileURI = localFileURI;
    }

    public Player(){
        //empty
    }

    public Player(String playerName){
        this.playerName = playerName;
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
