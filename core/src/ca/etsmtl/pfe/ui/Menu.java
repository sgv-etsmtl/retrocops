package ca.etsmtl.pfe.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.graphics.Camera;

import ca.etsmtl.pfe.helper.AssetLoader;


public class Menu {

    private Stage menuStage;
    private TextButton switchButton, itemButton, useButton, overwatchButton, optionsButton;
    private ShapeRenderer shapeRenderer;
    private float viewportWidth;
    private float viewportHeight;
    private OrthographicCamera menuCam;

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
        final int X_BOUND = 100;
        final int PADDING = 10;
        final int MARGIN = 20;

        ChangeListener menuButtonsListener = new ChangeListener() {
           @Override
            public void changed(ChangeEvent event, Actor actor) {
               Gdx.app.log("info", "event :  " + event.getTarget());
               Gdx.app.log("info", "Actor :  " + actor);
           }
        };

        switchButton = new TextButton("Switch", testButtonStyle);
        switchButton.pad(10);
        switchButton.setBounds(X_BOUND, 4 * (BUTTON_SIZE + PADDING) + MARGIN, BUTTON_SIZE, BUTTON_SIZE);
        switchButton.addListener(menuButtonsListener);

        itemButton = new TextButton("Item", testButtonStyle);
        itemButton.pad(10);
        itemButton.setBounds(X_BOUND, 3 * (BUTTON_SIZE + PADDING) + MARGIN, BUTTON_SIZE, BUTTON_SIZE);
        itemButton.addListener(menuButtonsListener);

        useButton = new TextButton("USE", testButtonStyle);
        useButton.pad(10);
        useButton.setBounds(X_BOUND, 2 * (BUTTON_SIZE + PADDING) + MARGIN, BUTTON_SIZE, BUTTON_SIZE);
        useButton.addListener(menuButtonsListener);


        overwatchButton = new TextButton("OW", testButtonStyle);
        overwatchButton.pad(10);
        overwatchButton.setBounds(X_BOUND, 1 * (BUTTON_SIZE + PADDING) + MARGIN, BUTTON_SIZE, BUTTON_SIZE);
        overwatchButton.addListener(menuButtonsListener);

        optionsButton = new TextButton("Options", testButtonStyle);
        optionsButton.pad(10);
        optionsButton.setBounds(X_BOUND, 0 * (BUTTON_SIZE + PADDING) + MARGIN, BUTTON_SIZE, BUTTON_SIZE);
        optionsButton.addListener(menuButtonsListener);


    //    VerticalGroup verticalGroup = new VerticalGroup();

        menuStage.addActor(switchButton);
        menuStage.addActor(itemButton);
        menuStage.addActor(useButton);
        menuStage.addActor(overwatchButton);
        menuStage.addActor(optionsButton);

//        verticalGroup.setVisible(true);
//        verticalGroup.setPosition(160,1080);
//        verticalGroup.pad(10);
//        verticalGroup.space(MARGIN);
//        menuStage.addActor(verticalGroup);





        lastPostionAsk = new Vector3(0,0,0);
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
