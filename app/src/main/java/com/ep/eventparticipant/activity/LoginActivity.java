package com.ep.eventparticipant.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ep.eventparticipant.other.AsHttpUtils;
import com.ep.eventparticipant.R;

public class LoginActivity extends AppCompatActivity {
    private TextView tv_main_title;
    private TextView tv_back,tv_register,tv_find_psw;
    private Button btn_login;
    private String userName,psw,spPsw;
    private EditText et_user_name,et_psw;

    private boolean isSuccess;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(intent);

        getPermissions();
        init();
    }

    private void init() {
        TextView textView=(TextView)findViewById(R.id.tourist);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "暂时关闭游客模式！", Toast.LENGTH_SHORT).show();

               /* Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);*/
            }
        });
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_back=findViewById(R.id.tv_back);
        tv_register=findViewById(R.id.tv_register);
        tv_find_psw=findViewById(R.id.tv_find_psw);
        btn_login=findViewById(R.id.btn_login);
        et_user_name=findViewById(R.id.et_user_name);
        et_psw=findViewById(R.id.et_psw);

        et_user_name.setText("root");
        et_psw.setText("password");

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "该功能暂未开放！", Toast.LENGTH_SHORT).show();
            }
        });

        //---------------------------> Login ------->
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=et_user_name.getText().toString().trim();
                psw=et_psw.getText().toString().trim();


                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(AsHttpUtils.login(userName, psw) == 0){
                            isSuccess = true;
                        }
                    }
                });
                thread.start();
                try {
                     thread.join(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


//                spPsw=readPsw(userName);
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(isSuccess){
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    saveLoginStatus(true, userName);
                    Intent data=new Intent();
                    data.putExtra("isLogin",true);
                    setResult(RESULT_OK,data);
                    LoginActivity.this.finish();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    return;
                }else if((spPsw!=null&&!TextUtils.isEmpty(spPsw))){
                    Toast.makeText(LoginActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String readPsw(String userName){
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        return sp.getString(userName , "");
    }

    private void saveLoginStatus(boolean status,String userName){

       SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin", status);
        editor.putString("loginUserName", userName);

        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                et_user_name.setText(userName);
                et_user_name.setSelection(userName.length());
            }
        }
    }

    private  void getPermissions(){
        boolean isAllGranted = checkAllPermission();
        if(!isAllGranted){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            }, 0);

        }
    }

     boolean checkAllPermission(){
        boolean b1 = (PackageManager.PERMISSION_GRANTED ==ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET));
        boolean b2 = (PackageManager.PERMISSION_GRANTED ==ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE));
        boolean b3 = (PackageManager.PERMISSION_GRANTED ==ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE));
        boolean b4 = (PackageManager.PERMISSION_GRANTED ==ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA));

        return b1 && b2 && b3 && b4;
     }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                for(int result: grantResults){
                    Log.e("", "onRequestPermissionsResult: " + result );
                    if(result != 0){
                        Toast.makeText(this, "获取权限失败， 软件可能无法正常工作！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }
}
