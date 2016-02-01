package com.example.soham.firstapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListActivity extends AppCompatActivity {
    Intent i;
    Intent j;
    static ArrayList<String> items = new ArrayList<String>();
    static HashMap myMap = new HashMap<String, String>();

    public void addItem(){

        if(items.contains(i.getStringExtra("title")))
            Toast.makeText(getApplicationContext(),"Item Already Exists",Toast.LENGTH_LONG).show();
        else {
            items.add(i.getStringExtra("title"));
            myMap.put(i.getStringExtra("title"), i.getStringExtra("content"));
        }
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        ListView myListView = (ListView)findViewById(R.id.myListView);
        myListView.setAdapter(listAdapter);


    }

    public void advancedSearch(View view)
    {
        j = new Intent(getApplicationContext(), SearchActivity.class);
        j.putExtra("myMap",myMap);
        startActivity(j);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        i = getIntent();
        if(i.getBooleanExtra("yes",false))
        this.addItem();

        final ListView myListView = (ListView)findViewById(R.id.myListView);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), myMap.get(myListView.getItemAtPosition(position)).toString(), Toast.LENGTH_LONG).show();
            }
        });

        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                myMap.remove(myListView.getItemAtPosition(position).toString());
                items.remove(myListView.getItemAtPosition(position).toString());
                listAdapter.notifyDataSetChanged();
               return false;
            }
        });



        SearchView search = (SearchView) findViewById(R.id.searchView);
        myListView.setAdapter(listAdapter);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapter.getFilter().filter(newText);
                return false;
            }
        });


    }

}
