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
    private OrthographicCamera cameraGUI;
    private Viewport viewport;
    private Viewport viewportGUI;
    private SpriteBatch batch;
    private WorldController controller;
    private DebugRenderer debugRenderer;



    public WorldRenderer(WorldController controller) {
        this.controller = controller;
        init();
    }

    private void init(){

        camera = new OrthographicCamera();
        viewport = new FillViewport(Constants.WIDTH_MAX, Constants.HEIGHT_MAX, camera);
        /*viewport = new ExtendViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT,
               Constants.WIDTH_MAX, Constants.HEIGHT_MAX, camera);*/
        viewport.apply(true);
        cameraGUI = new OrthographicCamera();
        cameraGUI.setToOrtho(true);
        viewportGUI = new FillViewport(Constants.VIEWPORT_GUI_WIDTH_MAX, Constants.VIEWPORT_GUI_HEIGHT_MAX,cameraGUI);
        viewportGUI.apply(true);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        debugRenderer = new DebugRenderer(camera.combined);

        viewport.getScreenHeight();
    }

    public void render() {
        batch.begin();
        controller.testBG.draw(batch);
        controller.ship.draw(batch);
        for(int i = 0; i < controller.enemies.size(); i++) {
            controller.enemies.get(i).draw(batch);
        }
        for(int i = 0; i < controller.shoots.size(); i++) {
            controller.shoots.get(i).draw(batch);
        }

        batch.end();
        debugRenderer.drawGrid();
    }

    public void resize(int width, int height){
        viewport.update(width, height);
        Gdx.app.debug(TAG, "VIEWPORT" +
                 "\n\tScreen Width/Height: " + viewport.getScreenWidth() + "/" + viewport.getScreenHeight() +
                "\n\tWorld Width/Height: " + viewport.getWorldWidth() + "/" + viewport.getWorldHeight() +
                "\n\tVieport Left/Right: " + Constants.VIEWPORT_LEFT + "/" + Constants.VIEWPORT_RIGHT);
        viewportGUI.update(width, height);
        Gdx.app.debug(TAG, "GUI VIEWPORT" +
                 "\n\tScreen Width/Height: " + viewportGUI.getScreenWidth() + "/" + viewportGUI.getScreenHeight() +
                "\n\tWorld Width/Height: " + viewportGUI.getWorldWidth() + "/" + viewportGUI.getWorldHeight() +
                "\n\tVieport Left/Right: " + Constants.VIEWPORT_GUI_LEFT + "/" + Constants.VIEWPORT_GUI_RIGHT);
    }

    @Override
    public void dispose() {
        batch.dispose();
        debugRenderer.dispose();

    }





}
