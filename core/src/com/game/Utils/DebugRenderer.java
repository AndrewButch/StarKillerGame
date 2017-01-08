package com.game.Utils;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.game.Constants;

public class DebugRenderer {
    private ShapeRenderer shapeRenderer;
    private boolean drawGrid;
    private Color color = new Color(Color.DARK_GRAY);




    public DebugRenderer(Matrix4 matrix4, boolean drawGrid) {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(matrix4);
        this.drawGrid = drawGrid;

    }

    public void setColor(Color color){
        this.color = color;
    }

    public void drawGrid() {
        if(drawGrid == true) {
            shapeRenderer.setColor(color);
            shapeRenderer.begin(ShapeType.Line);

            for(int i = 0; i <= (int)Constants.VIEWPORT_WIDTH; i++){
                shapeRenderer.line(i, 0,  i, Constants.VIEWPORT_HEIGHT);
            }
            for (int j = 0; j <= (int)Constants.VIEWPORT_HEIGHT; j++) {
                shapeRenderer.line(0, j,  Constants.VIEWPORT_WIDTH, j);
            }
            shapeRenderer.end();
        }

    }
}
