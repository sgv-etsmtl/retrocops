package ca.etsmtl.pfe.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;

import ca.etsmtl.pfe.gameworld.GameRenderer;
import ca.etsmtl.pfe.gameworld.GameWorld;
import ca.etsmtl.pfe.helper.GestureHandler;
import ca.etsmtl.pfe.ui.Menu;

public class GameScreen implements Screen {

    private GameWorld gameWorld;
    private GameRenderer gameRenderer;
    private Menu gameMenu;

    public GameScreen(float width, float heigth){
        gameMenu = new Menu(width,heigth);
        gameRenderer = new GameRenderer(width,heigth);
        gameWorld = new GameWorld(gameRenderer,gameMenu);
        gameRenderer.setMenu(gameMenu);
        gameMenu.setGameWorld(gameWorld);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gameMenu.getMenuStage());
        GestureDetector gestureDetector = new GestureDetector(new GestureHandler(gameWorld));
        inputMultiplexer.addProcessor(gestureDetector);

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
        gameRenderer.dispose();
        gameMenu.dispose();
    }
}
