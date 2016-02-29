package ca.etsmtl.pfe.gameloader.gameobjectinfo;

import com.badlogic.gdx.graphics.g2d.Sprite;

import ca.etsmtl.pfe.gameobjects.enemies.Goon;
import ca.etsmtl.pfe.gameworld.GameWorld;
import ca.etsmtl.pfe.helper.AssetLoader;

public class GoonInfo extends BaseCharacterInfo {

    public Goon getGoonFromInfo(GameWorld gameWorld){
        Goon enemy = new Goon(positionPixelX / gameWorld.DEFAULT_TILE_SIZE, positionPixelY / gameWorld.DEFAULT_TILE_SIZE, gameWorld);

        if(debugCharacter) {
            enemy.setCharacterSprite(new Sprite(AssetLoader.testSprite, spriteScrX, spriteScrY,
                    spriteWidth, spriteHeigth));
        }
        else{
            //real asset ennemie
        }
        return enemy;
    }
}
