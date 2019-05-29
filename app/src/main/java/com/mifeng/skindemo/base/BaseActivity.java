package com.mifeng.skindemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import com.mifeng.skindemo.skin.SkinFactory;
import com.mifeng.skindemo.skin.SkinManager;

/**
 * @Author：蜜蜂
 * @Date：2019/5/29
 */
public class BaseActivity extends Activity {

    SkinFactory factory = new SkinFactory();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().setContext(this);
        LayoutInflaterCompat.setFactory2(getLayoutInflater(),factory);

    }


    public void changeSkin(){
        factory.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        factory.apply();
    }
}
