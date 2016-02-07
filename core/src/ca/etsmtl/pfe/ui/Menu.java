package ca.etsmtl.pfe.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.graphics.Camera;

import ca.etsmtl.pfe.helper.AssetLoader;


public class Menu {

    private Stage menuStage;
    private TextButton test;
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

        test = new TextButton("test", testButtonStyle);
        test.pad(10);
        test.setBounds(80, 300, 200, 200);
        test.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("info", "test clicked!");
            }
        });
        menuStage.addActor(test);

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
