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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import io.objectbox.Box;

public class PlayerAdapter extends ArrayAdapter {
    public Object[] players;
    public Context mContext;
    private Box<Player> playersBox;
    public ImageButton favorite, unfavorite, expand;

    public PlayerAdapter(@NonNull Context context, int resource, @NonNull Object[] objects, Box<Player> playersBox) {
        super(context, resource, objects);
        //players = sortPlayers(objects);
        players = objects;
        mContext = context;
        this.playersBox = playersBox;
    }

    private Object[] sortPlayers(Object[] playerArray) {
        List playerList = Arrays.asList(playerArray);
        Collections.sort(playerList, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                Player player1 = (Player) o1;
                Player player2 = (Player) o2;
                if (player1.isFavorite() && !player2.isFavorite()) return -1;
                if (!player1.isFavorite() && player2.isFavorite()) return 1;

                //swap videos
                Log.d("App", "player 1: " + player1.localFileURI);
                Log.d("App", "player 2: " + player2.localFileURI);
                String temp = player1.localFileURI;
                player1.setLocalFileURI(player2.localFileURI);
                player2.setLocalFileURI(temp);
                Log.d("App", "player 1 now: " + player1.localFileURI);
                Log.d("App", "player 2 now: " + player2.localFileURI);
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

        final TextView shotInfo = listItem.findViewById(R.id.shotInfo);
        shotInfo.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras eu diam quis nulla consectetur gravida. Aliquam erat volutpat. Pellentesque sed nunc quis mi aliquam blandit. Mauris ut ornare erat, ut.");


        final Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        final Animation fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        expand = listItem.findViewById(R.id.expand);
        expand.setFocusable(false);
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shotInfo.getVisibility() == View.GONE) {
                    ((ImageButton) view).setImageResource(R.drawable.collapse);
                    ((ImageButton) view).setBackgroundColor(Color.TRANSPARENT);
                    expand.startAnimation(fadeOut); // TODO: doesn't work. Also, listview expansion animation needs to be added
                    shotInfo.setVisibility(View.VISIBLE);
                    shotInfo.startAnimation(fadeIn);
                } else {
                    ((ImageButton) view).setImageResource(R.drawable.expand);
                    ((ImageButton) view).setBackgroundColor(Color.TRANSPARENT);
                    shotInfo.startAnimation(fadeOut);
                    shotInfo.setVisibility(View.GONE);
                }
            }
        });

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
        if (player.isFavorite()) {
            favorite.setImageResource(R.drawable.favorite);
        } else {
            favorite.setImageResource(R.drawable.unfavorite);
            ;
        }
        return listItem;
    }

    @Override
    public Filter getFilter(){ // TODO: doesnt work
        final List<Object> playerList = new ArrayList<Object>(Arrays.asList(players));
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();

                if (constraint != null && constraint.toString().length() > 0) {
                    List<Object> founded = new ArrayList<Object>();
                    for(Object item: playerList){
                        if(item.toString().toLowerCase().contains(constraint)){
                            founded.add(item);
                        }
                    }

                    result.values = founded;
                    result.count = founded.size();
                }else {
                    result.values = playerList;
                    result.count = playerList.size();
                }
                return result;


            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //clear();
                for (Player item : (List<Player>) results.values) {
                    playerList.add(item);
                }
                notifyDataSetChanged();

            }

        };
    }

}
