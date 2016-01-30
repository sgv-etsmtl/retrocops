package ca.etsmtl.pfe.gameworld;

//This class is use to store all game object in the world and update them
//after a frame.
public class GameWorld {

    GameRenderer gameRenderer;

    public GameWorld(GameRenderer gameRenderer){
        this.gameRenderer = gameRenderer;

        //this for debug
        GameMap gameMap = new GameMap("beta.tmx");
        gameRenderer.setCurrentMap(gameMap);
    }

    public void update(float delta){

    }

    public void translateCamera(float x, float y){
        gameRenderer.tranlateCamera(x,y);
    }

}
