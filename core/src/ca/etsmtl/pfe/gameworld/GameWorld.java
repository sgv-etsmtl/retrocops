package ca.etsmtl.pfe.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.gameobjects.PlayerCharacter;
import ca.etsmtl.pfe.gameobjects.enemies.Goon;
import ca.etsmtl.pfe.helper.AssetLoader;
import ca.etsmtl.pfe.ui.Menu;

public class GameWorld {

    private GameMap gameMap;
    private GameRenderer gameRenderer;
    private BaseCharacter testPlayer;
    private Menu menu;
    private ArrayList<BaseCharacter> ennemies;
    private ArrayList<PlayerCharacter> playerCharacters;

    public GameWorld(GameRenderer gameRenderer,Menu menu){
        this.gameRenderer = gameRenderer;
        this.menu = menu;

        //this for debug
        gameMap = new GameMap("maps/testMap/beta2.tmx");
        gameRenderer.setCurrentMap(gameMap);
        testPlayer = new PlayerCharacter();
        testPlayer.setCharacterSprite(new Sprite(AssetLoader.testSprite, 160, 0, 160, 160));
        testPlayer.setPosition(640, 480);
        gameRenderer.addCharacterToDraw(testPlayer);

        ennemies = new ArrayList<BaseCharacter>();
        ennemies.add(new Goon(10, 11));
        ennemies.add(new Goon(7, 7));

        for( BaseCharacter enemy : ennemies) {
            gameRenderer.addCharacterToDraw(enemy);
        }


    }

    public void update(float delta){

        testPlayer.update(delta);

        for (BaseCharacter enemy : ennemies) {
            enemy.update(delta);
        }
    }

    public void translateCamera(float x, float y){
        gameRenderer.tranlateCamera(x, y);
    }

    public void changeCharacterPosition(float screenX, float screenY){
        if(!isPositionPixelInMenu(screenX,screenY)) {
            Vector3 end = getWorldPositionFromScreenPosition(screenX, screenY);
            Vector2 start = testPlayer.getPosition();
            testPlayer.setPathToWalk(gameMap.getPath(start.x, start.y, end.x, end.y));
        }
    }

    public Vector3 getWorldPositionFromScreenPosition(float screenX, float screenY){
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
