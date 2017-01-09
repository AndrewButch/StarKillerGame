package com.game;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.game.Objects.TestTriangle;
import com.game.Utils.ResolutionChanger;

public class WorldController extends InputAdapter {

    private static final String TAG = WorldController.class.getSimpleName();
    public TestTriangle ship;
    private ResolutionChanger resolutionChanger;

    public WorldController(){
        Gdx.input.setInputProcessor(this);
        init();
    }

    private void init(){
        ship = new TestTriangle(
                (int)(Constants.VIEWPORT_WIDTH * 0.5f), (int)(Constants.VIEWPORT_HEIGHT * 0.1f),
                1, 1);

        resolutionChanger = new ResolutionChanger();

    }

    public void update(float delta){
       handleControl(delta);
        //ship.update(delta);
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
        if(Gdx.input.isKeyPressed(Keys.R)) {
            Vector2 v = resolutionChanger.next();
            Gdx.graphics.setWindowedMode((int)v.x, (int)v.y);
        }
    }

}
