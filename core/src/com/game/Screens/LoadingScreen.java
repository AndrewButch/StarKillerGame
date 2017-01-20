package com.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;


public class LoadingScreen extends AbstractGameScreen {

    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        // Create()
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0x00/255.0f, 0x00/255.0f, 0x00/255.0f, 0x00/255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }


    @Override
    public void hide() {
        // Dispose()
    }

}
