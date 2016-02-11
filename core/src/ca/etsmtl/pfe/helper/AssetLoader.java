package ca.etsmtl.pfe.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


/*
   CODE EMPRUNTÉ :
   Les lignes suivantes sont basées sur une classe
   provenant du site :
      http://www.kilobolt.com/day-6-adding-graphics---welcome-to-the-necropolis.html
   J'ai la classe sert à charger en mémoire toutes les ressources(son, texture, ect.) utilisées durant toute la session de jeu.
   Je mis les ressources que nous avons besoins de charger en mémoire
*/
public class AssetLoader {

    public static TextureAtlas textureAtlasButton;
    public static BitmapFont gameFont, whiteFont;
    public static Skin skinButton;
    public static Texture testSprite;

    public static void load() {
        textureAtlasButton = new TextureAtlas(Gdx.files.internal("ui/button.pack"));
        skinButton = new Skin(textureAtlasButton);
        gameFont = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        gameFont.getData().setScale(2);
        whiteFont = new BitmapFont(Gdx.files.internal("font/white.fnt"));
        whiteFont.getData().setScale(1.2f);
        testSprite = new Texture("assets_temp/sprites_alpha.png");
    }

    public static void dispose() {
        gameFont.dispose();
        textureAtlasButton.dispose();
        skinButton.dispose();
        whiteFont.dispose();
        testSprite.dispose();
    }
}
/* FIN DU CODE EMPRUNTÉ */

