package com.game.Objects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.game.Constants;
import com.game.Utils.Assets;

import java.util.ArrayList;
import java.util.HashMap;

public class TestBackground extends Sprite{

    private final static float CLOIDS_VELOCITY = 0.2f;
    private final static float SMALL_STARS_VELOCITY = 0.5f;
    private final static float BIG_STARS_VELOCITY = 1.0f;
    private float offsetClouds = 0.0f;
    private float offsetSmallStars = 0.0f;
    private float offsetBigStars = 0.0f;
    private HashMap<String, Texture> textures;


    public TestBackground() {
        super();
        textures = new HashMap<String, Texture>();
        textures.put("clouds1", Assets.instance.bg1.getTexture());
        textures.put("clouds2", Assets.instance.bg2.getTexture());
        textures.put("clouds3", Assets.instance.bg3.getTexture());
        textures.put("smallStars", Assets.instance.smallStars.getTexture());
        textures.put("bigStars",  Assets.instance.bigStars.getTexture());
    }

    @Override
    public void draw(Batch batch) {
        batchDraw(batch, Assets.instance.bg1, offsetClouds );
        batchDraw(batch, Assets.instance.bg2, Constants.VIEWPORT_HEIGHT + offsetClouds );
        batchDraw(batch, Assets.instance.bg3, 2 * Constants.VIEWPORT_HEIGHT + offsetClouds );
        batchDraw(batch, Assets.instance.smallStars, offsetSmallStars );
        batchDraw(batch, Assets.instance.smallStars, Constants.VIEWPORT_HEIGHT + offsetSmallStars );
        batchDraw(batch, Assets.instance.bigStars, offsetBigStars );
        batchDraw(batch, Assets.instance.bigStars, Constants.VIEWPORT_HEIGHT + offsetBigStars);

    }

    public void update(float delta) {
        offsetClouds -= CLOIDS_VELOCITY * delta;
        offsetSmallStars -= SMALL_STARS_VELOCITY * delta;
        offsetBigStars -= BIG_STARS_VELOCITY * delta;
        if(offsetClouds <= - 3 * Constants.VIEWPORT_HEIGHT) {
            offsetClouds = 0.0f;
        }
        if(offsetSmallStars <= -Constants.VIEWPORT_HEIGHT) {
            offsetSmallStars = 0.0f;
        }
        if(offsetBigStars <= -Constants.VIEWPORT_HEIGHT) {
            offsetBigStars = 0.0f;
        }
    }
    private void batchDraw(Batch batch, AtlasRegion region, float offsetY) {
        batch.draw(region.getTexture(),
                0, offsetY,
                0, 0,
                Constants.WIDTH_MAX, Constants.HEIGHT_MAX,
                1, 1,
                0.0f,
                region.getRegionX(), region.getRegionY(),
                region.getRegionWidth(), region.getRegionHeight(),
                false, false);
    }

}
