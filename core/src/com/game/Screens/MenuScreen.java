package com.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.Constants;


public class MenuScreen extends AbstractGameScreen {
    private static final String TAG = MenuScreen.class.getSimpleName();

    private Stage stage;


    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        //Create
        stage = new Stage(new FillViewport(Constants.VIEWPORT_WIDTH_MAX, Constants.VIEWPORT_HEIGHT_MAX));
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
    }

    private void rebuildStage() {
        stage.clear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
