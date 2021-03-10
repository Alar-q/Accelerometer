package com.rat6.accelerometer;

import com.retro.androidgames.framework.Screen;
import com.retro.androidgames.framework.impl.AndroidGame;

public class MainActivity extends AndroidGame {

    @Override
    public Screen getStartScreen() {
        return new MainScreen(this);
    }
}