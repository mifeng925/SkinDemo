package com.mifeng.skindemo.skin;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：蜜蜂
 * @Date：2019/5/29
 */
public class SkinFactory implements LayoutInflater.Factory2 {

    private static final String TAG = "SkinFactory";

    private List<SkinView> mSkinViews = new ArrayList<>();

    public static final String PACKAGE_NAME[] = {
            "android.widget.", "android.view.", "android.webkit"
    };

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;

        if (name.contains(".")) {
            view = onCreateView(name, context, attrs);
        } else {
            for (String s : PACKAGE_NAME) {
                view = onCreateView(s + name, context, attrs);
                if (view != null) {
                    break;
                }
            }
        }

        if (view != null) {
            parseView(view, context, attrs);
        }
        return view;
    }

    public void apply() {
        for (SkinView skinView : mSkinViews) {
            skinView.apply();
        }
    }

    /**
     * 解析View
     *
     * @param view    被解析的View
     * @param context 上下文环境
     * @param attrs   属性参数
     */
    private void parseView(View view, Context context, AttributeSet attrs) {
        List<SkinItem> skinItems = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String name = attrs.getAttributeName(i);
            String value = attrs.getAttributeValue(i);
            if (name.contains("background") || name.contains("textColor")) {
                int id = Integer.parseInt(value.substring(1));
                String entryName = view.getResources().getResourceEntryName(id);
                String typeName = view.getResources().getResourceTypeName(id);
                SkinItem item = new SkinItem(name, id, entryName, typeName);
                skinItems.add(item);
            }
        }

        if (skinItems.size() > 0) {
            SkinView skinView = new SkinView(view, skinItems);
            mSkinViews.add(skinView);
            skinView.apply();
        }

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        try {
            Class aClass = context.getClassLoader().loadClass(name);
            Constructor<? extends View> constructor = aClass
                    .getConstructor(new Class[]{Context.class, AttributeSet.class});
            view = constructor.newInstance(context, attrs);
            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    class SkinItem {

        private String name;

        private int resId;

        private String entryName;

        private String typeName;

        public SkinItem(String name, int resId, String entryName, String typeName) {
            this.name = name;
            this.resId = resId;
            this.entryName = entryName;
            this.typeName = typeName;
        }

        public String getName() {
            return name;
        }

        public int getResId() {
            return resId;
        }

        public String getEntryName() {
            return entryName;
        }

        public String getTypeName() {
            return typeName;
        }
    }

    class SkinView {

        private View mView;

        private List<SkinItem> mList;

        public SkinView(View view, List<SkinItem> list) {
            mView = view;
            mList = list;
        }

        public void apply() {
            for (SkinItem item :
                    mList) {
                if (item.getName().equals("background")) {
                    if (item.getTypeName().equals("color")) {
                        mView.setBackgroundColor(SkinManager.getInstance().getColor(item.getResId()));
                    } else if (item.getTypeName().equals("drawable") || item.getTypeName().equals("mipmap")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mView.setBackground(SkinManager.getInstance().getDrawable(item.getResId()));
                        } else {
                            mView.setBackgroundDrawable(SkinManager.getInstance().getDrawable(item.getResId()));
                        }
                    }
                } else if (item.getName().equals("textColor")) {
                    ColorStateList textColors = ((TextView) mView).getTextColors();
                    int color = SkinManager.getInstance().getColor(item.getResId());
                    ((TextView) mView).setTextColor(color);
                }
            }
        }
    }
}
