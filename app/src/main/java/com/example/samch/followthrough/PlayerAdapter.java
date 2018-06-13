package com.example.samch.followthrough;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.objectbox.Box;

public class PlayerAdapter extends ArrayAdapter {
    public Object[] players;
    public Context mContext;
    private Box<Player> playersBox;
    public ImageButton favorite, unfavorite;

    public PlayerAdapter(@NonNull Context context, int resource, @NonNull Object[] objects, Box<Player> playersBox) {
        super(context, resource, objects);
        //players = sortPlayers(objects);
        players = objects;
        mContext = context;
        this.playersBox = playersBox;
    }

    private Object[] sortPlayers(Object[] playerArray){
        List playerList = Arrays.asList(playerArray);
        Collections.sort(playerList, new Comparator<Object>() {
            public int compare(Object o1, Object o2){
                Player player1 = (Player) o1;
                Player player2 = (Player) o2;
                if(player1.isFavorite() && !player2.isFavorite()) return -1;
                if(!player1.isFavorite()&& player2.isFavorite()) return 1;

                //swap videos
                Log.d("App","player 1: "+ player1.localFileURI);
                Log.d("App","player 2: "+ player2.localFileURI);
                String temp = player1.localFileURI;
                player1.setLocalFileURI(player2.localFileURI);
                player2.setLocalFileURI(temp);
                Log.d("App","player 1 now: "+ player1.localFileURI);
                Log.d("App","player 2 now: "+ player2.localFileURI);
                //

                return player1.getPlayerName().compareTo(player2.getPlayerName());
            }
        });
        return playerList.toArray();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        final Player player = (Player) players[position];
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.player_item, parent, false);
        TextView name = listItem.findViewById(R.id.player_name);
        name.setText(player.getPlayerName());
        favorite = listItem.findViewById(R.id.favorite);
        favorite.setFocusable(false);
        //favorite.getLayoutParams().height = 25;
        //favorite.getLayoutParams().width = 25;
        //favorite.requestLayout();
        //unfavorite = listItem.findViewById(R.id.unfavorite);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isFavorite()) {

                    player.unsetFavorite();
                    //unfavorite.setVisibility(View.VISIBLE);
                    //favorite.setVisibility(View.GONE);
                    ((ImageButton) view).setImageResource(R.drawable.unfavorite);
                    ((ImageButton) view).setBackgroundColor(Color.TRANSPARENT);
                    //((ImageButton)view).getLayoutParams().height = 50;
                    //((ImageButton)view).getLayoutParams().width = 50;

                } else {
                    player.setFavorite();
                    ((ImageButton) view).setImageResource(R.drawable.favorite);
                    //unfavorite.setVisibility(View.GONE);
                    //favorite.setVisibility(View.VISIBLE);
                }
                playersBox.put(player);
            }
        });
        if (player.isFavorite()){
            favorite.setImageResource(R.drawable.favorite);
        }
        else{
            favorite.setImageResource(R.drawable.unfavorite);;
        }
        return listItem;
    }

}
