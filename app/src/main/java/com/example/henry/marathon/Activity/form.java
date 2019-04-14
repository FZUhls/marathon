package com.example.henry.marathon.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.henry.marathon.R;

public class form extends AppCompatActivity implements View.OnClickListener {

    View name_write;
    View describe_write;
    View location_write;
    View date_write;
    View person_name_write;
    View person_tel_write;
    TextView cancel;
    TextView makesure;
    TextView nameWriteIn;
    TextView describeWriteIn;
    TextView locationWriteIn;
    TextView dateWriteIn;
    TextView personNameWriteIn;
    TextView personTelWriteIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        initView();
    }
    private void initView(){
        makesure = findViewById(R.id.make_sure);
        cancel = findViewById(R.id.cancel);
        name_write = findViewById(R.id.name_write);
        describe_write = findViewById(R.id.describe_write);
        location_write = findViewById(R.id.location_write);
        date_write = findViewById(R.id.date_write);
        person_name_write = findViewById(R.id.person_name_write);
        person_tel_write = findViewById(R.id.person_tel_write);
        nameWriteIn = findViewById(R.id.name_writeIn);
        describeWriteIn = findViewById(R.id.describe_writeIn);
        locationWriteIn = findViewById(R.id.location_writeIn);
        dateWriteIn = findViewById(R.id.date_writeIn);
        personNameWriteIn = findViewById(R.id.person_name_writeIn);
        personTelWriteIn = findViewById(R.id.person_tel_writeIn);
        makesure.setOnClickListener(this);
        cancel.setOnClickListener(this);
        describe_write.setOnClickListener(this);
        name_write.setOnClickListener(this);
        location_write.setOnClickListener(this);
        date_write.setOnClickListener(this);
        person_name_write.setOnClickListener(this);
        person_tel_write.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.name_write:
                Intent intent1 = new Intent(form.this,EditActivity.class);
                intent1.putExtra("extra_data","请输入物品的名字：");
                startActivityForResult(intent1,1);
                break;
            case R.id.describe_write:
                Intent intent2 = new Intent(form.this,EditActivity.class);
                intent2.putExtra("extra_data","请输入物品描述：");
                startActivityForResult(intent2,2);
                break;
            case R.id.location_write:
                break;
            case R.id.date_write:
                break;
            case R.id.person_name_write:
                Intent intent3 = new Intent(form.this,EditActivity.class);
                intent3.putExtra("extra_data","请输入您的姓名：");
                startActivityForResult(intent3,3);
                break;
            case R.id.person_tel_write:
                Intent intent4 = new Intent(form.this,EditActivity.class);
                intent4.putExtra("extra_data","请输入您的手机号码：");
                startActivityForResult(intent4,4);
                break;
            case R.id.make_sure:
                Intent intent5 = new Intent(form.this,MainActivity.class);
                startActivity(intent5);
                finish();
            case R.id.cancel:
                finish();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String returnedData = data.getStringExtra("data_return");
        if (!returnedData.equals("nothing//but//wwww/aaaa/hhahaha")){
            switch (requestCode){
                case 1:
                    nameWriteIn.setText(returnedData);
                    break;
                case 2:
                    describeWriteIn.setText(returnedData);
                    break;
                case 3:
                    personNameWriteIn.setText(returnedData);
                    break;
                case 4:
                    personTelWriteIn.setText(returnedData);
            }
        }
    }
}
