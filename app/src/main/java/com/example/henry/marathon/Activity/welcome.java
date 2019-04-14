package com.example.henry.marathon.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.henry.marathon.R;

public class welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        try {
            Thread.sleep(2000);
            Intent intent = new Intent(welcome.this,MainActivity.class);
            startActivity(intent);
            finish();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
