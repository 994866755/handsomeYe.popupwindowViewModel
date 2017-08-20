package com.example.kylinarm.popupwindowterminator;

import android.content.Context;
import android.view.View;

/**
 * Created by kylinARM on 2017/8/20.
 */

public class PopupWindowBuilder {

    public Context context;
    public int width;
    public int hight;
    public boolean isSetOut = true;
    public boolean isFocusable = true;
    public int animation;
    public float bgAlpha = 0.4f;
    public View anchor;
    public int xoff = 0;
    public int yoff = 0;
    public int gravityAs;
    public int gravityAt;

    public PopupWindowBuilder(Context context, int width, int hight){
        this.context = context;
        this.width = width;
        this.hight = hight;
    }

    public PopupWindowBuilder setSetOut(boolean setOut) {
        isSetOut = setOut;
        return this;
    }

    public PopupWindowBuilder setFocusable(boolean focusable) {
        isFocusable = focusable;
        return this;
    }

    public PopupWindowBuilder setAnimation(int animation) {
        this.animation = animation;
        return this;
    }

    public PopupWindowBuilder setBgAlpha(float bgAlpha) {
        this.bgAlpha = bgAlpha;
        return this;
    }

    public PopupWindowBuilder setAnchor(View anchor) {
        this.anchor = anchor;
        return this;
    }

    public PopupWindowBuilder setXoff(int xoff) {
        this.xoff = xoff;
        return this;
    }

    public PopupWindowBuilder setYoff(int yoff) {
        this.yoff = yoff;
        return this;
    }

    public PopupWindowBuilder setGravityAs(int gravityAs) {
        this.gravityAs = gravityAs;
        return this;
    }

    public PopupWindowBuilder setGravityAt(int gravityAt) {
        this.gravityAt = gravityAt;
        return this;
    }

    public PopupWindowBuilder builder(){
        return this;
    }


}
