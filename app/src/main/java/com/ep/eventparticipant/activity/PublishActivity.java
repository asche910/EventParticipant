package com.ep.eventparticipant.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ep.eventparticipant.R;
import com.ep.eventparticipant.other.AsHttpUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import okhttp3.OkHttpClient;

import static com.ep.eventparticipant.activity.portrait.bitmapToFile;


public class PublishActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView camera;
    private EditText biaoti, miaoshu, weizhi, gujia, jiezhishijian, lianxifangshi;
    private TextView quxiao, fabu;
    private Bitmap bitmap;
    private int a = 0;
    public static OkHttpClient client;
    private File tempFile;
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private Uri uri = null;

    private String imgUrl;
    private boolean isUploaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        init();

        final String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        client = new OkHttpClient();
        camera = (ImageView) findViewById(R.id.camera);
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
                if (biaoti.getText().toString().equals("") || miaoshu.getText().toString().equals("") || weizhi.getText().toString().equals("")
                        || gujia.getText().toString().equals("") || jiezhishijian.getText().toString().equals("") || lianxifangshi.getText().toString().equals("")) {
                    Toast.makeText(PublishActivity.this, "请把相关信息填写完整", Toast.LENGTH_SHORT).show();
                } else {

                    // 检测图片是否上传成功！
                    if (!isUploaded){
                        Toast.makeText(PublishActivity.this, "图片上传中， 请稍后！", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int code = AsHttpUtils.createExchange(biaoti.getText().toString(), jiezhishijian.getText().toString(), weizhi.getText().toString(), miaoshu.getText().toString(), gujia.getText().toString(), imgUrl);

                            //Toast.makeText(PublishActivity.this,String.valueOf(code),Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "--------------------" + String.valueOf(code));
                        }
                    }).start();
                    // createExchange(biaoti.getText().toString(),jiezhishijian.getText().toString(),weizhi.getText().toString(),miaoshu.getText().toString(),gujia.getText().toString(),imageurl);
                    Toast.makeText(PublishActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
//                uri=FileProvider.getUriForFile(getApplicationContext(),"net.csdn.blog.ruancoder.fileprovider",createFile());
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.putExtra(MediaStore.ACTION_IMAGE_CAPTURE,uri);
                a = 1;
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (requestCode == 0) {
            Bundle bundle = data.getExtras();
            bitmap = data.getParcelableExtra("data");
            camera.setPadding(5, 5, 5, 5);
            //uri=Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,null,null));
            // Glide.with(this).load(uri).into(camera);
            camera.setImageBitmap(bitmap);

            Toast.makeText(this, "图片上传中， 请稍等！", Toast.LENGTH_SHORT).show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    imgUrl = AsHttpUtils.upImage(bitmapToFile(bitmap));
                    isUploaded = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PublishActivity.this, "图片上传完成！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();

        }
    }


    private void init() {
        biaoti = (EditText) findViewById(R.id.biaoti);
        miaoshu = (EditText) findViewById(R.id.miaoshu);
        weizhi = (EditText) findViewById(R.id.weizhi);
        gujia = (EditText) findViewById(R.id.gujia);
        jiezhishijian = (EditText) findViewById(R.id.jiezhishijian);
        lianxifangshi = (EditText) findViewById(R.id.lianxifangshi);
        quxiao = (TextView) findViewById(R.id.quxiao_publish);
        fabu = (TextView) findViewById(R.id.fabu);
    }

    /*----------------------------*/

    static public File createFile() {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + new Random().nextInt() + ".jpg");
        return file;
    }


}
