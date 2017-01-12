package com.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public abstract class AbstractGameObject extends Sprite {

    public AbstractGameObject(Texture texture) {
        super(texture);
    }

    public AbstractGameObject(TextureRegion region){
        super(region);
    }

    public AbstractGameObject(TextureRegion region, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(region, srcX, srcY, srcWidth, srcHeight);
    }

    public abstract void update(float delta);
    public abstract void init();

}
