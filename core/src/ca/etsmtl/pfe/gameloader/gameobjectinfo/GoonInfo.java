package ca.etsmtl.pfe.gameloader.gameobjectinfo;

import com.badlogic.gdx.graphics.g2d.Sprite;

import ca.etsmtl.pfe.gameobjects.enemies.Goon;
import ca.etsmtl.pfe.helper.AssetLoader;

public class GoonInfo extends BaseCharacterInfo {

    public Goon getGoonFromInfo(){
        Goon ennemie = new Goon(positionPixelX/160,positionPixelY/160);

        if(debugCharacter) {
            ennemie.setCharacterSprite(new Sprite(AssetLoader.testSprite, spriteScrX, spriteScrY,
                    spriteWidth, spriteHeigth));
        }
        else{
            //real asset ennemie
        }
        return ennemie;
    }
}
