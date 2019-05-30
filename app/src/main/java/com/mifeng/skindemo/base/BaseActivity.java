package com.mifeng.skindemo.base;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.mifeng.skindemo.R;
import com.mifeng.skindemo.skin.SkinFactory;
import com.mifeng.skindemo.skin.SkinManager;

/**
 * @Author：蜜蜂
 * @Date：2019/5/29
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    SkinFactory factory = new SkinFactory();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SkinManager.getInstance().setContext(this);
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), factory);
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("换肤");

    }

    public void changeSkin() {
        factory.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        factory.apply();
        Log.e(TAG, "onStart: ");
    }
}
