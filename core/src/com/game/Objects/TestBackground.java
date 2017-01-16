package com.game.Objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.game.Constants;

public class TestBackground extends Sprite implements Disposable{
    private static final Texture CLOUDS1 = new Texture(Gdx.files.internal("clouds1.png"));
    private static final Texture CLOUDS2 = new Texture(Gdx.files.internal("clouds2.png"));
    private static final Texture CLOUDS3 = new Texture(Gdx.files.internal("clouds3.png"));
    private static final Texture SMALL_STARS = new Texture(Gdx.files.internal("small_stars.png"));
    private static final Texture BIG_STARS = new Texture(Gdx.files.internal("big_stars.png"));
    private final float CLOIDS_VELOCITY = 0.2f;
    private final float SMALL_STARS_VELOCITY = 0.5f;
    private final float BIG_STARS_VELOCITY = 1.0f;
    public float offsetClouds = 0.0f;
    public float offsetSmallStars = 0.0f;
    public float offsetBigStars = 0.0f;


    public TestBackground() {
        super(CLOUDS1);
    }

    @Override
    public void draw(Batch batch) {
        batchDraw(batch, CLOUDS1, offsetClouds );
        batchDraw(batch, CLOUDS2, Constants.VIEWPORT_HEIGHT + offsetClouds );
        batchDraw(batch, CLOUDS3, 2 * Constants.VIEWPORT_HEIGHT + offsetClouds );
        batchDraw(batch, SMALL_STARS, offsetSmallStars );
        batchDraw(batch, SMALL_STARS, Constants.VIEWPORT_HEIGHT + offsetSmallStars );
        batchDraw(batch, BIG_STARS, offsetBigStars );
        batchDraw(batch, BIG_STARS, Constants.VIEWPORT_HEIGHT + offsetBigStars);

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
    public void batchDraw(Batch batch, Texture texture, float offsetY) {
        batch.draw(texture,
                0, offsetY,
                0, 0,
                Constants.WIDTH_MAX, Constants.HEIGHT_MAX,
                1, 1,
                0.0f,
                getRegionX(), getRegionY(),
                getRegionWidth(), getRegionHeight(),
                false, false);
    }

    @Override
    public void dispose() {
        CLOUDS1.dispose();
        CLOUDS2.dispose();
        CLOUDS3.dispose();
        SMALL_STARS.dispose();
        BIG_STARS.dispose();
    }
}
