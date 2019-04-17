package com.example.henry.marathon.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henry.marathon.R;
import com.example.henry.marathon.javabean.FoundObj;
import com.example.henry.marathon.javabean.HttpState;
import com.example.henry.marathon.utils.HttpUtil;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class form extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private int date = 0;
    private double latitude = 0;
    private double longitude = 0;
    private View name_write;
    private Gson gson = new Gson();
    private View describe_write;
    private View location_write;
    private View date_write;
    private View person_name_write;
    private View person_tel_write;
    private View location_describe;
    private TextView cancel;
    private TextView makesure;
    private TextView nameWriteIn;
    private TextView describeWriteIn;
    private TextView locationWriteIn;
    private TextView LocationDescribeIn;
    private TextView dateWriteIn;
    private TextView personNameWriteIn;
    private TextView personTelWriteIn;
    private String name = "";
    private String describe = "";
    private String personName = "";
    private String personTel = "";
    private String locationdescribe_string = "";
    private DatePickerDialog dpd;
    private String do_what;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Intent intent_dowhat = getIntent();
        do_what = intent_dowhat.getStringExtra("class");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        initView();
    }
    /** 时间选择器 **/
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month;
        String day;
        String dateTemp;
        if (monthOfYear<9){
            month = "0"+(++monthOfYear);
        }else {
            month = String.valueOf(monthOfYear);
        }
        if (dayOfMonth<10){
            day = "0" +dayOfMonth;
        }else {
            day = String.valueOf(dayOfMonth);
        }
        dateTemp = String.valueOf(year)+month+day;
        date = Integer.valueOf(dateTemp);
        Log.d("输出的时间", "onDateSet: "+date);
        dateWriteIn.setText(String.valueOf(date));
    }
    private void initView(){
        makesure = findViewById(R.id.make_sure);
        cancel = findViewById(R.id.cancel);
        name_write = findViewById(R.id.name_write);
        describe_write = findViewById(R.id.describe_write);
        location_write = findViewById(R.id.location_write);
        location_describe = findViewById(R.id.location_describe);
        date_write = findViewById(R.id.date_write);
        person_name_write = findViewById(R.id.person_name_write);
        LocationDescribeIn = findViewById(R.id.location_describeIn);
        person_tel_write = findViewById(R.id.person_tel_write);
        nameWriteIn = findViewById(R.id.name_writeIn);
        describeWriteIn = findViewById(R.id.describe_writeIn);
        locationWriteIn = findViewById(R.id.location_writeIn);
        dateWriteIn = findViewById(R.id.date_writeIn);
        personNameWriteIn = findViewById(R.id.person_name_writeIn);
        personTelWriteIn = findViewById(R.id.person_tel_writeIn);
        location_describe.setOnClickListener(this);
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
                Intent intent5 = new Intent(form.this,MapActivity.class);
                startActivityForResult(intent5,5);
                break;
            case R.id.date_write:
                Calendar now = Calendar.getInstance();
                if (dpd == null) {
                    dpd = DatePickerDialog.newInstance(
                            form.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                } else {
                    dpd.initialize(
                            form.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                }
                dpd.setMaxDate(now);
                dpd.setOkColor(Color.parseColor("#FFFFFF"));
                dpd.show(getFragmentManager(),"Datepickerdialog");
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
            case R.id.location_describe:
                Intent intent6 = new Intent(form.this,EditActivity.class);
                intent6.putExtra("extra_data","请输入地点描述：");
                startActivityForResult(intent6,6);
                break;
            case R.id.make_sure:
                if (name.equals("")){
                    Toast.makeText(form.this,"请输入物品名",Toast.LENGTH_SHORT).show();
                }else if (describe.equals("")){
                    Toast.makeText(form.this,"请输入物品描述",Toast.LENGTH_SHORT).show();
                }else if (locationWriteIn.getText().toString().equals("请输入")){
                    Toast.makeText(form.this,"请选择地点",Toast.LENGTH_SHORT).show();
                }else if (date == 0){
                    Toast.makeText(form.this,"请选择时间",Toast.LENGTH_SHORT).show();
                }else if (personName.equals("")){
                    Toast.makeText(form.this,"请输入姓名",Toast.LENGTH_SHORT).show();
                }else if (personTel.equals("")){
                    Toast.makeText(form.this,"请输入电话号码",Toast.LENGTH_SHORT).show();
                }else {
                    FoundObj foundObj = new FoundObj();
                    foundObj.setDate(BigInteger.valueOf(date));
                    foundObj.setName(name);
                    foundObj.setDescribe(describe);
                    foundObj.setLocationdesc(locationdescribe_string);
                    foundObj.setLatlngx(latitude);
                    foundObj.setLatlngy(longitude);
                    foundObj.setPerson_name(personName);
                    foundObj.setPerson_tel(personTel);
                    String json = gson.toJson(foundObj);
                    Log.d("POSTJSON", "onClick: "+json);
                    HttpUtil httpUtil = new HttpUtil();
                    String address = "http://47.107.171.219:5000/"+do_what+"/publish";
                    Log.d("ADDRESSIS_", "onClick: "+address);
                    httpUtil.HttpPost(address,json, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(form.this,"连接失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String data = response.body().string();
                            HttpState httpState = gson.fromJson(data,HttpState.class);
                            final String status = httpState.getStatus();
                            Log.d("HTTPSYATUS", "onResponse: "+response.code());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(form.this,status,Toast.LENGTH_SHORT).show();
                                }
                            });
                            finish();
                        }
                    });
                    finish();
                }
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null){
            String returnedData = data.getStringExtra("data_return");
            switch (requestCode){
                case 1:
                    nameWriteIn.setText(returnedData);
                    name = returnedData;
                    break;
                case 2:
                    describeWriteIn.setText(returnedData);
                    describe = returnedData;
                    break;
                case 3:
                    personNameWriteIn.setText(returnedData);
                    personName = returnedData;
                    break;
                case 4:
                    personTelWriteIn.setText(returnedData);
                    personTel = returnedData;
                    break;
                case 5:
                    latitude=data.getDoubleExtra("latitude",0.000000000000000);
                    longitude=data.getDoubleExtra("longitude",0.000000000000000);
                    locationWriteIn.setText("已选地点");
                    break;
                case 6:
                    LocationDescribeIn.setText(returnedData);
                    locationdescribe_string = returnedData;
                    break;
            }
        }
    }

}
