package ca.etsmtl.pfe.helper;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import ca.etsmtl.pfe.gameworld.GameWorld;

/*
    this will be use to move the camera
 */

public class GestureHandler implements GestureDetector.GestureListener {
    private GameWorld gameWorld;
    private Vector2 dragFirstPos;

    public GestureHandler(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        dragFirstPos = new Vector2();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        dragFirstPos.x = x;
        dragFirstPos.y = y;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if(!gameWorld.isPositionPixelInMenu(dragFirstPos.x,dragFirstPos.y)) {
            gameWorld.translateCamera(-deltaX, deltaY);
            return true;
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
