package com.game.Objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

public class Shoot extends AbstractGameObject implements Disposable, Pool.Poolable {
    private static final Texture TEXTURE = new Texture(Gdx.files.internal("star.png"));
    public static float RADIUS = 0.25f;
    public static final float VELOCITY = 7.0f;
    public static int id = 0;
    private int currentID;

    private Circle boundingCircle;

    public Shoot(){
        super(TEXTURE);
        this.setSize(RADIUS * 2, RADIUS * 2);
        this.setOrigin(getWidth() * 0.5f, getHeight() * 0.5f);
        currentID = id++;
        this.setColor(Color.GREEN);
        this.boundingCircle = new Circle(0, 0, RADIUS);


    }

    @Override
    public void update(float delta) {
        translateY(VELOCITY * delta);
        boundingCircle.setY(getY());
       // Gdx.app.debug("BOUNDING Circle", this.getBoundingCircle() + " ");
    }

    @Override
    public void draw(Batch batch) {
        batch.setColor(getColor());
        batch.draw(TEXTURE,
                getX() - getOriginX(), getY()- getOriginY(),
                getOriginX(), getOriginY(),
                getWidth(), getHeight(),
                1, 1,
                0,
                getRegionX(), getRegionY(),
                getRegionWidth(), getRegionHeight(),
                false, false);
        batch.setColor(Color.WHITE);    }

    @Override
    public void dispose() {
        TEXTURE.dispose();
    }

    @Override
    public void reset() {

    }

    public int getId() {
        return this.currentID;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public void setBoundingCircle(float x, float y, float radius) {
        this.boundingCircle.set(x, y, radius);
    }

    public boolean overlap(Rectangle rect) {
        if(rect.contains(getX() - RADIUS, getY()) || rect.contains(getX() + RADIUS, getY()) ||
                rect.contains(getX(), getY() - RADIUS) || rect.contains(getX(), getY() + RADIUS)) {
            return true;
        } else
            return false;
    }

}
