package ca.etsmtl.pfe.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;

import java.util.ArrayList;

import ca.etsmtl.pfe.gameloader.gameobjectinfo.GoonInfo;
import ca.etsmtl.pfe.gameloader.gameobjectinfo.PlayerInfo;
import ca.etsmtl.pfe.gameloader.mapinfo.GameLevelInfo;
import ca.etsmtl.pfe.gameloader.mapinfo.LevelInfo;
import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.gameobjects.PlayerCharacter;
import ca.etsmtl.pfe.gameobjects.enemies.Goon;
import ca.etsmtl.pfe.helper.AssetLoader;
import ca.etsmtl.pfe.helper.LevelLoader;
import ca.etsmtl.pfe.pathfinding.Node;
import ca.etsmtl.pfe.ui.Menu;

public class GameWorld {

    private GameMap gameMap;
    private GameRenderer gameRenderer;
    private Menu menu;
    private ArrayList<BaseCharacter> ennemies;
    private ArrayList<PlayerCharacter> playerCharacters;
    private boolean playerTurnIsActive = true;
    private PlayerCharacter selectedCharacter;
    private int selectedCharacterIndex;

    public GameWorld(GameRenderer gameRenderer,Menu menu){
        this.gameRenderer = gameRenderer;
        this.menu = menu;
        gameMap = new GameMap();
        gameRenderer.setCurrentMap(gameMap);
        ennemies = new ArrayList<BaseCharacter>();
        playerCharacters = new ArrayList<PlayerCharacter>(2);

        //this is for debug
        LevelLoader.loadLever(this, 0);
        selectedCharacterIndex = 0;
        selectedCharacter = playerCharacters.get(selectedCharacterIndex);
    }

    public void update(float delta){

        if(playerTurnIsActive) {
            if(selectedCharacter != null) {
                selectedCharacter.update(delta);
            }
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
        if(!isPositionPixelInMenu(screenX,screenY) && selectedCharacter != null) {
            Vector3 end = getWorldPositionFromScreenPosition(screenX, screenY);
            Vector2 start = selectedCharacter.getPosition();
            DefaultGraphPath<Node> path = gameMap.getPath(start.x, start.y, end.x, end.y);
            if(path != null && path.nodes.size > 0){
                Node startNode = path.get(0);
                Node endNode = path.get(path.nodes.size - 1);
                gameMap.unblockCell(startNode.getTilePixelX(), startNode.getTilePixelY());
                gameMap.blockCell(endNode.getTilePixelX(), endNode.getTilePixelY());
                selectedCharacter.setPathToWalk(path);
            }
        }
    }

    public Vector3 getWorldPositionFromScreenPosition(float screenX, float screenY){
        return gameRenderer.transformScreenLocationToWorldLocation(screenX,screenY);
    }

    public boolean isPositionPixelInMenu(float screenX, float screenY){
        return menu.isMenuClicked(screenX, screenY);
    }

    public void setCameraZoom(float newZoom){
        gameRenderer.setCameraZoom(newZoom);
    }

    public float getCameraZoom(){
        return gameRenderer.getCameraZoom();
    }

    public void addCharacterPlayer(PlayerCharacter player){
        playerCharacters.add(player);
        gameRenderer.addCharacterToDraw(player);
        gameMap.blockCell(player.getPosition().x, player.getPosition().y);
    }

    public void addEnnemie(BaseCharacter ennemie){
        ennemies.add(ennemie);
        gameRenderer.addCharacterToDraw(ennemie);
        gameMap.blockCell(ennemie.getPosition().x, ennemie.getPosition().y);
    }

    public void clearWorld(){
        playerCharacters.clear();
        ennemies.clear();
        gameRenderer.deleteAllCharacterToDraw();
    }

    public void changeMap(String newMapFilePath){
        gameMap.loadMap(newMapFilePath);
        gameRenderer.recalculateBoundary();
    }

    public void changeSelectedCharacter(){
        selectedCharacterIndex = (selectedCharacterIndex  + 1) % playerCharacters.size();
        selectedCharacter = playerCharacters.get(selectedCharacterIndex);
        //change camera lookAt
    }

}
