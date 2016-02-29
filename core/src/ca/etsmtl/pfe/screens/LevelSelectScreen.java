package ca.etsmtl.pfe.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import ca.etsmtl.pfe.retrocops.RetroCops;
import ca.etsmtl.pfe.ui.levelselect.LevelSelectMenu;

public class LevelSelectScreen implements Screen {

    private Game currentGame;
    private float width;
    private float heigth;

    private LevelSelectMenu levelSelectMenu;

    public LevelSelectScreen(float width, float heigth, RetroCops currentGame) {
        this.currentGame = currentGame;
        this.width = width;
        this.heigth = heigth;
        levelSelectMenu = new LevelSelectMenu(width, heigth, currentGame);

    }

    public void changeInputToLevelSelectScreen(){
        Gdx.input.setInputProcessor(levelSelectMenu.getStage());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        levelSelectMenu.draw(delta);
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
