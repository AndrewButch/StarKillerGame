package com.game.Screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.game.Objects.AbstractGameObject;
import com.game.Utils.Assets;

public abstract class AbstractGameScreen implements Screen{
    protected Game game;


    public AbstractGameScreen(Game game) {
        this.game = game;

    }

    public abstract void show();

    public abstract void render(float delta);

    public abstract void resize(int width, int height);

    public abstract void pause();

    public abstract void hide();

    @Override
    public  void resume() {
        game.setScreen(new LoadingScreen(game));
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }

}
