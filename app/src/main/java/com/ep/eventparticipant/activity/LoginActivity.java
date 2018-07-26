package com.ep.eventparticipant.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.ep.eventparticipant.R;

import static com.ep.eventparticipant.MyApplication.getContext;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        getPermissions();
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
        }

    }
}
