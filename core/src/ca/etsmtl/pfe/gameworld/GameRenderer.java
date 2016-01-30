package ca.etsmtl.pfe.gameworld;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class GameRenderer {

    private OrthographicCamera cam;
    private GameMap currentMap;
    private static float HEIGTH_VIEW = 580;
    private static float WIDTH_VIEW = 980;
    private float camera_Start_Y;
    private float getCamera_Start_X;

    public GameRenderer(float viewportWidth, float viewportHeight){
        currentMap = new GameMap();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, viewportWidth, viewportHeight);
        camera_Start_Y = cam.position.y;
        getCamera_Start_X = cam.position.x;
    }

    public void render(float delta){
        cam.update();
        currentMap.render(cam);
    }

    public void tranlateCamera(float x, float y) {
        if(cam.position.x + x < getCamera_Start_X ||
                cam.position.x + x > currentMap.getMapWidth() - WIDTH_VIEW){
            x = 0;
        }

        if(cam.position.y + y < camera_Start_Y ||
                cam.position.y + y > currentMap.getMapHeigth() - HEIGTH_VIEW){
            y = 0;
        }
        if(x != 0 || y != 0){
            cam.translate(x, y);
        }

    }

    public GameMap getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(GameMap currentMap) {
        this.currentMap = currentMap;
    }
}
