package com.example.soham.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SearchActivity extends AppCompatActivity {
    Intent j;
    HashMap<String,String> myMap;
    ListView listView;
    HashMap<Double, ArrayList<String> > finale;
    ArrayList<String> finalResults;
    ArrayList<String> finalResultsSorted;


    public void searchOnClick(View view)
    {
        listView = (ListView) findViewById(R.id.listView);
        finalResults = new ArrayList<String>();
        finalResultsSorted = new ArrayList<String>();
        finale = new HashMap<Double, ArrayList<String>>();
        EditText searchText = (EditText) findViewById(R.id.searchText);
        search(searchText.getText().toString());
    }

    public void search(String q)
    {

        double jaccard;
        int i;

        String query = q;
        String document;

        Iterator it = myMap.entrySet().iterator();

        while (it.hasNext())
        {
            jaccard = 0.0;
            double count = 0.0;

            HashMap.Entry<String,String> pair = (HashMap.Entry<String,String>)it.next();
            document = pair.getValue().toString();
            jaccard = calcJaccard(query, document);

            if(jaccard>0) {
                finalResults.add(pair.getKey().toString() +" "+ jaccard);
            }
        }
        finalResults = sort(finalResults);
        showView(finalResults);
    }

    public ArrayList<String> sort(ArrayList<String> finalR)
    {
        int i=0;
        for(i=0; i<finalR.size(); ++i)
        {
            String splitted[] = finalR.get(i).split(" ");
            Double val = Double.parseDouble(splitted[1]);
            finale.put(val, new ArrayList<String>());
        }

        for(i=0;i<finalR.size();++i)
        {
            String splited[] = finalR.get(i).split(" ");
            Double val = Double.parseDouble(splited[1]);
            finale.get(val).add(splited[0]);
        }

        Iterator it = finale.entrySet().iterator();

        while(it.hasNext())
        {
            HashMap.Entry<String,ArrayList<String> > pair = (HashMap.Entry<String,ArrayList<String> >)it.next();

            for(i=0;i<pair.getValue().size();++i)
            finalResultsSorted.add(pair.getValue().get(i).toString());
        }

        //finalR.add("Sorted");
        return finalResultsSorted;
    }


    public void showView(ArrayList<String> results)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, results);
        listView.setAdapter(adapter);
    }

    public double calcJaccard(String query, String document)
    {
        int i=0;
        int j=0;
        double intersect =0.0;
        double jaccard =0.0;
        String doc[] = document.split(" ");
        String queryArray[] = query.split(" ");

        for(i=0;i<queryArray.length; ++i) {
            for (j = 0; j < doc.length; ++j) {
                if (queryArray[i].equalsIgnoreCase(doc[j]))
                    intersect += 1;
            }
        }

        if(queryArray.length+doc.length>0) {
                jaccard = intersect/(queryArray.length+doc.length);
            }
        return jaccard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        j = getIntent();
        myMap = (HashMap<String,String>)getIntent().getSerializableExtra("myMap");

        final ListView touch = (ListView) findViewById(R.id.listView);
        touch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), myMap.get(touch.getItemAtPosition(position)).toString(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
