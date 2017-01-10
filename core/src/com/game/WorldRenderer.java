package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.Objects.TestBackground;
import com.game.Utils.DebugRenderer;

public class WorldRenderer implements Disposable{
    private static final String TAG = WorldRenderer.class.getSimpleName();
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private WorldController controller;
    private DebugRenderer debugRenderer;
    private TestBackground testBG;


    public WorldRenderer(WorldController controller) {
        this.controller = controller;
        init();
    }

    private void init(){

        camera = new OrthographicCamera();
        camera.position.set(Constants.WIDTH_MAX * 0.5f, Constants.HEIGHT_MAX * 0.5f, 0);
        Gdx.app.debug(TAG, "Camera at: " + camera.position.x + "/" + camera.position.y);
        camera.update();
        //viewport = new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, camera);
        viewport = new FillViewport(Constants.WIDTH_MAX, Constants.HEIGHT_MAX, camera);
        //viewport = new ExtendViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT,
         //      Constants.WIDTH_MAX, Constants.HEIGHT_MAX, camera);

        viewport.apply();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        debugRenderer = new DebugRenderer(camera.combined, true);
        testBG = new TestBackground(new Texture(Gdx.files.internal("testBackground.png")));

        //Gdx.app.debug(TAG, "Init Viewport: " + camera.viewportWidth + "/" + camera.viewportHeight);
    }

    public void render() {
        batch.begin();
        testBG.draw(batch);
        controller.ship.draw(batch);
        batch.end();
        debugRenderer.drawGrid();
    }

    public void resize(int width, int height){
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
       // camera.update();
       // Gdx.app.debug(TAG, "Resize Viewport: " + camera.viewportWidth + "/" + camera.viewportHeight);
        viewport.update(width, height);
        Gdx.app.debug(TAG, "New viewport: " + width * 2  + "/" + height * 2 +
                    "\t" + "ratio: " + (float)height/width);
    }

    @Override
    public void dispose() {
        batch.dispose();
        debugRenderer.dispose();

    }



}
