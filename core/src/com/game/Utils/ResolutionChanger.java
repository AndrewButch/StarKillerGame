package com.game.Utils;


import com.badlogic.gdx.math.Vector2;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ResolutionChanger {
    private static int id = 1;
    ArrayList<Vector2> resolutions;
    Map<String, Vector2> res;


    public ResolutionChanger() {
        resolutions = new ArrayList<Vector2>();
        resolutions.add(new Vector2(1080, 1920));
        resolutions.add(new Vector2(1200, 1920));
        resolutions.add(new Vector2(768, 1024));
        resolutions.add(new Vector2(640, 960));
        resolutions.add(new Vector2(480, 800));
        res = new HashMap<String, Vector2>();
        res.put("16:9",new Vector2(1080, 1920));
        res.put("16:10",new Vector2(1200, 1920));
        res.put("4:3",new Vector2(768, 1024));
        res.put("3:2",new Vector2(640, 960));
        res.put("5:3",new Vector2(480, 800));
    }

    public Vector2 next() {
       Vector2 resol = new Vector2( resolutions.get(id));
        id = (id + 1) % resolutions.size();
        resol.scl(0.5f);
        return resol;
    }
}
