package com.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.game.Utils.Assets;
import com.game.WorldController;
import com.game.WorldRenderer;




public class GameScreen extends AbstractGameScreen {
    private static final String TAG = GameScreen.class.getSimpleName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;
    private boolean paused;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        // Create()
        // set log level
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Assets.instance.init(new AssetManager());

        worldController = new WorldController();
        worldRenderer = new WorldRenderer(worldController);
        paused = false;
    }

    @Override
    public void render(float delta) {
        if(!paused) {
            worldController.update(Gdx.graphics.getDeltaTime());
        }
        Gdx.gl.glClearColor(0x00/255.0f, 0x00/255.0f, 0x00/255.0f, 0x00/255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }


    @Override
    public void hide() {
        // Dispose()
        worldRenderer.dispose();
        Assets.instance.dispose();
    }

    @Override
    public void resume() {
        super.resume();
        // Передача управления ??!?!
        paused = false;
    }
    @Override
    public void pause() {
        paused = true;
    }
}
