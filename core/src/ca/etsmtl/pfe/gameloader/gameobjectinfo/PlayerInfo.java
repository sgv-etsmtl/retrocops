package ca.etsmtl.pfe.gameloader.gameobjectinfo;

import com.badlogic.gdx.graphics.g2d.Sprite;

import ca.etsmtl.pfe.gameobjects.PlayerCharacter;
import ca.etsmtl.pfe.helper.AssetLoader;

public class PlayerInfo extends BaseCharacterInfo {

    public PlayerCharacter getPlayerFromInfo() {
        PlayerCharacter player = new PlayerCharacter();
        player.setPosition(positionPixelX, positionPixelY);
        if (debugCharacter) {
            player.setCharacterSprite(new Sprite(AssetLoader.testSprite, spriteScrX, spriteScrY,
                    spriteWidth, spriteHeigth));
        } else {
            //real asset ennemie
        }
        return player;
    }
}