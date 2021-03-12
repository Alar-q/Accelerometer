package com.retro.androidgames.framework.hardware.impl;

import android.graphics.Bitmap;
import com.retro.androidgames.framework.hardware.interfaces.Graphics.PixmapFormat;
import com.retro.androidgames.framework.hardware.interfaces.Pixmap;

public class AndroidPixmap implements Pixmap {
    Bitmap bitmap;
    PixmapFormat format;
    public AndroidPixmap(Bitmap b, PixmapFormat f){
        this.bitmap = b;
        this.format = f;
    }
    @Override
    public int getWidth(){
        return bitmap.getWidth();
    }
    @Override
    public int getHeight(){
        return bitmap.getHeight();
    }
    @Override
    public PixmapFormat getFormat(){
        return format;
    }
    @Override
    public void dispose(){
        bitmap.recycle();
    }
}
