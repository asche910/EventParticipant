package com.ep.eventparticipant.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ep.eventparticipant.R;
import com.ep.eventparticipant.object.EventBean;
import com.ep.eventparticipant.other.AsHttpUtils;
import com.ep.eventparticipant.other.Glide4Engine;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static com.ep.eventparticipant.activity.MainActivity.getRandomId;
import static com.ep.eventparticipant.fragment.FragmentHome.getPath;
import static com.ep.eventparticipant.fragment.FragmentHome.myCreatedList;

/**
 * @author As_
 * @email apknet@163.com
 * @github https://github.com/apknet
 * @since 2018/07/24
 */

public class EventNewActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBack, btnStart, btnEnd, btnImage, btnSave;
    TextView textTitle, textStart, textEnd;
    EditText editName, editWhere, editNote;

    private int index;
    private EventBean eventBean;

    private int randomId;
    private String imgUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_new);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        init();
    }

    void init() {
        randomId = getRandomId();
        btnBack = findViewById(R.id.event_new_btn_back);
        btnStart = findViewById(R.id.event_new_btn_start);
        btnEnd = findViewById(R.id.event_new_btn_end);
        btnImage = findViewById(R.id.event_new_btn_image);
        btnSave = findViewById(R.id.event_new_btn_save);
        textTitle = findViewById(R.id.event_new_text_title);
        textStart = findViewById(R.id.event_new_text_start);
        textEnd = findViewById(R.id.event_new_text_end);
        editName = findViewById(R.id.event_new_edit_name);
        editWhere = findViewById(R.id.event_new_edit_where);
        editNote = findViewById(R.id.event_new_edit_note);

        btnBack.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnEnd.setOnClickListener(this);
        btnImage.setOnClickListener(this);
        btnSave.setOnClickListener(this);


        index = getIntent().getIntExtra("Position", -1);

        if (index != -1) {
            eventBean = myCreatedList.get(index);
            randomId = eventBean.getId();
            imgUri = eventBean.getImgUri();

            textTitle.setText("修改活动");
            editName.setText(eventBean.getName());
            textStart.setText(eventBean.getStartTime());
            textEnd.setText(eventBean.getEndTime());
            editWhere.setText(eventBean.getWhere());
            editNote.setText(eventBean.getNote());

            try {
                btnImage.setBackground(Drawable.createFromStream(getContentResolver().openInputStream(Uri.parse(eventBean.getImgUri())), null));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            btnImage.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.event_new_btn_save:
                String name = editName.getText().toString();
                String startTime = textStart.getText().toString();
                String endTime = textEnd.getText().toString();
                String where = editWhere.getText().toString();
                String note = editNote.getText().toString();

                if("".equals(name)){
                    Toast.makeText(this, "请输入活动名称！", Toast.LENGTH_SHORT).show();
                    return;
                }else if("".equals(startTime)){
                    Toast.makeText(this, "请输入开始时间！", Toast.LENGTH_SHORT).show();
                    return;
                }else if("".equals(endTime)){
                    Toast.makeText(this, "请输入结束时间！", Toast.LENGTH_SHORT).show();
                    return;
                }else if("".equals(where)){
                    Toast.makeText(this, "请输入活动地点！", Toast.LENGTH_SHORT).show();
                    return;
                }else if("".equals(note)) {
                    Toast.makeText(this, "请补充活动内容！", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (index == -1) {
                            AsHttpUtils.createActivity(randomId, name, startTime + " - " + endTime, where, note, imgUri);
                        }else{
                            AsHttpUtils.updateActivity(randomId, name, startTime + " - " + endTime, where, note, imgUri);
                        }
                    }
                }).start();

                if (index != -1) {
                    Toast.makeText(this, "活动修改成功！", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "活动创建成功！", Toast.LENGTH_SHORT).show();
                }
                finish();

                break;
            case R.id.event_new_btn_image:
                Matisse.from(this)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(1)
//                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .forResult(2);

                break;
            case R.id.event_new_btn_end:
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                Calendar now = Calendar.getInstance();
                                int nowYear = now.get(Calendar.YEAR);
                                int nowMonth = now.get(Calendar.MONTH);
                                int nowDay = now.get(Calendar.DAY_OF_MONTH);

                                if ((nowYear < year) ||
                                        ((nowYear == year) && ((nowMonth < monthOfYear) ||
                                                (nowMonth == monthOfYear && nowDay <= dayOfMonth))
                                        )
                                        ) {
                                    String strDate = String.format("%d.%02d.%02d", year, monthOfYear + 1, dayOfMonth);
                                    textEnd.setText(strDate);
                                } else {
                                    Toast.makeText(getApplicationContext(), "请选择未来的日期！", Toast.LENGTH_SHORT).show();

                                }
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show(getFragmentManager(), "Show_");

                break;
            case R.id.event_new_btn_start:
                Calendar now_ = Calendar.getInstance();
                DatePickerDialog datePickerDialog_ = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                Calendar now = Calendar.getInstance();
                                int nowYear = now.get(Calendar.YEAR);
                                int nowMonth = now.get(Calendar.MONTH);
                                int nowDay = now.get(Calendar.DAY_OF_MONTH);

                                if ((nowYear < year) ||
                                        ((nowYear == year) && ((nowMonth < monthOfYear) ||
                                                (nowMonth == monthOfYear && nowDay <= dayOfMonth))
                                        )
                                        ) {
                                    String strDate = String.format("%d.%02d.%02d", year, monthOfYear + 1, dayOfMonth);
                                    textStart.setText(strDate);
                                } else {
                                    Toast.makeText(getApplicationContext(), "请选择未来的日期！", Toast.LENGTH_SHORT).show();

                                }


                            }
                        },
                        now_.get(Calendar.YEAR),
                        now_.get(Calendar.MONTH),
                        now_.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog_.show(getFragmentManager(), "Show");

                break;
            case R.id.event_new_btn_back:
                finish();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            List<Uri> list = Matisse.obtainResult(data);
            for (Uri uri : list) {
                System.out.println(uri);
            }
            imgUri = list.get(0).toString();
//            uri = list.get(0);

            File file = new File(getPath(getApplicationContext(), Uri.parse(imgUri)));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    imgUri =  AsHttpUtils.upImage(file);
                }
            }).start();

            Toast.makeText(this, "图片上传中......", Toast.LENGTH_SHORT).show();

            ContentResolver contentResolver = getContentResolver();
            try {
                InputStream inputStream = contentResolver.openInputStream(list.get(0));

                btnImage.setBackground(Drawable.createFromStream(inputStream , null));
                btnImage.setText("");

                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
