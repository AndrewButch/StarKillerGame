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
    private Sprite sprite;
    private Texture texture;

    public TestTriangle(int posX, int posY, float sizeX, float sizeY) {
        texture = new Texture(Gdx.files.internal("SpaceShip.png"));
        sprite = new Sprite(texture);
        sprite.setSize(sizeX, sizeY);
        sprite.setOrigin(sprite.getWidth() / 2.0f, sprite.getHeight() / 2.0f);
        sprite.setPosition(posX, posY);
        /*Gdx.app.debug(TAG, "\n\tPosistion " + sprite.getX() + "/" + sprite.getY() +
                "\n\tSize " + sprite.getWidth() + "/" + sprite.getHeight());*/
    }

    public void update(float axisY, float axisX){

        if (axisX != 0) {
            sprite.translateX(axisX);
            if (sprite.getX() > Constants.VIEWPORT_RIGHT - sprite.getWidth() * 0.5f){
                sprite.setX(Constants.VIEWPORT_RIGHT - sprite.getWidth() * 0.5f);
            }
            if (sprite.getX() <  Constants.VIEWPORT_LEFT + sprite.getWidth() * 0.5f) {
                sprite.setX(Constants.VIEWPORT_LEFT + sprite.getWidth() * 0.5f);
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
