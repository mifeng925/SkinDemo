package com.mifeng.skindemo;

import android.app.Application;
import android.os.Environment;

import com.mifeng.skindemo.skin.SkinManager;

import java.io.File;

/**
 * @Author：蜜蜂
 * @Date：2019/5/30
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        SkinManager.getInstance().setContext(this);
//        File file = new File(Environment.getExternalStorageDirectory(), "skin.apk");
//        SkinManager.getInstance().loadSkinApk(file.getPath());
    }
}
