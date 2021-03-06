package ca.etsmtl.pfe.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.gameobjects.PlayerCharacter;
import ca.etsmtl.pfe.pathfinding.Node;
import ca.etsmtl.pfe.screens.GameScreen;
import ca.etsmtl.pfe.ui.gamemenu.Menu;

public class GameWorld {

    private GameMap gameMap;
    private GameRenderer gameRenderer;
    private Menu menu;
    private ArrayList<BaseCharacter> ennemies;
    private ArrayList<PlayerCharacter> playerCharacters;
    private boolean changeToPlayerTurnFlag;
    private GameWorldState gameWorldState;
    private PlayerCharacter selectedCharacter;
    private int selectedCharacterIndex;
    public final int DEFAULT_TILE_SIZE = 160;
    public boolean turnNeedsInit = true;
    private int nbOfDonePlayers, nbOfDoneEnemies;
    private GameScreen gameScreen;
    public GameLog gameLog;
    private final int HUMAN_MAX_MOVEMENT_IN_TILES = 12;


    public GameWorld(GameRenderer gameRenderer,Menu menu, GameScreen gameScreen){
        this.gameRenderer = gameRenderer;
        this.menu = menu;
        this.gameScreen = gameScreen;
        this.changeToPlayerTurnFlag = true;
        gameMap = new GameMap();
        gameRenderer.setCurrentMap(gameMap);
        ennemies = new ArrayList<BaseCharacter>();
        playerCharacters = new ArrayList<PlayerCharacter>(2);
        this.nbOfDonePlayers = 0;
        gameWorldState = GameWorldState.waitingForAction;
        selectedCharacterIndex = -1;
        this.gameLog = new GameLog();
        this.gameRenderer.setGameLog(gameLog);
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
            if(selectedCharacter != null &&
                    selectedCharacter.getBaseCharacterState() == BaseCharacter.BaseCharacterState.waiting &&
                    gameWorldState == GameWorldState.movingPlayer){
                setGameWorldState(GameWorldState.waitingForAction);
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

        if (this.gameWorldState ==  GameWorldState.waitingForAction) {
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
        this.gameScreen.getCurrentGame().changeScreenToGameOverScreen();
    }

    private void triggerGameOver() {
        Gdx.app.log("info", "GG YOU DEAD");
        this.gameScreen.getCurrentGame().changeScreenToGameOverScreen();

    }

    public void translateCamera(float x, float y){
        gameRenderer.tranlateCamera(x, y);
    }

    public void initPlayerTurn() {
        gameLog.addMessage("YOUR TURN");
        nbOfDonePlayers = 0;
        for (PlayerCharacter pc : playerCharacters) {
            pc.updateTargetList();
            pc.setBaseCharacterState(BaseCharacter.BaseCharacterState.waiting);
            pc.setCurrentActionPoints(pc.getACTION_POINTS_LIMIT());
            pc.setIsDone(false);
        }
        this.turnNeedsInit = false;
        this.gameWorldState = GameWorldState.waitingForAction;

    }


    public void initEnemyTurn() {
        gameLog.addMessage("ENEMY'S TURN");
        nbOfDoneEnemies = 0;
        for (BaseCharacter enemy : ennemies) {
            enemy.updateTargetList();
            enemy.setBaseCharacterState(BaseCharacter.BaseCharacterState.waiting);
            enemy.setCurrentActionPoints(enemy.getACTION_POINTS_LIMIT());
            enemy.setIsDone(false);
        }
        this.turnNeedsInit = false;
        this.gameWorldState = GameWorldState.waitingForAction;
    }


    public void handleTapScreen(float screenX, float screenY){
        switch (gameWorldState){
            case waitingForAction:
                changeCharacterPosition(screenX, screenY);
                break;
            case usingItem:
                useItem(screenX, screenY);
                break;
        }
    }

    public void setLastScreenPositionClick(float screenX, float screenY){
        //for debug info
        gameRenderer.setLastScreenPositionClick(screenX, screenY);
    }

    public PlayerCharacter getSelectedCharacter() {
        return selectedCharacter;
    }

    private void changeCharacterPosition(float screenX, float screenY){
        if(!isPositionPixelInMenu(screenX,screenY) && selectedCharacter != null &&
                selectedCharacter.getBaseCharacterState() == BaseCharacter.BaseCharacterState.waiting) {

            Vector3 end = getWorldPositionFromScreenPosition(screenX, screenY);
            Vector2 start = selectedCharacter.getPosition();
            DefaultGraphPath<Node> path = gameMap.getPath(start.x, start.y, end.x, end.y);

            if(path != null && path.nodes.size > 0 && path.nodes.size <= HUMAN_MAX_MOVEMENT_IN_TILES){
                Node startNode = path.get(0);
                Node endNode = path.get(path.nodes.size - 1);
                if(!startNode.equals(endNode)) {
                    gameMap.unblockCell(startNode.getTilePixelX(), startNode.getTilePixelY());
                    gameMap.blockCell(endNode.getTilePixelX(), endNode.getTilePixelY());
                    selectedCharacter.setPathToWalk(path);
                    //selectedCharacter.unHighlightCharacter();
                }
                setGameWorldState(GameWorldState.movingPlayer);
            }
        }
    }

    private void useItem(float screenX, float screenY){
        Vector3 positionClick = getWorldPositionFromScreenPosition(screenX, screenY);
        int tileXClick = (int) (positionClick.x / gameMap.getMapTilePixelWidth());
        int tileYClick = (int) (positionClick.y / gameMap.getMapTilePixelHeigth());
        int ennemyTileX;
        int ennemyTileY;
        if(selectedCharacter != null) {
            for (BaseCharacter ennemy : selectedCharacter.getTargetList()){
                ennemyTileX = (int) (ennemy.getPosition().x / gameMap.getMapTilePixelWidth());
                ennemyTileY = (int) (ennemy.getPosition().y / gameMap.getMapTilePixelHeigth());
                if(ennemyTileX == tileXClick && ennemyTileY == tileYClick){
                    selectedCharacter.attack(ennemy);
                    if(!ennemy.isAlive()){
                        gameMap.unblockCell(ennemy.getPosition().x, ennemy.getPosition().y);
                        ennemies.remove(ennemy);
                        gameRenderer.removeCharacterToDraw(ennemy);
                    }
                    setGameWorldState(GameWorldState.waitingForAction);
                    menu.resetTextUseButton();
                    updateItemMenuInfo();
                    break;
                }
            }
        }
    }

    public void
    changeCharacterTilePosition(Node fromNode, Node toNode, BaseCharacter baseCharacter){

            DefaultGraphPath<Node> path = gameMap.getPathFromNodes(fromNode, toNode);
            if(path != null && path.nodes.size > 0){

                gameMap.unblockCell(fromNode.getTilePixelX(), fromNode.getTilePixelY());
                gameMap.blockCell(toNode.getTilePixelX(), toNode.getTilePixelY());
                baseCharacter.setPathToWalk(path);
            }
    }

    public Vector3 getWorldPositionFromScreenPosition(float screenX, float screenY){
        return gameRenderer.transformScreenLocationToWorldLocation(screenX, screenY);
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

    public GameWorldState getGameWorldState() {
        return gameWorldState;
    }

    public void setGameWorldState(GameWorldState gameWorldState) {
        this.gameWorldState = gameWorldState;
        if(gameWorldState == GameWorldState.waitingForAction){
            if(selectedCharacter != null) {
                for (BaseCharacter ennemy : selectedCharacter.getTargetList()){
                    ennemy.unHighlightCharacter();
                }
            }
        }
        else if(gameWorldState == GameWorldState.usingItem){
            if(selectedCharacter != null) {
                for (BaseCharacter ennemy : selectedCharacter.getTargetList()){
                    ennemy.highlightCharacterForAttack();
                }
            }
        }
    }

    public void addCharacterPlayer(PlayerCharacter player){
        playerCharacters.add(player);
        //For debug
        gameRenderer.addCharacterToDraw(player);
        gameMap.blockCell(player.getPosition().x, player.getPosition().y);
    }

    public void addEnemy(BaseCharacter enemy){
        ennemies.add(enemy);
        gameRenderer.addCharacterToDraw(enemy);
        gameMap.blockCell(enemy.getPosition().x, enemy.getPosition().y);
    }

    public void clearWorld(){
        gameWorldState = GameWorldState.waitingForAction;
        selectedCharacter = null;
        selectedCharacterIndex = -1;
        playerCharacters.clear();
        ennemies.clear();
        gameRenderer.deleteAllCharacterToDraw();
        gameRenderer.resetCamera();
        changeToPlayerTurnFlag = true;
        turnNeedsInit = true;
        nbOfDoneEnemies = 0;
        nbOfDonePlayers = 0;
        gameLog.clearLog();
    }

    public void changeMap(String newMapFilePath){
        gameMap.loadMap(newMapFilePath);
        gameRenderer.recalculateBoundary();
        gameRenderer.resetCamera();
    }

    public void changeSelectedCharacter(){
        if(playerCharacters != null && gameWorldState == GameWorldState.waitingForAction) {
            if (selectedCharacter == null || selectedCharacter.getBaseCharacterState() == BaseCharacter.BaseCharacterState.waiting) {
                if(selectedCharacter != null) {
                    selectedCharacter.unHighlightCharacter();
                }
                selectedCharacterIndex = (selectedCharacterIndex + 1) % playerCharacters.size();
                selectedCharacter = playerCharacters.get(selectedCharacterIndex);
                //change camera lookAt
                gameRenderer.lookAtPlayer(selectedCharacter.getPosition());
                selectedCharacter.highlightSelectedCharacter();
                updateItemMenuInfo();
            }
        }
    }

    public void updateItemMenuInfo(){
        if(selectedCharacter != null) {
            menu.updateItemMenuInfo(selectedCharacter.getItemCharacter());
        }
    }

    public ArrayList<BaseCharacter> getEnnemies() {
        return ennemies;
    }

    public enum GameWorldState {
        usingItem,movingPlayer, waitingForAction, lose, win   }

    public ArrayList<PlayerCharacter> getPlayerCharacters() {
        return playerCharacters;
    }
}
