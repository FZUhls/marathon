package com.example.henry.marathon.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.henry.marathon.R;
import com.example.henry.marathon.adapter.Myadapter;
import com.example.henry.marathon.javabean.Obj;
import com.example.henry.marathon.javabean.Search;
import com.example.henry.marathon.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    private HttpUtil httpUtil = new HttpUtil();
    private Gson gson = new Gson();
    private String TAG = "HTTP_Response";
    private String LISTISNOTEMPTY = "LISTISNOTEMPTY";
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private View foundView;
    private View lostView;
    private ProgressBar progressBar;
    private List<View> viewList = new ArrayList<>();
    private String TAGG = "SEARCHHHHH";
    private RecyclerView recyclerView_found;
    private RecyclerView recyclerView_lost;
    private Myadapter myadapter_found;
    private Myadapter myadapter_lost;
    private MaterialSearchView searchView;
    private static boolean mBackKeyPressed = false;
    private List<Obj> objList_found = new ArrayList<>();
    private List<Obj> objList_lost = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        LayoutInflater inflater = getLayoutInflater();
        /*              给控件赋值
         *
         *
         *
         *
         */
        viewPager = findViewById(R.id.viewpaper);
        progressBar =findViewById(R.id.progress_bar);
        foundView = inflater.inflate(R.layout.foundpaper,null);
        lostView = inflater.inflate(R.layout.lostpaper,null);
        recyclerView_lost = lostView.findViewById(R.id.lost_recyclerView);
        recyclerView_found = foundView.findViewById(R.id.found_recyclerView);
        recyclerView_found.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_lost.setLayoutManager(new LinearLayoutManager(this));
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        viewList.add(foundView);
        viewList.add(lostView);
        /**********************************************************
         *
         *
         *
         */
        searchView.setVoiceSearch(false);
        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);
        bottomNavigationView.setSelectedItemId(R.id.found_item);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
        /*


        搜索事件
         */
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAGG, "onQueryTextSubmit: "+query);
                Search searchobj = new Search();
                searchobj.setKey("obj");
                searchobj.setValue(query);
                String json = gson.toJson(searchobj);
                httpUtil.HttpPost("http://47.107.171.219:5000/lost/search", json, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: ");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"Network error",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        });
                        String json = response.body().string();
                        int code = response.code();
                        if (code==200){
                            if (!objList_lost.isEmpty()){
                                Log.d(TAG+"OnCreate", "onResponse: lost列表不是空的");
                                objList_lost.clear();
                            }
                            Log.d(TAG, "lost_onResponse: "+code);
                            Log.d(TAG, "lost_onResponse: "+json);
                            List<Obj> objListtem = gson.fromJson(json,new TypeToken<List<Obj>>(){}.getType());
                            objList_lost.addAll(objListtem);
                            if (!objList_lost.isEmpty()){
                                //todo write recycerview here
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        myadapter_lost.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        else {
                            Log.d(TAG, "onResponse: "+"GetFailure");
                        }
                    }
                });
                httpUtil.HttpPost("http://47.107.171.219:5000/found/search", json, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: ");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"Network error",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        int code = response.code();
                        Log.d(TAG, "found_onResponse: "+code);
                        Log.d(TAG, "found_onResponse: "+json);
                        List<Obj> objListtem = gson.fromJson(json,new TypeToken<List<Obj>>(){}.getType());
                        if (code == 200){
                            if (!objList_found.isEmpty()){
                                objList_found.clear();
                                Log.d(TAG+"OnCreate", "onResponse: found列表不是空的");
                            }
                            else {
                                Log.d(TAG, "onResponse: "+"found");
                            }
                            objList_found.addAll(objListtem);
                            if (!objList_found.isEmpty()){
                                //todo write recycerview here
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        myadapter_found.notifyDataSetChanged();
                                    }
                                });
                            }
                        }

                        else {
                            Log.d(TAG, "onResponse: "+"GetFailure");
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                Log.d(TAGG, "onQueryTextChange: "+newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
                Log.d(TAGG, "onSearchViewShown: ");
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                Log.d(TAGG, "onSearchViewClosed: ");
            }
        });
        /******************************/

        navigationView.setNavigationItemSelectedListener(this);
        httpUtil.HttpGet("http://47.107.171.219:5000/found/all", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: shit");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                int code = response.code();
                if (code==200){
                    if (!objList_found.isEmpty()){
                        objList_found.clear();
                    }
                    Log.d(TAG, "onResponse: "+code);
                    Log.d(TAG, "onResponse: "+json);
                    List<Obj> objListtem = gson.fromJson(json,new TypeToken<List<Obj>>(){}.getType());
                    objList_found.addAll(objListtem);
                    if (!objList_found.isEmpty()){
                        //todo write recycerview here
                        Log.d(TAG+"OnCreate", "onResponse: 列表不是空的");
                        myadapter_found = new Myadapter(objList_found);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView_found.setAdapter(myadapter_found);
                            }
                        });
                    }
                }
                else {
                    Log.d(TAG, "onResponse: "+"GetFailure");
                }
            }
        });
        httpUtil.HttpGet("http://47.107.171.219:5000/lost/all", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: shit");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                int code = response.code();
                Log.d(TAG, "onResponse: "+code);
                Log.d(TAG, "onResponse: "+json);
                List<Obj> objListtem = gson.fromJson(json,new TypeToken<List<Obj>>(){}.getType());
                if (code == 200){
                    if (!objList_lost.isEmpty()){
                        objList_lost.clear();
                    }
                    objList_lost.addAll(objListtem);
                    if (!objList_lost.isEmpty()){
                        //todo write recycerview here
                        Log.d(TAG+"OnCreate", "onResponse: 列表不是空的");
                        myadapter_lost = new Myadapter(objList_lost);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView_lost.setAdapter(myadapter_lost);
                            }
                        });
                    }
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
            if(!mBackKeyPressed){
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mBackKeyPressed = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mBackKeyPressed = false;
                    }
                    }, 2000);
            } else{//退出程序
                 this.finish();
                 System.exit(0);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

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
            intent.putExtra("class","lost");
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this,form.class);
            intent.putExtra("class","found");
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else  if (id == R.id.found_item){
            viewPager.setCurrentItem(0,false);
        }else if (id ==R.id.lost_item){
            viewPager.setCurrentItem(1,false);
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
                    if (!objList_found.isEmpty()){
                        objList_found.clear();
                        Log.d(TAG, "onResponse: 列表不是空的");
                    }
                    List<Obj> objListTemp;
                    objListTemp = gson.fromJson(josn,new TypeToken<List<Obj>>(){}.getType());
                    objList_found.addAll(objListTemp);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myadapter_found.notifyDataSetChanged();
                        }
                    });
                }else {
                    Log.d(TAG, "onResponse: "+"GetFailure");
                }
            }

        });
        httpUtil.HttpGet("http://47.107.171.219:5000/lost/all", new Callback() {
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
                    if (!objList_lost.isEmpty()){
                        objList_lost.clear();
                        Log.d(TAG, "onResponse: 列表不是空的");
                    }
                    List<Obj> objListTemp;
                    objListTemp = gson.fromJson(josn,new TypeToken<List<Obj>>(){}.getType());
                    objList_lost.addAll(objListTemp);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myadapter_lost.notifyDataSetChanged();
                        }
                    });
                }else {
                    Log.d(TAG, "onResponse: "+"GetFailure");
                }
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i){
            case 0:
                bottomNavigationView.setSelectedItemId(R.id.found_item);
                break;
            case 1:
                bottomNavigationView.setSelectedItemId(R.id.lost_item);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
