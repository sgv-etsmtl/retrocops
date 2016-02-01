package ca.etsmtl.pfe.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

import com.badlogic.gdx.scenes.scene2d.Stage;


public class Menu {

    private Stage menuStage;
    private TextButton test;
    private TextureAtlas textureMenu;
    private Skin skin;
    private BitmapFont menuFont;

    public Menu(float width, float heigth){
        menuStage = new Stage(new FillViewport(width,heigth));
        textureMenu = new TextureAtlas(Gdx.files.internal("ui/button.pack"));
        skin = new Skin(textureMenu);
        menuFont = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        menuFont.getData().setScale(2);

        TextButton.TextButtonStyle testButtonStyle = new TextButton.TextButtonStyle();
        //name in the file button.pack
        testButtonStyle.up = skin.getDrawable("button.up");
        testButtonStyle.down = skin.getDrawable("button.down");
        testButtonStyle.pressedOffsetX = 10;
        testButtonStyle.pressedOffsetY = -5;
        testButtonStyle.font = menuFont;

        test = new TextButton("test", testButtonStyle);
        test.pad(10);
        test.setBounds(80,300,200,200);
        test.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("info","test clicked!");
            }
        });
        menuStage.addActor(test);
    }

    public Stage getMenuStage() {
        return menuStage;
    }

    public void drawMenu(){
        menuStage.act(Gdx.graphics.getDeltaTime());
        menuStage.draw();
    }
}
