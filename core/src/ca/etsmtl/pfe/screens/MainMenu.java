package ca.etsmtl.pfe.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

import ca.etsmtl.pfe.helper.AssetLoader;


//https://www.youtube.com/watch?v=q2qoiTqGsh8
public class MainMenu implements Screen{

    private Stage stage;
    private Table table;
    private TextButton startButton;
    private Label gameName;

    private Game currentGame;
    private float width;
    private float heigth;


    public MainMenu(float width, float heigth, Game currentGame){
        this.width = width;
        this.heigth = heigth;
        this.currentGame = currentGame;
    }



    @Override
    public void show() {

        stage = new Stage(new FillViewport(width,heigth));
        table = new Table();

        table.setBounds(0,0,stage.getWidth(),stage.getHeight());


        TextButton.TextButtonStyle startButtonStyle = new TextButton.TextButtonStyle();
        //name in the file button.pack
        startButtonStyle.up = AssetLoader.skinButton.getDrawable("button.up");
        startButtonStyle.down = AssetLoader.skinButton.getDrawable("button.down");
        startButtonStyle.pressedOffsetX = 10;
        startButtonStyle.pressedOffsetY = -5;
        startButtonStyle.font = AssetLoader.gameFont;

        startButton = new TextButton("Start", startButtonStyle);
        startButton.pad(10);
        startButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                currentGame.setScreen(new GameScreen(width,heigth));
            }
        });

        Label.LabelStyle gameNameStyle = new Label.LabelStyle();
        gameNameStyle.font = AssetLoader.gameFont;

        gameName = new Label("RetroCops",gameNameStyle);

        table.add(gameName);
        table.row();
        table.add(startButton).width(600).height(100);
        table.debug();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
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
        stage.dispose();
    }
}
