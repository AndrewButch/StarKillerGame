package com.game;

import com.badlogic.gdx.Gdx;

public class Constants {
    public static final float VIEWPORT_WIDTH = 9.0f;
    public static final float VIEWPORT_HEIGHT = 16.0f;
    public static final float WIDTH_MAX = 12.0f;
    public static final float HEIGHT_MAX = 16.0f;
    public static final float VIEWPORT_GUI_WIDTH = 450f;
    public static final float VIEWPORT_GUI_HEIGHT = 800f;
    public static final float VIEWPORT_GUI_WIDTH_MAX = 600f;
    public static final float VIEWPORT_GUI_HEIGHT_MAX = 800f;

    public static float VIEWPORT_LEFT = (WIDTH_MAX - VIEWPORT_WIDTH) * 0.5f;
    public static float VIEWPORT_RIGHT = VIEWPORT_LEFT + VIEWPORT_WIDTH;

    public static float VIEWPORT_GUI_LEFT = (VIEWPORT_GUI_WIDTH_MAX - VIEWPORT_GUI_WIDTH) * 0.5f;
    public static float VIEWPORT_GUI_RIGHT = VIEWPORT_GUI_LEFT + VIEWPORT_GUI_WIDTH;

    public static final String TEXTURE_ATLAS = "OutputAtlas/atlas.atlas";

}
