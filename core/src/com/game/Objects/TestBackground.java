package com.game.Objects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.Constants;

public class TestBackground extends Sprite{
    public TestBackground(Texture texture) {
        super(texture);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(getTexture(),
                0, 0,
                0, 0,
                Constants.WIDTH_MAX, Constants.HEIGHT_MAX,
                1, 1,
                0.0f,
                getRegionX(), getRegionY(),
                getRegionWidth(), getRegionHeight(),
                false, false);
    }
}
