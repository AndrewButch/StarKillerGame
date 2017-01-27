package com.game;


public class Constants {
    // VIEWPORT
    public static final float VIEWPORT_WIDTH = 9.0f;
    public static final float VIEWPORT_HEIGHT = 16.0f;
    public static final float VIEWPORT_WIDTH_MAX = 12.0f;
    public static final float VIEWPORT_HEIGHT_MAX = 16.0f;
    // GUI
    public static float VIEWPORT_GUI_WIDTH = 450f;
    public static float VIEWPORT_GUI_HEIGHT = 800f;
    public static final float VIEWPORT_GUI_WIDTH_MAX = 600f;
    public static final float VIEWPORT_GUI_HEIGHT_MAX = 800f;
    // GAME LEFT/RIGHT BORDERD
    public static float VIEWPORT_LEFT = (VIEWPORT_WIDTH_MAX - VIEWPORT_WIDTH) * 0.5f;
    public static float VIEWPORT_RIGHT = VIEWPORT_LEFT + VIEWPORT_WIDTH;
    // GUI LEFT/RIGHT BORDERS
    public static float VIEWPORT_GUI_LEFT;
    public static float VIEWPORT_GUI_RIGHT;

    public static final String TEXTURE_ATLAS_GAME = "OutputGame/atlas.atlas";
    public static final String TEXTURE_ATLAS_MENU= "OutputMenu/atlas.atlas";

    public static void updateGUI(int width, int height) {
        // update GUI WIDTH
        VIEWPORT_GUI_WIDTH = (float)Math.floor((double)VIEWPORT_GUI_HEIGHT * width / height);
        // update GUI left/right
        VIEWPORT_GUI_LEFT = (VIEWPORT_GUI_WIDTH_MAX - VIEWPORT_GUI_WIDTH) * 0.5f;
        VIEWPORT_GUI_RIGHT = VIEWPORT_GUI_LEFT + VIEWPORT_GUI_WIDTH;
    }
}
