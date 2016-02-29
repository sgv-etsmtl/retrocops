package ca.etsmtl.pfe.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;

import java.util.ArrayList;

import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.gameobjects.PlayerCharacter;
import ca.etsmtl.pfe.gameobjects.enemies.Goon;
import ca.etsmtl.pfe.helper.AssetLoader;
import ca.etsmtl.pfe.pathfinding.Node;
import ca.etsmtl.pfe.ui.Menu;

public class GameWorld {

    private GameMap gameMap;
    private GameRenderer gameRenderer;
    private BaseCharacter player1;
    private Menu menu;
    private ArrayList<BaseCharacter> ennemies;
    private ArrayList<PlayerCharacter> playerCharacters;
    private boolean playerTurnIsActive;
    private final int DEFAULT_TILE_SIZE = 160;

    public GameWorld(GameRenderer gameRenderer,Menu menu){
        this.gameRenderer = gameRenderer;
        this.menu = menu;
        this.playerTurnIsActive = true;

        //this for debug
        gameMap = new GameMap("maps/testMap/beta2.tmx");
        gameRenderer.setCurrentMap(gameMap);

        player1 = new PlayerCharacter();
        player1.setCharacterSprite(new Sprite(AssetLoader.testSprite, 160, 0, DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE));
        player1.setPosition(640, 480);

        playerCharacters = new ArrayList<PlayerCharacter>();
        playerCharacters.add((PlayerCharacter) player1);
        //add player 2 here

        for( BaseCharacter player : playerCharacters) {
            gameRenderer.addCharacterToDraw(player);
        }

        gameRenderer.addCharacterToDraw(player1);

        ennemies = new ArrayList<BaseCharacter>();
        ennemies.add(new Goon(10, 11, this.gameMap));
        ennemies.add(new Goon(7, 7, this.gameMap));

        for( BaseCharacter enemy : ennemies) {
            gameRenderer.addCharacterToDraw(enemy);
        }


    }

    public void update(float delta){

        if(playerTurnIsActive) {
            player1.update(delta);
        } else {
            //AI
            for (BaseCharacter enemy : ennemies) {
                enemy.update(delta);
            }
        }
    }

    public void translateCamera(float x, float y){
        gameRenderer.tranlateCamera(x, y);
    }

    public void changeCharacterPosition(float screenX, float screenY){
        if(!isPositionPixelInMenu(screenX,screenY)) {
            Vector3 end = getWorldPositionFromScreenPosition(screenX, screenY);
            Vector2 start = player1.getPosition();

            Gdx.app.log("info", "start vector x: " + start.x + " | y:" + start.y);
            Gdx.app.log("info", "end vector x: " + end.x + " | y:" + end.y);

            Node fromNode = gameMap.getMapGraph().getNodeByTileXY((int) start.x / DEFAULT_TILE_SIZE, (int) start.y  / DEFAULT_TILE_SIZE, gameMap.getMapPixelWidth());
            Node toNode = gameMap.getMapGraph().getNodeByTileXY((int) end.x  / DEFAULT_TILE_SIZE, (int) end.y  / DEFAULT_TILE_SIZE, gameMap.getMapPixelWidth());

            gameMap.getMapGraph().unblockCell(fromNode.getTileX(), fromNode.getTileY(), gameMap.getMapPixelWidth());
            gameMap.getMapGraph().blockCell(toNode.getTileX(), toNode.getTileY(), gameMap.getMapPixelWidth());
            player1.setPathToWalk(gameMap.getPath(start.x, start.y, end.x, end.y));
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
