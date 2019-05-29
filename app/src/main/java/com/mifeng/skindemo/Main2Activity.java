package com.mifeng.skindemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mifeng.skindemo.base.BaseActivity;
import com.mifeng.skindemo.skin.SkinManager;

import java.io.File;

/**
 * @author 蜜蜂
 */
public class Main2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


    }

    public void changeSkin(View v){

        File file = new File(Environment.getExternalStorageDirectory(), "skin.apk");
        SkinManager.getInstance().loadSkinApk(file.getPath());
        changeSkin();
    }


}
