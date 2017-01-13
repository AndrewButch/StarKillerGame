package com.game.Objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.game.Constants;


public class Enemy extends AbstractGameObject implements Disposable {
    private static final Texture texture = new Texture(Gdx.files.internal("SpaceShip.png"));
    private static final float VELOCITY = 3.0f;

    private Rectangle boundingBox;
    private Color color =  Color.RED;
    private EnemyType type = EnemyType.NORMAL;
    private Vector2 velocity;


    @Override
    public void dispose() {
        texture.dispose();
    }

    public enum EnemyType {
        NORMAL, HEAVY, FAST, BOSS
    }

    private static int id = 0;
    private int enemyId = 0;

    //Constructor with random generated enemy position
    public Enemy() {
        this(   /* x = */MathUtils.random((int)Constants.VIEWPORT_LEFT, (int)Constants.VIEWPORT_RIGHT),
                /* y = */Constants.VIEWPORT_HEIGHT - 1.0f);
    }

    //Constructor with manual enemy position
    public Enemy(float posX, float posY){
        super(texture);
        this.setX(posX);
        this.setY(posY);
        this.setSize(1, 1);
        this.enemyId = id;
        this.velocity = new Vector2(0, VELOCITY + MathUtils.random(0, 2.0f));
        incrementId();
        Gdx.app.debug(  "Enemy ", "#" + this.enemyId + " position (" + getX() + "/" + getY() + ")" +
                        "Velocity: (" + velocity.x + "/" + velocity.y + ") ");
    }

    @Override
    public void update(float delta) {
        translateY(-velocity.y * delta);
    }

    @Override
    public void init() {

    }

    public static int getId() {
        return id;
    }

    private static void incrementId() {
        Enemy.id++;
    }

    public EnemyType getType() {
        return type;
    }

    public void setType(EnemyType type) {
        this.type = type;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(texture,
                getX(), getY(),
                getRegionWidth() * 0.5f, getRegionHeight() * 0.5f,
                getWidth(), getHeight(),
                1, 1,
                0,
                getRegionX(), getRegionY(),
                getRegionWidth(), getRegionHeight(),
                false, true);
    }
}
