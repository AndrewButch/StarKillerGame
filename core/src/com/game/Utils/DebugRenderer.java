package com.game.Utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.game.Constants;

public class DebugRenderer implements Disposable {
    private ShapeRenderer shapeRenderer;
    private static boolean drawGrid = false;
    private Color color = new Color(Color.DARK_GRAY);


    public DebugRenderer(Matrix4 matrix4) {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(matrix4);
        //Gdx.app.debug("DEBUG RENDER:", Constants.VIEWPORT_LEFT + "/" + Constants.VIEWPORT_RIGHT);
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void drawGrid() {
        if(drawGrid) {
            shapeRenderer.setColor(color);
            shapeRenderer.begin(ShapeType.Line);

            //вертикальные линии
            for (float i = Constants.VIEWPORT_LEFT; i <= Constants.VIEWPORT_RIGHT; i++) {
                shapeRenderer.line(i, 0, i, Constants.VIEWPORT_HEIGHT);
            }
            //горизонатльные линии
            for (float j = 0; j <= Constants.VIEWPORT_HEIGHT; j++) {
                shapeRenderer.line(Constants.VIEWPORT_LEFT, j, Constants.VIEWPORT_RIGHT, j);
            }

            shapeRenderer.end();
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    public static boolean isGridEnable() {
        return drawGrid;
    }

    public static void enableGrid(boolean drawGrid) {
        DebugRenderer.drawGrid = drawGrid;
    }
}
