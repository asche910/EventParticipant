package com.ep.eventparticipant.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ep.eventparticipant.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView camera;
private EditText biaoti,miaoshu,weizhi,gujia,jiezhishijian,lianxifangshi;
private TextView quxiao,fabu;
private Bitmap bitmap;
private int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        init();
        camera=(ImageView)findViewById(R.id.camera);
        camera.setOnClickListener(this);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (biaoti.getText()==null||miaoshu.getText()==null||a==0||weizhi.getText()==null||gujia.getText()==null||jiezhishijian.getText()==null||lianxifangshi.getText()==null){
                    Toast.makeText(PublishActivity.this,"请把相关信息填写完整",Toast.LENGTH_SHORT).show();
                }
              else {
                    RequestBody requestBody=new FormBody.Builder().add("title", String.valueOf(biaoti.getText())).add("describe",String.valueOf(miaoshu.getText()))
                            .add("location", String.valueOf(weizhi.getText())).add("evaluate", String.valueOf(gujia.getText())).add("stoptime", String.valueOf(jiezhishijian.getText()))
                            .add("contact", String.valueOf(lianxifangshi.getText())).build();
                    Request request=new Request.Builder().url("http://baidu.com").post(requestBody).build();
                    OkHttpClient client=new OkHttpClient();
                    try {
                        Response response=client.newCall(request).execute();
                        /*-----------------------*/
                   } catch (IOException e) {
                       e.printStackTrace();
                  }
                  Toast.makeText(PublishActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                    //finish();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera:
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);a=1;
                startActivityForResult(intent,0);break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0){
             bitmap=data.getParcelableExtra("data");
            camera.setPadding(5,5,5,5);
            camera.setImageBitmap(bitmap);
        }
    }
    private void init(){
        biaoti=(EditText)findViewById(R.id.biaoti);
        miaoshu=(EditText)findViewById(R.id.miaoshu);
        weizhi=(EditText)findViewById(R.id.weizhi);
        gujia=(EditText)findViewById(R.id.gujia);
        jiezhishijian=(EditText)findViewById(R.id.jiezhishijian);
        lianxifangshi=(EditText)findViewById(R.id.lianxifangshi);
        quxiao=(TextView)findViewById(R.id.quxiao_publish);
        fabu=(TextView)findViewById(R.id.fabu);
    }
}
