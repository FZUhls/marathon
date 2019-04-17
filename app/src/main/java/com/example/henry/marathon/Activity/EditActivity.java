package com.example.henry.marathon.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.henry.marathon.R;
public class EditActivity extends AppCompatActivity {
    private String TAG = "EditActivity_edit";
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editText = findViewById(R.id.edit_query);
        Intent intentget = getIntent();
        String data = intentget.getStringExtra("extra_data");
        if (data.equals("请输入您的手机号码：")){
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        }else if (data.equals("请输入您的姓名：")){
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        TextView headWord = findViewById(R.id.edit_head_text);
        headWord.setText(data);
        TextView cancel = findViewById(R.id.cancel2);
        TextView make_sure = findViewById(R.id.make_sure2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作
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
                return false;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
