package ca.etsmtl.pfe.gameworld;

import com.badlogic.gdx.math.Vector3;

//This class is use to store all game object in the world and update them
//after a frame.
public class GameWorld {

    GameRenderer gameRenderer;

    public GameWorld(GameRenderer gameRenderer){
        this.gameRenderer = gameRenderer;

        //this for debug
        GameMap gameMap = new GameMap("beta2.tmx");
        gameRenderer.setCurrentMap(gameMap);
    }

    public void update(float delta){

    }

    public void translateCamera(float x, float y){
        gameRenderer.tranlateCamera(x,y);
    }

    public Vector3 getWorldPositioonFromScreenPosition(float x, float y){
        return gameRenderer.transformScreenLocationToWorldLocation(x,y);
    }

    public boolean isPositionPixelInMenu(float x, float y){
        return gameRenderer.isPositionPixelIsInMenu(x,y);
    }

}
