package ca.etsmtl.pfe.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.graphics.Camera;

import ca.etsmtl.pfe.gameworld.GameWorld;
import ca.etsmtl.pfe.helper.AssetLoader;


public class Menu {

    private Stage menuStage;
    private ImageButton switchButton;
    private TextButton itemButton, useButton, overwatchButton, optionsButton;
    private Table verticalTable;
    private ShapeRenderer shapeRenderer;
    private float viewportWidth;
    private float viewportHeight;
    private OrthographicCamera menuCam;
    private MenuClickListenerHandler menuClickListenerHandler;
    private GameWorld gameWorld;

    private Vector3 lastPostionAsk;

    public Menu(float viewportWidth, float viewportHeight){
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        menuStage = new Stage(new FillViewport(viewportWidth,viewportHeight));
        menuCam = (OrthographicCamera) menuStage.getCamera();
        shapeRenderer = new ShapeRenderer();
        TextButton.TextButtonStyle testButtonStyle = new TextButton.TextButtonStyle();
        //name in the file button.pack
        testButtonStyle.up = AssetLoader.skinButton.getDrawable("button.up");
        testButtonStyle.down = AssetLoader.skinButton.getDrawable("button.down");
        testButtonStyle.pressedOffsetX = 10;
        testButtonStyle.pressedOffsetY = -5;
        testButtonStyle.font = AssetLoader.gameFont;

        //placeholder menu buttons
        final int BUTTON_SIZE = 200;
        final int PADDING = 5;

        ImageButton.ImageButtonStyle switchCharacterStyle = new ImageButton.ImageButtonStyle();
        switchCharacterStyle.imageUp = AssetLoader.skinSwitchCharacter.getDrawable("switchCharacter.up");
        switchCharacterStyle.imageDown = AssetLoader.skinSwitchCharacter.getDrawable("switchCharacter.down");
        switchButton = new ImageButton(switchCharacterStyle);

        menuClickListenerHandler = new MenuClickListenerHandler(switchButton,itemButton,useButton,
                                                                overwatchButton,optionsButton, gameWorld);


        switchButton.addListener(menuClickListenerHandler);

        itemButton = new TextButton("Item", testButtonStyle);
        itemButton.addListener(menuClickListenerHandler);

        useButton = new TextButton("USE", testButtonStyle);
        useButton.addListener(menuClickListenerHandler);


        overwatchButton = new TextButton("OW", testButtonStyle);
        overwatchButton.addListener(menuClickListenerHandler);

        optionsButton = new TextButton("Options", testButtonStyle);
        optionsButton.addListener(menuClickListenerHandler);


        verticalTable = new Table();
        verticalTable.setBounds(30, 0, 300, viewportHeight);

        verticalTable.add(switchButton).width(BUTTON_SIZE).height(BUTTON_SIZE);
        verticalTable.row();
        verticalTable.add(itemButton).width(BUTTON_SIZE).height(BUTTON_SIZE).pad(PADDING);
        verticalTable.row();
        verticalTable.add(useButton).width(BUTTON_SIZE).height(BUTTON_SIZE).pad(PADDING);
        verticalTable.row();
        verticalTable.add(overwatchButton).width(BUTTON_SIZE).height(BUTTON_SIZE).pad(PADDING);
        verticalTable.row();
        verticalTable.add(optionsButton).width(BUTTON_SIZE).height(BUTTON_SIZE);
        switchButton.getImageCell().expand().fill();
        menuStage.addActor(verticalTable);

        lastPostionAsk = new Vector3(0,0,0);
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        menuClickListenerHandler.setGameWorld(gameWorld);
    }

    public Stage getMenuStage() {
        return menuStage;
    }

    public void drawMenu(){

        Camera cam = menuStage.getCamera();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setProjectionMatrix(cam.combined);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect((cam.position.x - viewportWidth / 2), (cam.position.y - viewportHeight / 2), 320, viewportHeight);

        shapeRenderer.end();

        menuStage.act(Gdx.graphics.getDeltaTime());
        menuStage.draw();
    }

    public boolean isMenuClicked(float ScreenX, float ScreenY){
        findWorldCoordonateFromScreenCoordonate(ScreenX, ScreenY);
        return lastPostionAsk.x < menuCam.position.x - viewportWidth/2 + 320;
    }

    private void findWorldCoordonateFromScreenCoordonate(float ScreenX, float ScreenY){
        lastPostionAsk.x = ScreenX;
        lastPostionAsk.y = ScreenY;
        lastPostionAsk.z = 0;
        menuCam.unproject(lastPostionAsk);
    }

    public void dispose(){
        menuStage.dispose();
        shapeRenderer.dispose();
    }
}
