package com.retro.androidgames.framework.hardware.interfaces;

import com.retro.androidgames.framework.hardware.interfaces.Graphics.PixmapFormat;

public interface Pixmap {
    public int getWidth();
    public int getHeight();
    public PixmapFormat getFormat();
    public void dispose();
}

