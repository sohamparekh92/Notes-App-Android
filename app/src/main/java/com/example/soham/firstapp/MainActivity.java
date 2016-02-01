package com.example.soham.firstapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public void info(MenuItem item)
    {
        Intent i = new Intent(this, InfoActivity.class);
        startActivity(i);
    }

    public void viewList(View view)
    {
        Intent j = new Intent(this,ListActivity.class);
        startActivity(j);
    }


    public void onClickFunction(View view){

        String msg;
        EditText title = (EditText) findViewById(R.id.titleText);
        EditText content = (EditText) findViewById(R.id.contentText);
        msg = title.getText().toString();

        if(msg.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Must have a Title!", Toast.LENGTH_LONG).show();
        }
        else {
            try {

                Intent i = new Intent(getApplicationContext(),ListActivity.class);
                i.putExtra("title",msg);
                i.putExtra("content", content.getText().toString());
                i.putExtra("yes",true);
                content.getText().clear();
                title.getText().clear();
                startActivity(i);
            }
            catch (Throwable t){
                Toast.makeText(getApplicationContext(), "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

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
}
