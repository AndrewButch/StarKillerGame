package com.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.Constants;
import com.game.Utils.Assets;


public class LoadingScreen extends AbstractGameScreen {
    private static final String TAG = LoadingScreen.class.getSimpleName();

    private SpriteBatch batch;
    private Texture background, logo, progressBarImg, progressBarBaseImg;
    private OrthographicCamera camera;
    private Viewport viewport;
    private AssetManager manager;
    private Vector2 logoPos, pbPos;

    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        // Create()
        batch = new SpriteBatch();
        manager = Assets.instance.getAssetManager();
        // load bars
        Assets.instance.loadBars();
        // load game assets
        Assets.instance.loadGameAssets();

        background = manager.get("background.png");
        logo = manager.get("logo.png");
        progressBarImg = manager.get("progress_bar.png");
        progressBarBaseImg = manager.get("progress_bar_base.png");

        camera = new OrthographicCamera();
        viewport = new FillViewport(Constants.VIEWPORT_WIDTH_MAX, Constants.VIEWPORT_HEIGHT_MAX, camera);
        viewport.apply(true);

        logoPos = new Vector2(Constants.VIEWPORT_WIDTH_MAX * 0.5f, Constants.VIEWPORT_HEIGHT_MAX * 0.5f);
        pbPos = new Vector2(logoPos.x, logoPos.y - 1.0f);
//        Gdx.app.debug(TAG, "Logo position " + logoPos.x + "/" + logoPos.y);
//        Gdx.app.debug(TAG, "Progress bar position " + pbPos.x + "/" + pbPos.y);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0x00/255.0f, 0x00/255.0f, 0x00/255.0f, 0x00/255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // draw BG
        batch.draw(background,
                0, 0,
                0, 0,
                Constants.VIEWPORT_WIDTH_MAX, Constants.VIEWPORT_HEIGHT_MAX,
                1, 1,
                0.0f,
                0, 0,
                background.getWidth(), background.getHeight(),
                false, false);
        //draw Logo
        batch.draw(logo,
                logoPos.x - 1.5f, logoPos.y - 0.2f,
                0, 0,
                3.0f, 0.4f,
                1, 1,
                0.0f,
                0, 0,
                logo.getWidth(), logo.getHeight(),
                false, false);
        //draw progress bar base
        batch.draw(progressBarBaseImg,
                pbPos.x - 1.5f , pbPos.y - 0.1f ,
                0, 0,
                3.0f, 0.2f,
                1, 1,
                0.0f,
                0, 0,
                progressBarBaseImg.getWidth(), progressBarBaseImg.getHeight(),
                false, false);
        //draw progress bar
        batch.draw(progressBarImg,
                pbPos.x - 1.5f, pbPos.y - 0.1f,
                0, 0,
                3.0f * manager.getProgress(), 0.2f,
                1, 1,
                0.0f,
                0, 0,
                progressBarImg.getWidth(), progressBarImg.getHeight(),
                false, false);
        batch.end();
        if(manager.update()) {
            //if (Gdx.input.isTouched()) {
                Assets.instance.initGameTextures();
                game.setScreen(new GameScreen(game));
            //}

        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
//        Gdx.app.debug(TAG, "VIEWPORT" +
//                "\n\tGameViewport Width/Height: " + viewport.getScreenWidth() + "/" + viewport.getScreenHeight() +
//                "\n\tWorld Width/Height: " + viewport.getWorldWidth() + "/" + viewport.getWorldHeight() +
//                "\n\tVieport Left/Right: " + Constants.VIEWPORT_LEFT + "/" + Constants.VIEWPORT_RIGHT);
    }

    @Override
    public void pause() {

    }


    @Override
    public void hide() {
        // Dispose()
        batch.dispose();
    }

}
