package ca.etsmtl.pfe.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.gameobjects.PlayerCharacter;
import ca.etsmtl.pfe.helper.LevelLoader;
import ca.etsmtl.pfe.pathfinding.Node;
import ca.etsmtl.pfe.ui.gamemenu.Menu;

public class GameWorld {

    private GameMap gameMap;
    private GameRenderer gameRenderer;
    private Menu menu;
    private ArrayList<BaseCharacter> ennemies;
    private ArrayList<PlayerCharacter> playerCharacters;
    private boolean changeToPlayerTurnFlag;
    private enum GameWorldState{
        ongoing,lose,win
    }
    private GameWorldState gameWorldState;

    public PlayerCharacter getSelectedCharacter() {
        return selectedCharacter;
    }

    private PlayerCharacter selectedCharacter;
    private int selectedCharacterIndex;
    public final int DEFAULT_TILE_SIZE = 160;
    public boolean turnNeedsInit = true;
    private int nbOfDonePlayers, nbOfDoneEnemies;
    
    public GameWorld(GameRenderer gameRenderer,Menu menu){
        this.gameRenderer = gameRenderer;
        this.menu = menu;
        this.changeToPlayerTurnFlag = true;
        gameMap = new GameMap();
        gameRenderer.setCurrentMap(gameMap);
        ennemies = new ArrayList<BaseCharacter>();
        playerCharacters = new ArrayList<PlayerCharacter>(2);
        gameWorldState = GameWorldState.ongoing;

        //this is for debug
        LevelLoader.loadLever(this, 0);
        selectedCharacterIndex = -1;
    }

    public void update(float delta){

        if(changeToPlayerTurnFlag) {

            if(turnNeedsInit) {
                initPlayerTurn();
            }


            nbOfDonePlayers = 0;
            for(PlayerCharacter pc : this.playerCharacters) {

                pc.update(delta);

                if (pc.getBaseCharacterState() == BaseCharacter.BaseCharacterState.moving) {
                    gameRenderer.lookAtPlayer(pc.getPosition());
                }

                if(pc.isDone() && pc.equals(selectedCharacter)) {
                    nbOfDonePlayers++;
                    changeSelectedCharacter();
                }
            }

            if (nbOfDonePlayers >= playerCharacters.size()) {
                turnNeedsInit = true;
                changeToPlayerTurnFlag = false;
            }

        } else {
            //AI
            if(turnNeedsInit) {
                initEnemyTurn();
            }

            nbOfDoneEnemies = 0;
            for (BaseCharacter enemy : ennemies) {

                enemy.update(delta);

                if(enemy.isDone()) {
                    this.nbOfDoneEnemies++;
                }
            }

            if (this.nbOfDoneEnemies >= ennemies.size()) {
                changeToPlayerTurnFlag = true;
                turnNeedsInit = true;
            }
        }

        postUpdateChecks();

    }

    private void postUpdateChecks() {

        if (this.gameWorldState ==  GameWorldState.ongoing) {
            // check if all ennemies are dead ou any pc is dead
            for (PlayerCharacter pc : this.playerCharacters) {
                if (!pc.isAlive()) {
                    this.gameWorldState = GameWorldState.lose;
                }
            }

            boolean everyEnemyDead = true;
            for (BaseCharacter enemy : this.ennemies) {
                if (enemy.isAlive()) {
                    everyEnemyDead = false;
                }
            }
            if(everyEnemyDead) {
                this.gameWorldState = GameWorldState.win;
            }
        }

        if (this.gameWorldState == GameWorldState.win) {

            triggerWin();

        } else if (this.gameWorldState == GameWorldState.lose) {

            triggerGameOver();
        }
    }

    private void triggerWin() {
        Gdx.app.log("info", "YOU WIN' YOU CRAZY KILLER");

    }

    private void triggerGameOver() {
        Gdx.app.log("info", "GG YOU DEAD");


    }

    public void translateCamera(float x, float y){
        gameRenderer.tranlateCamera(x, y);
    }


    public void initPlayerTurn() {
        nbOfDonePlayers = 0;
        for (PlayerCharacter pc : playerCharacters) {
            pc.setBaseCharacterState(BaseCharacter.BaseCharacterState.waiting);
            pc.setCurrentActionPoints(pc.getACTION_POINTS_LIMIT());
            pc.setIsDone(false);
        }
        this.turnNeedsInit = false;

    }


    public void initEnemyTurn() {
        nbOfDoneEnemies = 0;
        for (BaseCharacter enemy : ennemies) {
            enemy.setBaseCharacterState(BaseCharacter.BaseCharacterState.waiting);
            enemy.setCurrentActionPoints(enemy.getACTION_POINTS_LIMIT());
            enemy.setIsDone(false);
        }
        this.turnNeedsInit = false;
    }

    public void changeCharacterPosition(float screenX, float screenY){
        if(!isPositionPixelInMenu(screenX,screenY) && selectedCharacter != null &&
                selectedCharacter.getBaseCharacterState() == BaseCharacter.BaseCharacterState.waiting) {
            Vector3 end = getWorldPositionFromScreenPosition(screenX, screenY);
            Vector2 start = selectedCharacter.getPosition();
            DefaultGraphPath<Node> path = gameMap.getPath(start.x, start.y, end.x, end.y);
            if(path != null && path.nodes.size > 0){
                Node startNode = path.get(0);
                Node endNode = path.get(path.nodes.size - 1);
                if(!startNode.equals(endNode)) {
                    gameMap.unblockCell(startNode.getTilePixelX(), startNode.getTilePixelY());
                    gameMap.blockCell(endNode.getTilePixelX(), endNode.getTilePixelY());
                    selectedCharacter.setPathToWalk(path);
                }
            }
        }
    }

    public void changeCharacterTilePosition(Node fromNode, Node toNode, BaseCharacter baseCharacter){

            DefaultGraphPath<Node> path = gameMap.getPathFromNodes(fromNode, toNode);
            if(path != null && path.nodes.size > 0){
                Node startNode = path.get(0);
                Node endNode = path.get(path.nodes.size - 1);
                gameMap.unblockCell(startNode.getTilePixelX(), startNode.getTilePixelY());
                gameMap.blockCell(endNode.getTilePixelX(), endNode.getTilePixelY());
                baseCharacter.setPathToWalk(path);
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

    public GameMap getGameMap() {
        return gameMap;
    }

    public void addCharacterPlayer(PlayerCharacter player){

        playerCharacters.add(player);
        gameRenderer.addCharacterToDraw(player);
        gameMap.blockCell(player.getPosition().x, player.getPosition().y);
    }

    public void addEnemy(BaseCharacter enemy){
        ennemies.add(enemy);
        gameRenderer.addCharacterToDraw(enemy);
        gameMap.blockCell(enemy.getPosition().x, enemy.getPosition().y);
    }

    public void clearWorld(){
        selectedCharacter = null;
        selectedCharacterIndex = -1;
        playerCharacters.clear();
        ennemies.clear();
        gameRenderer.deleteAllCharacterToDraw();
        gameRenderer.resetCamera();
    }

    public void changeMap(String newMapFilePath){
        gameMap.loadMap(newMapFilePath);
        gameRenderer.recalculateBoundary();
        gameRenderer.resetCamera();
    }

    public void changeSelectedCharacter(){
        if(playerCharacters != null) {
            if (selectedCharacter == null || selectedCharacter.getBaseCharacterState() == BaseCharacter.BaseCharacterState.waiting) {
                selectedCharacterIndex = (selectedCharacterIndex + 1) % playerCharacters.size();
                selectedCharacter = playerCharacters.get(selectedCharacterIndex);
                //change camera lookAt
                gameRenderer.lookAtPlayer(selectedCharacter.getPosition());
            }
        }
    }

    public ArrayList<PlayerCharacter> getPlayerCharacters() {
        return playerCharacters;
    }
}
