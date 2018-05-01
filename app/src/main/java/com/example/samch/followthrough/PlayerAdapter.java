package com.example.samch.followthrough;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    public PlayerAdapter(@NonNull Context context, int resource, @NonNull Object[] objects, Box<Player> playersBox) {
        super(context, resource, objects);
        players = sortPlayers(objects);
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
        Button favorite = listItem.findViewById(R.id.favorite);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isFavorite()) {
                    player.unsetFavorite();
                    ((Button)view).setText("Favorite");

                } else {
                    player.setFavorite();
                    ((Button)view).setText("Unfavorite");
                }
                playersBox.put(player);
            }
        });
        if (player.isFavorite()){
            favorite.setText("Unfavorite");
        }
        else{
            favorite.setText("Favorite");
        }
        return listItem;
    }

}
