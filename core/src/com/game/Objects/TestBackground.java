package com.game.Objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;
import com.game.Constants;

public class TestBackground extends Sprite implements Disposable{
    private static final Texture TEXTURE1 = new Texture(Gdx.files.internal("background.png"));
    private static final Texture TEXTURE2 = new Texture(Gdx.files.internal("stars.png"));
    private final float OFFSET_VELOCITY1 = 2f;
    private final float OFFSET_VELOCITY2 = 0.5f;
    public float offsetY = 0.0f;
    public float offsetYY = 0.0f;


    public TestBackground() {
        super(TEXTURE1);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(TEXTURE1,
                0, offsetYY,
                0, 0,
                Constants.WIDTH_MAX, Constants.HEIGHT_MAX,
                //5, 5,
                1, 1,
                0.0f,
                getRegionX(), getRegionY(),
                getRegionWidth(), getRegionHeight(),
                false, false);
        batch.draw(TEXTURE1,
                0, Constants.VIEWPORT_HEIGHT + offsetYY,
                0, 0,
                Constants.WIDTH_MAX, Constants.HEIGHT_MAX,
                //5, 5,
                1, 1,
                0.0f,
                getRegionX(), getRegionY(),
                getRegionWidth(), getRegionHeight(),
                false, false);
        batch.draw(TEXTURE2,
                0, offsetY,
                0, 0,
                Constants.WIDTH_MAX, Constants.HEIGHT_MAX,
                //5, 5,
                1, 1,
                0.0f,
                getRegionX(), getRegionY(),
                getRegionWidth(), getRegionHeight(),
                false, false);
        batch.draw(TEXTURE2,
                0, Constants.VIEWPORT_HEIGHT + offsetY,
                0, 0,
                Constants.WIDTH_MAX, Constants.HEIGHT_MAX,
                //5, 5,
                1, 1,
                0.0f,
                getRegionX(), getRegionY(),
                getRegionWidth(), getRegionHeight(),
                false, false);
    }

    public void update(float delta) {
        offsetY -= OFFSET_VELOCITY1 * delta;
        offsetYY -= OFFSET_VELOCITY2 * delta;
        if(offsetY <= -Constants.VIEWPORT_HEIGHT) {
            offsetY = 0.0f;
        }
        if(offsetYY <= -Constants.VIEWPORT_HEIGHT) {
            offsetYY = 0.0f;
        }
    }

    @Override
    public void dispose() {
        TEXTURE1.dispose();
        TEXTURE2.dispose();
    }
}
