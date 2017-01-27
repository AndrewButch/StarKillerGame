package com.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.Constants;
import com.game.Utils.Assets;


public class MenuScreen extends AbstractGameScreen {
    private static final String TAG = MenuScreen.class.getSimpleName();

    private Stage stage;
    private Image imgLogo;
    private Image imgBackground;
    private Button btnMenuPlay;
    private Button btnMenuOptions;

    //debug
    private final float DEBUG_REBUILD_INTERVAL = 5.0f;
    private boolean debugEnable = false;
    private float debugRebuildStage;


    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        //Create
        stage = new Stage(new FillViewport(Constants.VIEWPORT_WIDTH_MAX, Constants.VIEWPORT_HEIGHT_MAX));
        Gdx.input.setInputProcessor(stage);
        Assets.instance.loadMenuAssets();
        Assets.instance.initMenuTextures();
        rebuildStage();
    }

    private void rebuildStage() {
        stage.clear();

        //build all layers
        Table layerBackground = buildBackgroundLayer();
        Table layerLogos = buildLogosLayer();
        Table layerControls = buildControlsLayer();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(Constants.VIEWPORT_GUI_LEFT, Constants.VIEWPORT_GUI_RIGHT);
        stack.addActor(layerBackground);
        stack.addActor(layerLogos);
        stack.addActor(layerControls);
    }

    private Table buildBackgroundLayer() {
        Table layer = new Table();

        return layer;
    }

    private Table buildControlsLayer() {
        Table layer = new Table();
        return layer;
    }

    private Table buildLogosLayer() {
        Table layer = new Table();
        return layer;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (debugEnable) {
            debugRebuildStage -= delta;
            if(debugRebuildStage <= 0) {
                debugRebuildStage = DEBUG_REBUILD_INTERVAL;
                rebuildStage();
            }
        }

        stage.act(delta);
        stage.draw();
        if(Gdx.input.isTouched()) {
            game.setScreen(new LoadingScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        Gdx.app.debug(TAG, "Stage Viewport: " +
                stage.getViewport().getScreenWidth() + "/" + stage.getViewport().getScreenHeight());
    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {
        // Dispose
        stage.dispose();
    }
}
