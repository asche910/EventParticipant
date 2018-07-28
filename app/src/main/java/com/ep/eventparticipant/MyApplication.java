package com.ep.eventparticipant;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * @author As_
 * @since 2018/07/24
 * @email apknet@163.com
 * @github https://github.com/apknet
 *
 */

public class MyApplication extends Application {
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);
    }

    public static Context getContext(){
        return context;
    }
}
