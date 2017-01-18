package com.game.Objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.game.Constants;
import com.game.Utils.Assets;


public class Enemy extends AbstractGameObject implements Pool.Poolable {
    private static final float VELOCITY = 3.0f;
    private static final float SIZE_X = 1.0f;
    private static final float SIZE_Y = 1.0f;

    private EnemyType type = EnemyType.NORMAL;
    private Vector2 velocity;
    public static int id = 0;
    private int enemyId = 0;

    public enum EnemyType {
        NORMAL, HEAVY, FAST, BOSS
    }
    public Enemy(){
        this(generateRandomPosVector());
    }

    public Enemy(Vector2 pos) {
        this(pos.x, pos.y);
    }
    //Constructor with manual enemy position
    public Enemy(float posX, float posY){
        super(Assets.instance.player);
        this.setPosition(posX, posY);
        this.setSize(SIZE_X, SIZE_Y);
        this.enemyId = id;
        this.velocity = new Vector2();
        setRandomVelocity();
        incrementId();
        this.setColor(MathUtils.random(0.0f, 1.0f),
                MathUtils.random(0.0f, 1.0f),
                MathUtils.random(0.0f, 1.0f), 1);

        Gdx.app.debug(  "Enemy", "#" + this.enemyId + " position(" + getX() + "/" + getY() + ")" +
                        "\tVelocity:(" + velocity.x + "/" + velocity.y + ")" +
                        "\tBoundingRect:(" + getBoundingRectangle().getX() + "/" + getBoundingRectangle().getY() + ")" +
                        "(" + getBoundingRectangle().getWidth() + "/" + getBoundingRectangle().getHeight() + ")");
    }

    @Override
    public void update(float delta) {
        translateY(-velocity.y * delta);

        /*if(getY() <= 0) setY(Constants.VIEWPORT_HEIGHT);*/
       // Gdx.app.debug("BOUNDING BOX", this.getBoundingRectangle() + " ");
    }

    public int getId() {
        return enemyId;
    }

    public void setEnemyId(int enemyId) {
        this.enemyId = enemyId;
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
        batch.setColor(getColor());
        batch.draw(getTexture(),
                getX(), getY(),
                getRegionWidth() * 0.5f, getRegionHeight() * 0.5f,
                getWidth(), getHeight(),
                1, 1,
                0,
                getRegionX(), getRegionY(),
                getRegionWidth(), getRegionHeight(),
                false, true);
        batch.setColor(Color.WHITE);
    }

    private static Vector2 generateRandomPosVector() {
        return new Vector2(
                /* x = */ MathUtils.random(Constants.VIEWPORT_LEFT, Constants.VIEWPORT_RIGHT - 1.0f),
                /* y = */ Constants.VIEWPORT_HEIGHT);
    }


    private void setRandomPosition() {
        this.setPosition(
                MathUtils.random(Constants.VIEWPORT_LEFT, Constants.VIEWPORT_RIGHT - 1.0f),
                Constants.VIEWPORT_HEIGHT);
    }

    private void setRandomVelocity() {
        this.velocity.set(0, VELOCITY + MathUtils.random(0, 2));
//        this.velocity.set(0, VELOCITY);
    }


    @Override
    public void reset() {
        setRandomPosition();
        setRandomVelocity();
    }
}
