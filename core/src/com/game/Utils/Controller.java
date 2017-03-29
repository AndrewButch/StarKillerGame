package com.game.Utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Controller {
    private final AtlasRegion controller_background;
    private final AtlasRegion controller_top;
    private final float maxSignal; // максимальный сигнал перемещения = радиус подложки
    private Sprite bg;
    private Sprite top;
    private Vector2 bgCenter; // координаты центра подложки джойстика
    private Vector2 topCenter; // координаты центра самого джойстика
    private Vector2 translate; // сигнал перемещения
    private boolean moving = false;
    private boolean controlInTouch = false;
    private boolean visible = false;

    /**
     * @param posX  X position of center
     * @param posY Y position of center
     * @param diameter diamater of joystic
     * @param controlInTouch enable move Joysick to the touch point
     */
    public Controller(float posX, float posY, float diameter, boolean controlInTouch) {
        // background path of controller
        this.controlInTouch = controlInTouch;
        controller_background = Assets.instance.joystickBG;
        controller_top = Assets.instance.joystick;
        bg = new Sprite(controller_background);
        bg.setPosition(posX, posY);
        bg.setSize(diameter, diameter);
        bg.setOriginCenter();
        bgCenter = new Vector2(bg.getX() + bg.getOriginX(), bg.getY() + bg.getOriginY());
        // joystick
        top = new Sprite(controller_top);
        top.setSize(diameter * 0.375f, diameter * 0.375f);
        top.setOriginCenter();
        top.setPosition(bgCenter.x - top.getOriginX(),
                        bgCenter.y - top.getOriginY());
        topCenter = new Vector2(top.getX() + top.getOriginX(), top.getY() + top.getOriginY());
        Gdx.app.debug("Controller", "\n\tBG center " + bgCenter +
                                    "\n\tTop center" + topCenter);
        translate = new Vector2();
        // макс.сигнал от перемещения контроллера по Х и Y равен РАДИУСУ подложки
        maxSignal = bg.getWidth() * 0.5f;
        Gdx.app.debug("Controller", "\n\tMax signal " + maxSignal);

    }

    public Controller(float posX, float posY, float diameter) {
        this(posX, posY, diameter, false);
    }
    public Controller(Vector2 pos, float diameter) {
        this(pos.x, pos.y, diameter, false);
    }
    public Controller(Vector2 pos, float diameter, boolean controlInTouch) {
        this(pos.x, pos.y, diameter, controlInTouch);
    }

    public void draw(SpriteBatch batch) {
        if(visible) {
            bg.draw(batch);
            top.draw(batch);
        }
    }

    public Vector2 move(float deltaX, float deltaY) {
        translate.set(0, 0);
        // перемещаем  в точку касания
        // и проверяем выход за пределы круга

        top.setPosition(deltaX - top.getOriginX(), deltaY - top.getOriginY());
        topCenter.x = top.getX() + top.getOriginX();
        topCenter.y =  top.getY() + top.getOriginY();
        // если джойстик выходит за пределы подложки, возвращаем на край
        if (bgCenter.dst(topCenter) > bg.getWidth() * 0.5f){
            //нормализация
            // C = R (B-A) / |BA|
            Vector2 vecX = new Vector2(bgCenter.x, topCenter.x);
            Vector2 VecY = new Vector2(bgCenter.y, topCenter.y);
            float x2 = vecX.y - vecX.x;
            float y2 = VecY.y - VecY.x;
            Vector2 coords = new Vector2(x2, y2);
            float len = coords.len();
            //находим новые координаты центра джойстика и от него позицию отрисовки
            // C = R (B-A) / |BA| + координата центра подложки
            topCenter.x = bgCenter.x + bg.getWidth() * 0.5f * (vecX.y - vecX.x) / len;
            topCenter.y = bgCenter.y + bg.getWidth() * 0.5f  * (VecY.y - VecY.x) / len;
            top.setPosition(topCenter.x - top.getOriginX(), topCenter.y - top.getOriginY());
            /*Gdx.app.debug("Controller",
                  "\n\tBG center " + bgCenter +
                   "\n\tTop center" + topCenter);*/
        }

        translate = topCenter.cpy().sub(bgCenter); // разница между центрами подложки и джойстика
        //Gdx.app.debug("Controller", "B - A" + translate + "" );
        translate.scl(1 / (maxSignal)); //нормализация значения + выходной сигнал 1 / длина вектора

        //Gdx.app.debug("Controller", "Output" + translate + "" );

        return translate;
    }

    public Rectangle getBoundingBox() {
        return bg.getBoundingRectangle();
    }

    public void setMoving (boolean moving) {
        this.moving = moving;
    }

    public boolean isMoving() {
        return moving;
    }
    public void reset() {
        top.setPosition(bgCenter.x - top.getOriginX(),
                bgCenter.y - top.getOriginY());
        topCenter.x = top.getX() + top.getOriginX();
        topCenter.y =  top.getY() + top.getOriginY();
    }

    public void setControllerCenter(float posX, float posY) {
        // bg
        this.bgCenter.set(posX, posY);
        this.bg.setPosition(posX - bg.getOriginX(), posY - bg.getOriginY());
        // joystick
        this.topCenter.set(posX, posY);
        this.top.setPosition(posX - top.getOriginX(), posY - top.getOriginY());
        Gdx.app.debug("Controller", "\n\tnew BG center " + bgCenter +
                "\n\tnew Top center" + topCenter);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
