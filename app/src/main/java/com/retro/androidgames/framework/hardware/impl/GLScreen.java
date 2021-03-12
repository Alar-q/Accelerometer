package com.retro.androidgames.framework.hardware.impl;

import com.retro.androidgames.framework.hardware.interfaces.Game;
import com.retro.androidgames.framework.hardware.interfaces.Screen;

public abstract class GLScreen extends Screen {
    protected final GLGraphics glGraphics;
    protected final GLGame glGame;

    public GLScreen(Game game) {
        super(game);
        glGame = (GLGame)game;
        glGraphics = ((GLGame)game).getGLGraphics();
    }
}
