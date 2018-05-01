package com.example.samch.followthrough;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Player { //representation of data.
    @Id
    long id;
    String playerName;
    String localFileURI;
    boolean favorite;

    public Player(long id, String playerName, String localFileURI){
        this.id = id;
        this.playerName = playerName;
        this.localFileURI = localFileURI;
    }

    public Player(){
        //empty
    }

    public Player(String playerName, String localFileURI){
        this.playerName = playerName;
        this.localFileURI = localFileURI;
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
