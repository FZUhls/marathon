package com.example.henry.marathon.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.example.henry.marathon.R;
import com.example.henry.marathon.adapter.Myadapter;
import com.example.henry.marathon.javabean.Obj;
import com.example.henry.marathon.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private HttpUtil httpUtil = new HttpUtil();
    private Gson gson = new Gson();
    private String TAG = "HTTP_Response";
    private RecyclerView recyclerView;
    private Myadapter myadapter;
    private List<Obj> objList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.all_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        httpUtil.HttpGet("http://47.107.171.219:5000/found/all", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: shit");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String josn = response.body().string();
                int code = response.code();
                Log.d(TAG, "onResponse: "+code);
                Log.d(TAG, "onResponse: "+josn);
                    List<Obj> objListtem = gson.fromJson(josn,new TypeToken<List<Obj>>(){}.getType());
                    objList.addAll(objListtem);
                    if (!objList.isEmpty()){
                        //todo write recycerview here
                        Log.d(TAG+"OnCreate", "onResponse: 列表不是空的");
                        myadapter = new Myadapter(objList);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(myadapter);
                            }
                        });
                    }
                else {
                    Log.d(TAG, "onResponse: "+"GetFailure");
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this,form.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this,MapActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        httpUtil.HttpGet("http://47.107.171.219:5000/found/all", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: shit");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String josn = response.body().string();
                int code = response.code();
                Log.d(TAG, "onResponse: "+code);
                Log.d(TAG, "onResponse: "+josn);
                if (code==200){
                    if (!objList.isEmpty()){
                        objList.clear();
                        Log.d(TAG, "onResponse: 列表不是空的");
                    }
                    List<Obj> objListTemp;
                    objListTemp = gson.fromJson(josn,new TypeToken<List<Obj>>(){}.getType());
                    objList.addAll(objListTemp);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myadapter.notifyDataSetChanged();
                        }
                    });
                }else {
                    Log.d(TAG, "onResponse: "+"GetFailure");
                }
            }
        });
    }
}
