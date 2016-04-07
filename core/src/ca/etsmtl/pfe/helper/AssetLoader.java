package ca.etsmtl.pfe.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;


/*
   CODE EMPRUNTÉ :
   Les lignes suivantes sont basées sur une classe
   provenant du site :
      http://www.kilobolt.com/day-6-adding-graphics---welcome-to-the-necropolis.html
   J'ai la classe sert à charger en mémoire toutes les ressources(son, texture, ect.) utilisées durant toute la session de jeu.
   Je mis les ressources que nous avons besoins de charger en mémoire
*/
public class AssetLoader {

    public static TextureAtlas textureAtlasButton,textureAtlasSwithcCharacterButton;
    public static BitmapFont gameFont, whiteFont, ammoFont;
    public static Skin skinButton,skinSwitchCharacter;
    public static Texture testSprite;
    public static Image pistolUp;
    public static Image pistolDown;
    public static ArrayList<Sound> gunSounds;
    public static int NB_GUN_SOUNDS = 4;
    public static Music gameMusic;


    public static void load() {
        textureAtlasButton = new TextureAtlas(Gdx.files.internal("ui/button.pack"));
        textureAtlasSwithcCharacterButton = new TextureAtlas(Gdx.files.internal("ui/switchCharacter.pack"));
        skinButton = new Skin(textureAtlasButton);
        skinSwitchCharacter = new Skin(textureAtlasSwithcCharacterButton);
        gameFont = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        gameFont.getData().setScale(2);
        whiteFont = new BitmapFont(Gdx.files.internal("font/white.fnt"));
        whiteFont.getData().setScale(1.2f);
        ammoFont = new BitmapFont(Gdx.files.internal("font/ammo.fnt"));
        testSprite = new Texture("assets_temp/sprites_alpha.png");
        pistolUp = new Image(new Texture("ui/pistol_up.png"));
        pistolDown = new Image(new Texture("ui/pistol_down.png"));
        gunSounds = new ArrayList<Sound>(NB_GUN_SOUNDS);
        gunSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/sfx/pistol/pistol1.ogg")));
        gunSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/sfx/pistol/pistol2.ogg")));
        gunSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/sfx/pistol/pistol3.ogg")));
        gunSounds.add(Gdx.audio.newSound(Gdx.files.internal("audio/sfx/pistol/pistol4.ogg")));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music_game.mp3"));
    }

    public static Sound getGunSoundRandom(){
        return gunSounds.get(MathUtils.random(0, NB_GUN_SOUNDS - 1));
    }

    public static void dispose() {
        gameFont.dispose();
        textureAtlasButton.dispose();
        textureAtlasSwithcCharacterButton.dispose();
        skinButton.dispose();
        skinSwitchCharacter.dispose();
        whiteFont.dispose();
        testSprite.dispose();
        ammoFont.dispose();
        for(int i = 0; i < gunSounds.size();i++){
            gunSounds.get(i).dispose();
        }
    }
}
/* FIN DU CODE EMPRUNTÉ */

