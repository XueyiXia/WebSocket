package com.websocket;

import android.app.Application;

/**
 * @author: xiaxueyi
 * @date: 2017-02-22
 * @time: 16:27
 * @说明: 基类
 */
public class AppLoader extends Application{

    private static AppLoader mInstance=null;

    public static AppLoader getInstance(){

        return mInstance;
    }

    @Override
    public void onCreate() {
        mInstance=this;
        super.onCreate();
    }
}

