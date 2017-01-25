package com.game;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
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
    public static final float LEVEL_CHANGING_TIME = 2.0f;
    private float currentEnemyTime = 0;
    private float currentShootTime = 0;
    private float currentLevelTime = 0;
    private float changeLevel = 0;
    Player ship;
    TestBackground testBG;
    private ResolutionChanger resolutionChanger;
    LinkedList<Enemy> enemies;
    LinkedList<Shoot> shoots;
    private EnemyPool enemyPool;
    private ShootPool shootPool;
    public int score;
    public int currentLevel;
    public boolean levelChange;
    private float[] levels;

    private Shoot currentShoot;

    public WorldController(){
        Gdx.input.setInputProcessor(this);
        init();
    }

    private void init(){
        resolutionChanger = new ResolutionChanger();
        levelChange = true;
        //player
        ship = new Player(
                (int)(Constants.VIEWPORT_WIDTH_MAX * 0.5f), (int)(Constants.VIEWPORT_HEIGHT * 0.1f));
        score = 0;

        //background
        testBG = new TestBackground();
        //enemies
        enemies = new LinkedList<Enemy>();
        enemyPool = new EnemyPool(30, 50);
        //shoots
        shoots = new LinkedList<Shoot>();
        shootPool = new ShootPool(10, 20);
        levels = new float[10];
        //Add level time
        for (int i = 0; i < levels.length; i++) {
            levels[i] = 3.0f ;
        }
        currentLevel = 1;
    }

    public void update(float delta){
        //Gdx.app.debug(TAG, "Delta!" + delta);
        if(delta >= 0.05f ) delta = 0.05f;
        handleControl(delta);
        phoneControl(delta);
        currentShootTime += delta;
        currentEnemyTime += delta;
        //Проверка смены уровня
        if(currentLevelTime >= levels[currentLevel]) {
            levelChange = true;
            currentLevel = (currentLevel + 1) % 10;
            currentLevelTime = 0;
        }
        //Пауза во время смены уровня с показом следующей волны

        if (levelChange) {
            if(enemies.isEmpty()) {
                changeLevel += delta;
                if (changeLevel >= LEVEL_CHANGING_TIME) {
                    levelChange = false;
                    changeLevel = 0;

                }
            }
        }
        testBG.update(delta);
        //add new enemy
        float newEnemyTime = 0.5f;
        if(!levelChange) {
            currentLevelTime += delta;
            if (currentEnemyTime >= newEnemyTime) {
                currentEnemyTime = 0.0f;
                enemies.addLast(enemyPool.obtain());
            }
        }

        //update enemies
        if (!enemies.isEmpty()) {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy currentEnemy = enemies.get(i);
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
        //update shoots
        if (!shoots.isEmpty()) {
            for (int i = 0; i < shoots.size(); i++) {
                currentShoot = shoots.get(i);
                currentShoot.update(delta);
                if (currentShoot.getY() >= Constants.VIEWPORT_HEIGHT) {
//                    Gdx.app.debug("Delete", "Shoot #" + currentShoot.getId() +
//                            "\tShot count: " + shoots.size() +
//                            "\tPool free: " + shootPool.getFree());
                    shoots.remove(currentShoot);
                    shootPool.free(currentShoot);
                    continue;
                }
                //check collision with enemy
                for (int j = 0; j < enemies.size(); j++) {
                    Enemy currentEnemy = enemies.get(j);
                    if (currentShoot.getY() >= currentEnemy.getY()) {
                        if (currentShoot.overlap(currentEnemy.getBoundingRectangle())) {
                            shoots.remove(currentShoot);
                            shootPool.free(currentShoot);
                            enemies.remove(currentEnemy);
                            currentEnemy.reset();
                            enemyPool.free(currentEnemy);
                            score += 100;
                        }
                    }
                }
            }
        }
    }

    private void handleControl(float delta){
        if (Gdx.app.getType() != Application.ApplicationType.Desktop)
            return;
        if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
            ship.update(0, -Player.VELOCITY * delta);
        }
        if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
            ship.update(0, Player.VELOCITY * delta);
        }
        if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
            ship.update(Player.VELOCITY * delta, 0);
        }
        if(Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)) {
            ship.update(-Player.VELOCITY * delta, 0);
        }
        if(Gdx.input.isKeyPressed(Keys.SPACE) && currentShootTime >= Player.SHOOT_RELOAD) {
            currentShootTime = 0.0f;
            shoot();
        }

    }
    private void phoneControl(float delta) {
        if(Gdx.input.isTouched() && currentShootTime >= Player.SHOOT_RELOAD) {
            currentShootTime = 0.0f;
            shoot();
        }
        if(Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
            float amountX = Gdx.input.getAccelerometerX() / 10.0f;
            float amountY = Gdx.input.getAccelerometerY() / 10.0f;
            amountX *= 90.0f;
            amountY *= 90.0f;
            if (Math.abs(amountX) < 5.0f) {
                amountX = 0;
            }
            if (Math.abs(amountY) < 5.0f) {
                amountY = 0;
            }
            amountX /= 10;
            amountY /= 10;
            ship.update(-Player.VELOCITY * amountY * delta, -Player.VELOCITY * amountX * delta);
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
    public void dispose() {

    }

    private void shoot() {
        currentShoot = shootPool.obtain();
        currentShoot.setPosition(ship.getX(), ship.getY());
        currentShoot.setBoundingCircle(ship.getX(), ship.getY(), Shoot.RADIUS);
//        Gdx.app.debug("Shoot", "#" + currentShoot.getId() +
//                     " position (" + currentShoot.getX() + "/" + currentShoot.getY() + ")" );
        shoots.addLast(currentShoot);
    }
}
