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
    private boolean drawGrid;
    private Color color = new Color(Color.DARK_GRAY);
    private int colSize;
    private int rowSize;




    public DebugRenderer(Matrix4 matrix4, boolean drawGrid) {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(matrix4);
        this.drawGrid = drawGrid;
        this.colSize = Gdx.graphics.getHeight() / (int)Constants.VIEWPORT_HEIGHT;
        this.rowSize = Gdx.graphics.getWidth() / (int)Constants.VIEWPORT_WIDTH;

    }

    public void setColor(Color color){
        this.color = color;
    }

    public void drawGrid() {
        if(drawGrid == true) {
            shapeRenderer.setColor(color);
            shapeRenderer.begin(ShapeType.Line);
            //вертикальные линии
            for(int i = 0; i <= (int)Constants.VIEWPORT_WIDTH; i++){
                shapeRenderer.line(i , 0,  i , Constants.VIEWPORT_HEIGHT);
            }
            //горизонатльные линии
            for (int j = 0; j <= (int)Constants.VIEWPORT_HEIGHT; j++) {
                shapeRenderer.line(0, j ,  Constants.VIEWPORT_WIDTH,  j );
            }
            shapeRenderer.end();
        }

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
