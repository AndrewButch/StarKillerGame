package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.Utils.DebugRenderer;

public class WorldRenderer implements Disposable{
    private static final String TAG = WorldRenderer.class.getSimpleName();
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private WorldController controller;
    private DebugRenderer debugRenderer;


    public WorldRenderer(WorldController controller) {
        this.controller = controller;
        init();
    }

    private void init(){

        camera = new OrthographicCamera();
        camera.position.set(Constants.VIEWPORT_WIDTH * 0.5f, Constants.VIEWPORT_HEIGHT * 0.5f, 0);
        camera.update();
        viewport = new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, camera);
        viewport.apply();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        debugRenderer = new DebugRenderer(camera.combined, true);

        //Gdx.app.debug(TAG, "Init Viewport: " + camera.viewportWidth + "/" + camera.viewportHeight);
    }

    public void render() {
        debugRenderer.drawGrid();
        batch.begin();
        controller.ship.draw(batch);
        batch.end();
    }

    public void resize(int width, int height){
        //camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
       // Gdx.app.debug(TAG, "Resize Viewport: " + camera.viewportWidth + "/" + camera.viewportHeight);
        viewport.update(width, height);
        Gdx.app.debug(TAG, "New viewport: " + width + "/" + height + "\t" + "ratio: " + (float)height/width);
    }

    @Override
    public void dispose() {
        batch.dispose();

    }

}
