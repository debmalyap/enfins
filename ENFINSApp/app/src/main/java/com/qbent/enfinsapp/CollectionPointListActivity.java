package com.qbent.enfinsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.qbent.enfinsapp.adapter.CollectionPointListRecyclerViewAdapter;
import com.qbent.enfinsapp.model.CollectionPoint;
import com.qbent.enfinsapp.restapi.ApiCallback;
import com.qbent.enfinsapp.restapi.ApiHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CollectionPointListActivity extends MainActivity
    implements ApiCallback {

    private RecyclerView recyclerView;
    private List<CollectionPoint> collectionPoints = new ArrayList<CollectionPoint>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_collection_point_list, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_collection_point);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Hello First Activity", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(getApplicationContext(), CollectionPointDetailActivity.class));
            }
        });


        populateCollectionPoints();
    }

    @Override
    public void onApiRequestStart() throws IOException {

    }

    @Override
    public void onApiRequestComplete(String key, String result) throws IOException {
        try {
            if (key.equals("collection_points")) {

                JSONArray jsonArray = new JSONArray(result);
                System.out.println(jsonArray.length());
                for(int i=0;i<jsonArray.length() - 1;i++)
                {
                    JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                    CollectionPoint collectionPoint = new CollectionPoint(
                            jsonObject.getString("name"),
                            jsonObject.getString("address"),
                            jsonObject.getString("mobileNo"),
                            jsonObject.getString("collectionDayName")
                    );
                    collectionPoints.add(collectionPoint);
                }


                recyclerView = findViewById(R.id.recyclerViewCollectionPoints);
                recyclerView.setLayoutManager(new LinearLayoutManager(CollectionPointListActivity.this));
                CollectionPointListRecyclerViewAdapter cpAdapter = new CollectionPointListRecyclerViewAdapter(collectionPoints);
                recyclerView.setAdapter(cpAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateCollectionPoints() {
        new ApiHandler.GetAsync(CollectionPointListActivity.this).execute("collection-points");
    }
}
