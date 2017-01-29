package com.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.Constants;
import com.game.Utils.Assets;
import com.game.Utils.ResolutionChanger;


public class MenuScreen extends AbstractGameScreen {
    private static final String TAG = MenuScreen.class.getSimpleName();

    private Stage stage;
    private Table table;
    private Image imgLogo;
    private ImageButton btnMenuPlay;
    private ImageButton btnMenuOptions;

    private Viewport viewport;
    private SpriteBatch batch;




    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        //Create
        viewport = new FillViewport(1440 * 0.5f, 1920 * 0.5f);
        //viewport = new FillViewport(Constants.VIEWPORT_WIDTH_MAX, Constants.VIEWPORT_HEIGHT_MAX);
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        Assets.instance.loadMenuAssets();
        Assets.instance.initMenuTextures();

        //btn play
        ImageButton.ImageButtonStyle ibs = new ImageButton.ImageButtonStyle();
        ibs.imageUp = new TextureRegionDrawable(Assets.instance.btnMenuPlayUp);
        ibs.imageDown = new TextureRegionDrawable(Assets.instance.btnMenuPlayDown);
        btnMenuPlay = new ImageButton(ibs);
        btnMenuPlay.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen( new LoadingScreen(game));
            }
        });
        // options button
        ImageButton.ImageButtonStyle ibs1 = new ImageButton.ImageButtonStyle();
        ibs1.imageUp = new TextureRegionDrawable(Assets.instance.btnMenuOptionsUp);
        ibs1.imageDown = new TextureRegionDrawable(Assets.instance.btnMenuOptionsDown);
        btnMenuOptions = new ImageButton(ibs1);
        btnMenuOptions.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ResolutionChanger.instance.changeResolution();

            }
        });
        //logo
        imgLogo= new Image(Assets.instance.imgMenuLogo);

        // Table
        table = new Table();
        //table.debug(); //enable debug

        rebuildStage();
    }

    private void rebuildStage() {
        stage.clear();

        // Set table structure
        table.row();
        table.add(imgLogo).padBottom(100).padTop(100).expand();
        table.row();
        table.add(btnMenuPlay).expand();
        table.row();
        table.add(btnMenuOptions).padBottom(200).expand();
        table.setFillParent(true);
        table.pack();
        table.getColor().a = 0f;
        table.addAction(Actions.fadeIn(2f));

        stage.addActor(table);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(Assets.instance.imgMenuBackground.getTexture(),
                0, 0,
                0, 0,
                1440 * 0.5f, 1920 * 0.5f,
                1, 1,
                0,
                Assets.instance.imgMenuBackground.getRegionX(), Assets.instance.imgMenuBackground.getRegionY(),
                Assets.instance.imgMenuBackground.getRegionWidth(), Assets.instance.imgMenuBackground.getRegionHeight(),
                false, false);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
