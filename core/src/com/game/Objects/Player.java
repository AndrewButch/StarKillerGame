package com.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Rectangle;
import com.game.Constants;
import com.game.Utils.Assets;

public class Player {
    public static final String TAG = Player.class.getSimpleName();
    public static final float VELOCITY = 5.0f;
    public static final float SHOOT_RELOAD = 0.4f;
    public static final float PLAYER_WIDTH = 1.0f;
    public static final float PLAYER_HEIGHT = 1.0f;

    private Rectangle boudnigBox;
    private Sprite sprite;
    private ShootPool shootPool;


    public Player(int posX, int posY) {
        sprite = new Sprite(Assets.instance.player);
        sprite.setSize(PLAYER_WIDTH, PLAYER_HEIGHT);
        sprite.setOrigin(sprite.getWidth() / 2.0f, sprite.getHeight() / 2.0f);
        sprite.setPosition(posX, posY);
//        Gdx.app.debug(TAG, "\n\tPosistion " + sprite.getX() + "/" + sprite.getY() +
//                "\n\tSize " + sprite.getWidth() + "/" + sprite.getHeight());
        boudnigBox = new Rectangle(
                sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        shootPool = new ShootPool(10, 20);
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

    public Rectangle getBoudnigBox() {
        return boudnigBox;
    }

    public float getX() {
        return this.sprite.getX();
    }
    public float getY() {
        return this.sprite.getY();
    }
}
