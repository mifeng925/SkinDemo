package com.mifeng.skindemo.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author：蜜蜂
 * @Date：2019/5/29
 */
public class SkinManager {

    private Resources mResources;

    private Context mContext;

    private String skinPackge;

    private static final SkinManager SKIN_MANAGER = new SkinManager();

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        return SKIN_MANAGER;
    }

    ;

    public void setContext(Context context) {
        mContext = context;
    }

    public void loadSkinApk(String path) {

        try {
            PackageManager manager = mContext.getPackageManager();
            skinPackge = manager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES).packageName;
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = assetManager.getClass().getMethod("addAssetPath", String.class);
            method.invoke(assetManager, path);

            mResources = new Resources(assetManager, mContext.getResources().getDisplayMetrics(), mContext.getResources().getConfiguration());


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public int getColor(int id){
        if (mResources == null) {
            return id;
        }
        String entryName = mContext.getResources().getResourceEntryName(id);
        String typeName = mContext.getResources().getResourceTypeName(id);
        int skinId = mResources.getIdentifier(entryName, typeName, skinPackge);
        if (skinId == 0) {
            return id;
        }
        return mResources.getColor(skinId);
    }

    public Drawable getDrawable(int id){
        if (mResources == null) {
            return ContextCompat.getDrawable(mContext, id);
        }
        String entryName = mContext.getResources().getResourceEntryName(id);
        String typeName = mContext.getResources().getResourceTypeName(id);
        int skinId = mResources.getIdentifier(entryName, typeName, skinPackge);
        if (skinId == 0) {
            return ContextCompat.getDrawable(mContext, id);
        }
        return mResources.getDrawable(skinId);
    }

}
