package com.game.Utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ArrayMap;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class ResolutionChanger {
    private static final String TAG = ResolutionChanger.class.getSimpleName();
    private static int id = 1;
    ArrayMap<String, Vector2> res;
    public static final ResolutionChanger instance = new ResolutionChanger();


    public ResolutionChanger() {
        res = new ArrayMap<String, Vector2>();
        res.put("16:9",new Vector2(1080, 1920));
        res.put("5:3",new Vector2(1152, 1920));
        res.put("16:10",new Vector2(1200, 1920));
        res.put("3:2",new Vector2(1280, 1920));
        res.put("4:3",new Vector2(1440, 1920));
    }

    public void changeResolution() {

       Vector2 resol = new Vector2( res.getValueAt(id));
        Gdx.app.debug(TAG, res.getKeyAt(id) + " (" + (int)resol.x + "/" + (int)resol.y + ")");
        id = (id + 1) % res.size;
        resol.scl(0.5f);
        Gdx.graphics.setWindowedMode((int)resol.x, (int)resol.y);

    }
}
