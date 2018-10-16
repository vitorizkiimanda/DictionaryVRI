package com.example.vitorizkiimanda.dictionaryvri;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private EditText searchInputBar;
    private Button searchButton;
    private String resultString;

    RecyclerView recyclerView;
    DictionaryAdapter dictionaryAdapter;
    DictionaryHelper dictionaryHelper;
    List<DictionaryModel> dictionaryModelArrayList = new ArrayList<>();
    boolean english = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(this);
        DictionaryHelper dictionaryHelper = new DictionaryHelper(this);
        AppPreference appPreference = new AppPreference(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        Log.d("dictionaryHelper", String.valueOf(dictionaryHelper));
        dictionaryHelper.open();
        dictionaryModelArrayList = dictionaryHelper.getAllData(english);
        dictionaryHelper.close();
        dictionaryAdapter.setmData((ArrayList<DictionaryModel>) dictionaryModelArrayList);
        recyclerView.setAdapter(dictionaryAdapter);

//        binding
        searchInputBar = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);

//        trigger search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultString = String.valueOf(searchInputBar.getText());
                if(resultString.length()>0) {
                    Log.d("inputtan :", resultString);
                    getData(resultString);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dictionary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(this);
        DictionaryHelper dictionaryHelper = new DictionaryHelper(this);

        if (id == R.id.indonesia_english) {
            Log.d("dictionaryHelper", String.valueOf(dictionaryHelper));
            english=true;
            dictionaryHelper.open();
            dictionaryModelArrayList = dictionaryHelper.getAllData(english);
            dictionaryHelper.close();
            dictionaryAdapter.setmData((ArrayList<DictionaryModel>) dictionaryModelArrayList);
            recyclerView.setAdapter(dictionaryAdapter);
        } else if (id == R.id.english_indonesia) {
            english=false;
            dictionaryHelper.open();
            dictionaryModelArrayList = dictionaryHelper.getAllData(english);
            dictionaryHelper.close();
            dictionaryAdapter.setmData((ArrayList<DictionaryModel>) dictionaryModelArrayList);
            recyclerView.setAdapter(dictionaryAdapter);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getData(String input){

        DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(this);
        DictionaryHelper dictionaryHelper = new DictionaryHelper(this);
        try {
            dictionaryHelper.open();
            if (input.isEmpty()) {
                //nothing
            } else {
                dictionaryModelArrayList = dictionaryHelper.getByName(input, english);
            }
        } catch (android.database.SQLException e) {
            e.printStackTrace();
        } finally {
            dictionaryHelper.close();
        }
        dictionaryAdapter.setmData((ArrayList<DictionaryModel>) dictionaryModelArrayList);
        recyclerView.setAdapter(dictionaryAdapter);
    }
}
