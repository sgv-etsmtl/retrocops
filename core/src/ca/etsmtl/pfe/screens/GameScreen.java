package ca.etsmtl.pfe.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;

import ca.etsmtl.pfe.gameworld.GameRenderer;
import ca.etsmtl.pfe.gameworld.GameWorld;
import ca.etsmtl.pfe.helper.GestureHandler;
import ca.etsmtl.pfe.helper.InputHandler;

public class GameScreen implements Screen {

    private GameWorld gameWorld;
    private GameRenderer gameRenderer;

    public GameScreen(float width, float heigth){
        gameRenderer = new GameRenderer(width,heigth);
        gameWorld = new GameWorld(gameRenderer);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        GestureDetector gestureDetector = new GestureDetector(new GestureHandler(gameWorld));
        inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(new InputHandler(gameWorld));

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        gameWorld.update(delta);
        gameRenderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
