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
import com.game.Objects.Player;
import com.game.Objects.Shoot;
import com.game.Objects.ShootPool;
import com.game.Objects.TestBackground;
import com.game.Utils.DebugRenderer;
import com.game.Utils.ResolutionChanger;

import java.util.LinkedList;

public class WorldController extends InputAdapter implements Disposable{

    private static final String TAG = WorldController.class.getSimpleName();
    private float newEnemyTime = 0.5f;
    private float currentEnemyTime = 0;
    private float currentShootTime = 0;
    public Player ship;
    public TestBackground testBG;
    private ResolutionChanger resolutionChanger;
    public LinkedList<Enemy> enemies;
    public LinkedList<Shoot> shoots;
    private ParticleEffect effect;
    public EnemyPool enemyPool;
    public ShootPool shootPool;


    private Enemy currentEnemy;
    private Shoot currentShoot;

    public WorldController(){
        Gdx.input.setInputProcessor(this);
        init();
    }

    private void init(){
        resolutionChanger = new ResolutionChanger();
        //player
        ship = new Player(
                (int)(Constants.WIDTH_MAX * 0.5f), (int)(Constants.VIEWPORT_HEIGHT * 0.1f));
        //background
        testBG = new TestBackground();
        //effects
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("stars.particle"), Gdx.files.internal(""));
        //enemies
        enemies = new LinkedList<Enemy>();
        enemyPool = new EnemyPool(30, 50);
        enemies.addLast(enemyPool.obtain());
        //shoots
        shoots = new LinkedList<Shoot>();
        shootPool = new ShootPool(10, 20);


    }

    public void update(float delta){
        //Gdx.app.debug(TAG, "Delta!" + delta);
        if(delta >= 0.05f ) delta = 0.05f;
       handleControl(delta);
        phoneControl(delta);
        currentEnemyTime += delta;
        currentShootTime += delta;
        //add new enemy
        testBG.update(delta);
        if (currentEnemyTime >= newEnemyTime /*&& enemies.size() < 10*/) {
            currentEnemyTime = 0.0f;
            enemies.addLast(enemyPool.obtain());

        }
        //update enemies
        if(!enemies.isEmpty()) {
            for (int i = 0; i < enemies.size(); i++) {
                currentEnemy = enemies.get(i);
                currentEnemy.update(delta);
                if (currentEnemy.getY() <= -currentEnemy.getHeight()) {
//                    Gdx.app.debug("Delete", "Enemy #" + currentEnemy.getId() +
//                            "\tShip count: " + enemies.size() +
//                            "\tPool free: " + enemyPool.getFree());
                    enemies.remove(currentEnemy);
                    currentEnemy.reset();
                    enemyPool.free(currentEnemy);
                }

            }
        }
        if (!shoots.isEmpty()) {
            for (int i = 0; i < shoots.size(); i++) {
                currentShoot = shoots.get(i);
                currentShoot.update(delta);
                if (currentShoot.getY() >= Constants.VIEWPORT_HEIGHT) {
                    Gdx.app.debug("Delete", "Shoot #" + currentShoot.getId() +
                            "\tShot count: " + shoots.size() +
                            "\tPool free: " + shootPool.getFree());
                    shoots.remove(currentShoot);
                    shootPool.free(currentShoot);
                }
            }
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
        if(Gdx.input.isKeyPressed(Keys.SPACE) && currentShootTime >= ship.SHOOT_RELOAD) {
            currentShootTime = 0.0f;
            shoot();

        }
    }
    private void phoneControl(float delta) {
        if(Gdx.input.isTouched() && currentShootTime >= ship.SHOOT_RELOAD) {
            currentShootTime = 0.0f;
            shoot();
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

//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        shoot();
//        return true;
//    }

    @Override
    public void dispose() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).dispose();
        }
        for (int i = 0; i < shoots.size(); i++) {
            shoots.get(i).dispose();
        }
        testBG.dispose();
    }

    private void shoot() {
        currentShoot = shootPool.obtain();
        currentShoot.setPosition(ship.getX(), ship.getY());
        Gdx.app.debug("Shoot", "#" + currentShoot.getId() +
                     " position (" + currentShoot.getX() + "/" + currentShoot.getY() + ")" );
        shoots.addLast(currentShoot);
    }
}
