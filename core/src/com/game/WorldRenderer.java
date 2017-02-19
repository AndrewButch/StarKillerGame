package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.Objects.Player;
import com.game.Utils.Assets;
import com.game.Utils.DebugRenderer;
import com.game.Utils.ResolutionChanger;

public class WorldRenderer extends InputAdapter implements Disposable{
    private static final String TAG = WorldRenderer.class.getSimpleName();
    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private Viewport viewport;
    private Viewport viewportGUI;
    private SpriteBatch batch;
    private WorldController controller;
    private DebugRenderer debugRenderer;
    private Vector2 touchPos;


    public WorldRenderer(WorldController controller) {
        this.controller = controller;
        init();
        Gdx.input.setInputProcessor(this);
    }

    private void init(){

        camera = new OrthographicCamera();
        viewport = new FillViewport(Constants.VIEWPORT_WIDTH_MAX, Constants.VIEWPORT_HEIGHT_MAX, camera);
        /*viewport = new ExtendViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT,
               Constants.VIEWPORT_WIDTH_MAX, Constants.VIEWPORT_HEIGHT_MAX, camera);*/
        viewport.apply(true);
        cameraGUI = new OrthographicCamera();
        cameraGUI.setToOrtho(true);
        viewportGUI = new FillViewport(Constants.VIEWPORT_GUI_WIDTH_MAX, Constants.VIEWPORT_GUI_HEIGHT_MAX,cameraGUI);
        viewportGUI.apply(true);

        batch = new SpriteBatch();

        debugRenderer = new DebugRenderer(camera.combined);
        touchPos = new Vector2();

    }

    public void render() {
        renderWorld(batch);
        renderGUI(batch);
        debugRenderer.drawGrid();
    }

    public void resize(int width, int height){
        viewport.update(width, height);
//        Gdx.app.debug(TAG, "VIEWPORT" +
//                 "\n\tGameViewport Width/Height: " + viewport.getScreenWidth() + "/" + viewport.getScreenHeight() +
//                "\n\tWorld Width/Height: " + viewport.getWorldWidth() + "/" + viewport.getWorldHeight() +
//                "\n\tVieport Left/Right: " + Constants.VIEWPORT_LEFT + "/" + Constants.VIEWPORT_RIGHT);
        viewportGUI.update(width, height);
        Constants.updateGUI(width, height);
//        Gdx.app.debug(TAG, "GUI VIEWPORT" +
//                "\n\tGUI Width/Height: " + Constants.VIEWPORT_GUI_WIDTH + "/" + Constants.VIEWPORT_GUI_HEIGHT +
//                "\n\tWorld Width/Height: " + viewportGUI.getWorldWidth() + "/" + viewportGUI.getWorldHeight() +
//                "\n\tVieport Left/Right: " + Constants.VIEWPORT_GUI_LEFT + "/" + Constants.VIEWPORT_GUI_RIGHT);
    }


    private void renderWorld(SpriteBatch batch) {

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//        Texture texture = new Texture(Gdx.files.internal("InputGame/SpaceShip.png"));
//        batch.draw(texture,
//                Constants.VIEWPORT_WIDTH_MAX * 0.5f, Constants.VIEWPORT_HEIGHT_MAX * 0.5f,
//                0, 0,
//                1, 1,
//                1, 1,
//                0,
//                0, 0,
//                texture.getWidth(), texture.getHeight(), false, false);
        touchControl(Gdx.graphics.getDeltaTime());

        controller.testBG.draw(batch);
        controller.ship.draw(batch);
        for(int i = 0; i < controller.enemies.size(); i++) {
            controller.enemies.get(i).draw(batch);
        }
        for(int i = 0; i < controller.shoots.size(); i++) {
            controller.shoots.get(i).draw(batch);
        }
        controller.joystick.draw(batch);
        batch.end();
    }
    private void renderGUI(SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        if(controller.levelChange && controller.enemies.isEmpty()) {
            renderNextLevelText(batch);

        }
        renderFPS(batch);
        renderScore(batch);
        batch.end();
    }

    private void renderFPS(SpriteBatch batch) {
        float x = Constants.VIEWPORT_GUI_LEFT + 20;
        float y = Constants.VIEWPORT_GUI_HEIGHT - 30;
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
        if (fps >= 45) {
            // fps more 45 have green color
            fpsFont.setColor(0, 1, 0, 1);
        } else if (fps >= 30) {
            // fps more 30 have yellow color
            fpsFont.setColor(1, 1, 0, 1);
        } else {
            // fps less 30 have red color
            fpsFont.setColor(1, 0, 0, 1);
        }
        fpsFont.draw(batch, "FPS: " + fps, x , y);
        fpsFont.setColor(1, 1, 1, 1);
    }

    private void renderScore(SpriteBatch batch) {
        float x = Constants.VIEWPORT_GUI_LEFT + 20;
        float y = 10;
        Assets.instance.fonts.defaultBig.draw(batch, "Score: " + controller.score, x, y);
    }

    private void renderNextLevelText(SpriteBatch batch) {
        float x = (Constants.VIEWPORT_GUI_LEFT + Constants.VIEWPORT_GUI_RIGHT) * 0.5f;
        float y = Constants.VIEWPORT_GUI_HEIGHT * 0.5f;

        BitmapFont wave = Assets.instance.fonts.defaultBig;
        wave.setColor(Color.YELLOW);
        wave.draw(batch,  "Wave " + controller.currentLevel , x, y, 0, Align.center, false);
    }

    @Override
    public void dispose() {
        batch.dispose();
        debugRenderer.dispose();
    }

    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPos.set(screenX, screenY);
        viewport.unproject(touchPos);
        return controller.touchDown(touchPos.x, touchPos.y, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return controller.touchUp(screenX, screenY, pointer, button);
    }

    public void touchControl(float delta) {
        if(Gdx.input.isTouched() && controller.joystick.isMoving()) {
            //получаем величину сигнала от контроллера нормированную (от 0 до 1)
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            Gdx.app.debug(TAG, "\n\tTouch" + touchPos + "");
            Vector2 translate = controller.joystick.move(touchPos.x, touchPos.y);
            // умножаем на максимальную скорость значение скорости получаем Speed/sec
            translate.scl(Player.VELOCITY * delta);
            //Gdx.app.debug("Test", "\n\tCurrent pos" + currentPos + "");
            controller.ship.update(translate.x, translate.y);
        }

    }
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.G) {
            if(DebugRenderer.isGridEnable()){
                DebugRenderer.enableGrid(false);
            } else {
                DebugRenderer.enableGrid(true);
            }
        }
        if(keycode == Input.Keys.R) {
            ResolutionChanger.instance.changeResolution();
        }
        return true;
    }

}
