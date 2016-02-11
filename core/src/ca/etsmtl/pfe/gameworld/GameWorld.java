package ca.etsmtl.pfe.gameworld;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.helper.AssetLoader;
import ca.etsmtl.pfe.ui.Menu;

public class GameWorld {

    private GameMap gameMap;
    private GameRenderer gameRenderer;
    private BaseCharacter test;
    private Menu menu;

    public GameWorld(GameRenderer gameRenderer,Menu menu){
        this.gameRenderer = gameRenderer;
        this.menu = menu;
        //this for debug
        gameMap = new GameMap("maps/testMap/beta2.tmx");
        gameRenderer.setCurrentMap(gameMap);
        test = new BaseCharacter();
        test.setCharacterSprite(new Sprite(AssetLoader.testSprite, 160, 0, 160, 160));
        test.setPosition(640,480);
        gameRenderer.addCharacterToDraw(test);


    }

    public void update(float delta){
        test.update(delta);
    }

    public void translateCamera(float x, float y){
        gameRenderer.tranlateCamera(x, y);
    }

    public void changeCharacterPosition(float screenX, float screenY){
        if(!isPositionPixelInMenu(screenX,screenY)) {
            Vector3 end = getWorldPositioonFromScreenPosition(screenX, screenY);
            Vector2 start = test.getPosition();
            test.setPathToWalk(gameMap.getPath(start.x, start.y, end.x, end.y));
        }
    }

    public Vector3 getWorldPositioonFromScreenPosition(float screenX, float screenY){
        return gameRenderer.transformScreenLocationToWorldLocation(screenX,screenY);
    }

    public boolean isPositionPixelInMenu(float screenX, float screenY){
        return menu.isMenuClicked(screenX,screenY);
    }

    public void setCameraZoom(float newZoom){
        gameRenderer.setCameraZoom(newZoom);
    }

    public float getCameraZoom(){
        return gameRenderer.getCameraZoom();
    }

}
