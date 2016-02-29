package ca.etsmtl.pfe.gameloader.gameobjectinfo;

import java.io.Serializable;


public class BaseCharacterInfo implements Serializable {

    protected int positionPixelX;
    protected int positionPixelY;
    protected int spriteScrX;
    protected int spriteScrY;
    protected int spriteWidth;
    protected int spriteHeigth;
    protected boolean debugCharacter;

    public BaseCharacterInfo() {
    }

    public BaseCharacterInfo(int positionPixelX, int positionPixelY, int spriteScrX, int spriteScrY,
                             int spriteWidth, int spriteHeigth, boolean debugCharacter) {
        this.positionPixelX = positionPixelX;
        this.positionPixelY = positionPixelY;
        this.spriteScrX = spriteScrX;
        this.spriteScrY = spriteScrY;
        this.spriteWidth = spriteWidth;
        this.spriteHeigth = spriteHeigth;
        this.debugCharacter = debugCharacter;
    }

    public int getPositionPixelX() {
        return positionPixelX;
    }

    public void setPositionPixelX(int positionPixelX) {
        this.positionPixelX = positionPixelX;
    }

    public int getPositionPixelY() {
        return positionPixelY;
    }

    public void setPositionPixelY(int positionPixelY) {
        this.positionPixelY = positionPixelY;
    }

    public int getSpriteScrX() {
        return spriteScrX;
    }

    public void setSpriteScrX(int spriteScrX) {
        this.spriteScrX = spriteScrX;
    }

    public int getSpriteScrY() {
        return spriteScrY;
    }

    public void setSpriteScrY(int spriteScrY) {
        this.spriteScrY = spriteScrY;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeigth() {
        return spriteHeigth;
    }

    public void setSpriteHeigth(int spriteHeigth) {
        this.spriteHeigth = spriteHeigth;
    }

    public boolean isDebugCharacter() {
        return debugCharacter;
    }

    public void setDebugCharacter(boolean debugCharacter) {
        this.debugCharacter = debugCharacter;
    }

}
