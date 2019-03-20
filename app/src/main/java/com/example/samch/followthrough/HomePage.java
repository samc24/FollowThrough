package com.example.samch.followthrough;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.app.ListActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v7.widget.Toolbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class HomePage extends AppCompatActivity {
    private Box<Player> playersBox;
    private List<Player> players;
    public ListView lv;
    private ArrayList<String> playerNames = new ArrayList<>();
    public PlayerAdapter playerArrayAdapter, searchAdapter;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //getWindow().setEnterTransition(new Fade());
        setTheme(R.style.AppTheme);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true); //changing the list item's style to be highlighted because the list items are part of a checkable group
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        Toast.makeText(getApplicationContext(), "Selected:" + menuItem.toString(), Toast.LENGTH_LONG).show();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        // If your app switches out content based on which navigation menu item the user selects, you should consider using fragments in the main content area. Swapping fragments when you navigate from the navigation drawer allows for a seamless drawer animation, because the same base layout stays in place. To learn how to build your layout with fragments, see the Fragments documentation.

                        //TODO: add suitable items. Maybe have: all cardview categories for easy access; access to duos; etc

                        return true;
                    }
                });

//        actionbar.setTitle("Choose a Pro"); // or android:label in xml
        playersBox = ((App) getApplication()).getBoxStore().boxFor(Player.class);
        //playersBox = (MyObjectBox.builder().androidContext(HomePage.this).build()).boxFor(Player.class);
        players = playersBox.query().build().find();
        for (int i = 0; i < players.size(); i++) {
            playerNames.add(players.get(i).getPlayerName());
        }
        // sort pros array (mergesort?)
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#003da5")));

        handleIntent(getIntent());

//        ArrayAdapter adapter = (new ArrayAdapter<String>(this, R.layout.list_item,
        //              getResources().getStringArray(R.array.pros)));
        final ArrayAdapter adapter = (new ArrayAdapter<String>(this, R.layout.list_item,
                playerNames.toArray(new String[playerNames.size()])));
        playerArrayAdapter = (new PlayerAdapter(this, R.layout.player_item, players.toArray(new Player[players.size()]), playersBox));
        lv = findViewById(R.id.proList);
        lv.setAdapter(playerArrayAdapter);

        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {/*
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(4000);
                view.startAnimation(animation1);*/
//                Intent compare = new Intent(HomePage.this, UserVPro.class);
                Intent compare = new Intent();
                TextView textView = (TextView) view.findViewById(R.id.player_name);
                String proName = textView.getText().toString();
                long proId = playerArrayAdapter.players[i].getId();
                String proVid = "android.resource://com.example.samch.followthrough/";
                switch (proName) {
                    case "Stephen Curry":
                        proVid += R.raw.stephform;
                        break;
                    case "Damian Lillard":
                        proVid += R.raw.dameform;
                        break;
                    case "Lebron James":
                        proVid += R.raw.lebronform;
                        break;
                    default:
                        proVid += R.raw.lebronform;
                }
                compare.putExtra("ProId", proId);
                compare.putExtra("ProVid", proVid);
                compare.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                setResult(2,compare);
                finish();
//                startActivity(compare);
            }
        });


    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            //use the query to search your data somehow
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        if (null != searchManager) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Enter name of Pro");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null) {
                    //playerArrayAdapter.getFilter().filter(query);
                    List<Player> searched = playersBox.query().startsWith(Player_.playerName, query).build().find();
                    searchAdapter = (new PlayerAdapter(getApplicationContext(), R.layout.player_item, searched.toArray(new Player[searched.size()]), playersBox));
                    lv.setAdapter(searchAdapter);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null) {
                    //playerArrayAdapter.getFilter().filter(newText);
                    List<Player> searched = playersBox.query().startsWith(Player_.playerName, newText).build().find();
                    searchAdapter = (new PlayerAdapter(getApplicationContext(), R.layout.player_item, searched.toArray(new Player[searched.size()]), playersBox));
                    lv.setAdapter(searchAdapter);
                }
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                lv.setAdapter(playerArrayAdapter);

                return true;
            }
        });
        MenuItem mSearchItem = menu.findItem(R.id.search);

        mSearchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                lv.setAdapter(playerArrayAdapter);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    /*public boolean onOptionsItemSelected(MenuItem menuItem){

//        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}