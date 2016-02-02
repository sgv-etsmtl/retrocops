package ca.etsmtl.pfe.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class AssetLoader {

    public static TextureAtlas textureAtlasButton;
    public static BitmapFont gameFont, whiteFont;
    public static Skin skinButton;

    public static void load() {
        textureAtlasButton = new TextureAtlas(Gdx.files.internal("ui/button.pack"));
        skinButton = new Skin(textureAtlasButton);
        gameFont = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        gameFont.getData().setScale(2);
        whiteFont = new BitmapFont(Gdx.files.internal("font/white.fnt"));
        whiteFont.getData().setScale(1.2f);
    }

    public static void dispose() {
        gameFont.dispose();
        textureAtlasButton.dispose();
        skinButton.dispose();
        whiteFont.dispose();
    }
}
