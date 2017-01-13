package com.game;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.game.Objects.Enemy;
import com.game.Objects.EnemyPool;
import com.game.Objects.TestTriangle;
import com.game.Utils.DebugRenderer;
import com.game.Utils.ResolutionChanger;

import java.util.ArrayList;
import java.util.LinkedList;

public class WorldController extends InputAdapter implements Disposable{

    private static final String TAG = WorldController.class.getSimpleName();
    private float newEnemyTime = 1.0f;
    private float currentTime = 0;
    public TestTriangle ship;
    private ResolutionChanger resolutionChanger;
    public LinkedList<Enemy> enemies;
    private Vector2 touchPos;
    private ParticleEffect effect;
    public EnemyPool enemyPool;

    public Enemy currentEnemy;

    public WorldController(){
        Gdx.input.setInputProcessor(this);
        init();
    }

    private void init(){
        ship = new TestTriangle(
                (int)(Constants.WIDTH_MAX * 0.5f), (int)(Constants.VIEWPORT_HEIGHT * 0.1f),
                1.36f, 1.5f);
        touchPos = new Vector2();
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("stars.particle"), Gdx.files.internal(""));
        resolutionChanger = new ResolutionChanger();
        enemies = new LinkedList<Enemy>();
        enemyPool = new EnemyPool(10);
        enemies.add(enemyPool.obtain());
    }

    public void update(float delta){
       handleControl(delta);
        currentTime += delta;
        if (currentTime >= newEnemyTime) {
            enemies.add(enemyPool.obtain());
            currentTime = 0.0f;
        }
        /*for(Enemy enemy : enemies) {
            enemy.update(delta);
            if(enemy.getY() <= - enemy.getHeight()) {
                enemies.remove(enemy);
                enemyPool.free(enemy);
            }
        }*/
        for(int i = 0; i < enemies.size(); ) {
            currentEnemy = enemies.get(i);
            currentEnemy.update(delta);
            if(currentEnemy.getY() <= - currentEnemy.getHeight()) {
                Gdx.app.debug("Delete", "Enemy #" + currentEnemy.getId());
                enemies.remove(currentEnemy);
                enemyPool.free(currentEnemy);
                continue;
            }
            i++;
        }
    }

    private void handleControl(float delta){
        if (Gdx.app.getType() != Application.ApplicationType.Desktop)
            return;
        if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
            ship.update(0, -ship.VELOCITY * delta);
        }
        if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
            ship.update(0, ship.VELOCITY * delta);
        }
        if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
            ship.update(ship.VELOCITY * delta, 0);
        }
        if(Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)) {
            ship.update(-ship.VELOCITY * delta, 0);
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Keys.G) {
            if(DebugRenderer.isGridEnable()){
                DebugRenderer.enableGrid(false);
            } else {
                DebugRenderer.enableGrid(true);
            }
        }
        if(keycode == Keys.R) {
            Vector2 v = resolutionChanger.next();
            Gdx.graphics.setWindowedMode((int)v.x, (int)v.y);
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return true;
    }

    @Override
    public void dispose() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).dispose();
        }
    }
}
