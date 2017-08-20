package com.example.kylinarm.popupwindowterminator;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import butterknife.ButterKnife;

/**
 * Created by kylinARM on 2017/8/19.
 *  AlertDialog在位置显示上是固定的，而PopupWindow则相对比较随意，能够在主屏幕上的任意位置显示。
 *  AlertDialog在显示的时候不会阻塞UI线程，而PopupWindow在显示的时候会阻塞UI线程。
 */

public abstract class PopupWindowBaseViewModel<T> implements PopupWindow.OnDismissListener{

    protected T data;

    protected PopupWindowBuilder builder;
    protected int width;
    protected int hight;
    protected boolean isSetOut;
    protected boolean isFocusable;
    protected int animation;
    protected float bgAlpha = 0.4f;
    protected View anchor;
    protected int xoff;
    protected int yoff;
    protected int gravityAs;
    protected int gravityAt;

    protected Context context;
    protected PopupWindow popupwindow;
    protected View contentView;
    protected WindowManager.LayoutParams lp;//activity的WindowManager.LayoutParams，可用来设置透明度
    protected Activity parentActivity;

    public PopupWindowBaseViewModel(PopupWindowBuilder builder){
        this.builder = builder;
        init();//初始化变量
        if (context instanceof Activity){
            parentActivity = (Activity) context;
        }
        createTemplate();
    }

    private void init(){
        this.context = builder.context;
        this.width = builder.width;
        this.hight = builder.hight;
        this.isSetOut = builder.isSetOut;
        this.isFocusable = builder.isFocusable;
        this.animation = builder.animation;
        this.bgAlpha = builder.bgAlpha;
        this.anchor = builder.anchor;
        this.xoff = builder.xoff;
        this.yoff = builder.yoff;
        this.gravityAs = builder.gravityAs;
        this.gravityAt = builder.gravityAt;
    }

    public final void createTemplate(){

        if (contentView == null){
            contentView = LayoutInflater.from(context).inflate(getLayoutId(),getRoot());
        }
        ButterKnife.inject(this,contentView);
        width = getPopWidth();
        hight = getPopHight();
        popupwindow = getPopupWindow();
        //在此判断一次popupwindow，如果为空则不进行接下来的操作
        if (popupwindow == null){
            return;
        }


        initPopView();//设置popwin的配置
        initView(); //初始化view

    }

    protected abstract PopupWindow getPopupWindow();

    protected abstract int getLayoutId();
    protected ViewGroup getRoot(){
        return null;
    }

    protected int getPopWidth(){
        return width;
    }

    protected int getPopHight(){
        return hight;
    }

    protected void initPopView(){
        setSoftInput();
        setOutsideTouchable();
        setFocusable();
        setAnimationStyle();
        setBackgroundDrawable(); //设置背景防止5.0以下的系统出BUG
        backgroundAlpha();
    }

    /**
     *  设置软键盘
     */
    protected void setSoftInput(){
        popupwindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    /**
     *  设置点击外部是否消失
     */
    private void setOutsideTouchable(){
        popupwindow.setOutsideTouchable(isSetOut);
    }

    /**
     *  设置焦点
     *   为false的话，你点击手机物理按钮回退出activity
     */
    private void setFocusable(){
        popupwindow.setFocusable(isFocusable);
    }

    /**
     *  设置动画
     */
    private void setAnimationStyle(){
        popupwindow.setAnimationStyle(animation);
    }

    /**
     * 如果不设置PopupWindow的背景，有些版本(5.0以下)就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
     */
    private void setBackgroundDrawable(){
        popupwindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
    }


    /**
     *  设置背景的透明度
     */
    protected void backgroundAlpha(){
        popupwindow.setOnDismissListener(this);
        if (parentActivity != null){
            lp = parentActivity.getWindow().getAttributes();
            lp.alpha = bgAlpha; //0.0-1.0
            parentActivity.getWindow().setAttributes(lp);
        }
    }

    protected abstract void initView();

    /**
     *  给popupwin数据
     *  创建popmodel时已传数据调用这个
     */
    public void setDataToView(){
        setDataToView(data);
    }

    /**
     *  创建popmodel时没传数据调用这个
     * @param data
     */
    protected abstract void setDataToView(T data);

    /**
     *  恢复背景颜色
     */
    public void recoveryAlpha(){
        if ((parentActivity != null) && (lp != null)){
            if (lp.alpha != 1f){
                lp.alpha = 1f;
                parentActivity.getWindow().setAttributes(lp);
            }
        }
    }

    @Override
    public void onDismiss() {
        recoveryAlpha();
    }

    /**
     *  展示  As情况的
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showAs(){
        popupwindow.showAsDropDown(anchor,xoff,yoff,gravityAs);
    }

    /**
     *  展示  At情况的
     */
    public void showAt(){
        popupwindow.showAtLocation(anchor,gravityAt,xoff,yoff);
    }



}
