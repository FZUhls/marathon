package com.example.henry.marathon.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henry.marathon.R;

public class EditActivity extends AppCompatActivity {
    private String TAG = "EditActivity_edit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intentget = getIntent();
        String data = intentget.getStringExtra("extra_data");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        TextView headWord = findViewById(R.id.edit_head_text);
        headWord.setText(data);
        final EditText editText = findViewById(R.id.edit_query);
        TextView cancel = findViewById(R.id.cancel2);
        TextView make_sure = findViewById(R.id.make_sure2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data_return","nothing//but//wwww/aaaa/hhahaha");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        make_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edit = editText.getText().toString();
                Log.d(TAG, "onClick: "+edit);
                if (edit.equals("")){
                    Toast.makeText(EditActivity.this,"请输入内容",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("data_return",edit);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}
