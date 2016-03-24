package ca.etsmtl.pfe.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

import ca.etsmtl.pfe.helper.AssetLoader;
import ca.etsmtl.pfe.retrocops.RetroCops;


/*
   CODE EMPRUNTÉ :
   Les lignes suivantes sont basées une tutoriel
   provenant du site :
      //https://www.youtube.com/watch?v=q2qoiTqGsh8
   J'ai pris la classe présenté dans le tutoriel et
   je l'ai change pour avoir le nom du jeu affichée et avoir un button start que j'ai fait
   en d'autre mots j'ai changer la valeur du string du label et les coordonneé pixel des différentes composante.
   pour le skin je load un fichier que j'ai fait avec les button que j'ai fait.
*/
public class GameOverScreen implements Screen{

    private Stage stage;
    private Table table;
    private TextButton returnToLevelSelect;
    private Label titleLabel;

    private RetroCops currentGame;
    private float width;
    private float heigth;


    public GameOverScreen(float width, float heigth, RetroCops currentGame){
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

        returnToLevelSelect = new TextButton("Level Select", startButtonStyle);
        returnToLevelSelect.pad(10);
        returnToLevelSelect.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                currentGame.changeScrenToLevelSelectScreen();
            }
        });

        Label.LabelStyle gameNameStyle = new Label.LabelStyle();
        gameNameStyle.font = AssetLoader.gameFont;

        titleLabel = new Label("GAME\nOVER\nYEAH!",gameNameStyle);

        table.add(titleLabel);
        table.row();
        table.add(returnToLevelSelect).width(600).height(100);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    public void changeInputToGameOverScreen(){
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

    }
}
/* FIN DU CODE EMPRUNTÉ */