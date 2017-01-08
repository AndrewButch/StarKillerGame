package com.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.Constants;

public class TestTriangle {
    public static final String TAG = TestTriangle.class.getSimpleName();
    public static final float VELOCITY = 5.0f;
    int width = 32;
    int height = 32;
    private Sprite sprite;
    private Pixmap pixmap;
    private Texture texture;
    private boolean moveUp = false;
    private int moveCount = 0;

    public TestTriangle(int posX, int posY, float sizeX, float sizeY) {
        pixmap = createPixmap(width, height);
        texture = new Texture(pixmap);

        sprite = new Sprite(texture);
        sprite.setSize(sizeX, sizeY);
        sprite.setOrigin(sprite.getWidth() / 2.0f, sprite.getHeight() / 2.0f);
        sprite.setPosition(posX, posY);
        Gdx.app.debug(TAG, "\n\tPosistion " + sprite.getX() + "/" + sprite.getY() +
                "\n\tSize " + sprite.getWidth() + "/" + sprite.getHeight());
    }

    private Pixmap createPixmap(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 1);
        //pixmap.fill();
        pixmap.fillTriangle(0, height, width , height, width/2, 0);
        return pixmap;
    }

    public void update(float axisY, float axisX){

        if (axisX != 0) {
            sprite.translateX(axisX);
            if (sprite.getX() > Constants.VIEWPORT_WIDTH - sprite.getWidth() * 0.5f){
                sprite.setX(Constants.VIEWPORT_WIDTH - sprite.getWidth() * 0.5f);
            }
            if (sprite.getX() <  sprite.getWidth() * 0.5f) {
                sprite.setX(sprite.getWidth() * 0.5f);
            }
        }
        if (axisY != 0) {
            sprite.translateY(axisY);
            if (sprite.getY() > Constants.VIEWPORT_HEIGHT - sprite.getHeight() * 0.5f){
                sprite.setY(Constants.VIEWPORT_HEIGHT  - sprite.getHeight() * 0.5f);
            }
            if (sprite.getY() < sprite.getWidth() * 0.5f ) {
                sprite.setY(sprite.getWidth() * 0.5f);
            }
        }

    }

    public void draw(SpriteBatch batch) {
        batch.draw(sprite.getTexture(),
                sprite.getX() - sprite.getWidth() * 0.5f, sprite.getY()- sprite.getHeight() * 0.5f,
                sprite.getOriginX(), sprite.getOriginY(),
                sprite.getWidth(), sprite.getHeight(),
                1, 1,
                0,
                sprite.getRegionX(), sprite.getRegionY(),
                sprite.getRegionWidth(), sprite.getRegionHeight(),
                false, false);
        //sprite.draw(batch);

    }


}
