package ca.etsmtl.pfe.helper;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import ca.etsmtl.pfe.gameworld.GameWorld;


/*
    this handler will serve to change the menu contexte
 */
public class InputHandler implements InputProcessor {

    private GameWorld gameWorld;

    public InputHandler(GameWorld gameWorld){
        this.gameWorld = gameWorld;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
