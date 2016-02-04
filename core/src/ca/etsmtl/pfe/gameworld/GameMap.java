package ca.etsmtl.pfe.gameworld;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameMap {

    private String mapFilePath;
    private OrthogonalTiledMapRenderer tileMapRenderer;
    private TiledMap currentMap;
    private TmxMapLoader loader;
    private float mapHeigth;
    private float mapWidth;

    public GameMap(){
        loader = new TmxMapLoader();
    }

    public GameMap(String mapFilePath){
        loader = new TmxMapLoader();
        tileMapRenderer = new OrthogonalTiledMapRenderer(null);
        loadMap(mapFilePath);
    }

    public void loadMap(String mapFilePath){
        this.mapFilePath = mapFilePath;
        currentMap = loader.load(this.mapFilePath);
        tileMapRenderer.setMap(currentMap);
        if(currentMap != null) {
            MapProperties prop = currentMap.getProperties();

            int numberOfTiltedWidth = prop.get("width", Integer.class);
            int numberOfTiltedHeight = prop.get("height", Integer.class);
            int tileWidth = prop.get("tilewidth", Integer.class);
            int tileHeight = prop.get("tileheight", Integer.class);
            mapHeigth = numberOfTiltedHeight * tileHeight;
            mapWidth = numberOfTiltedWidth * tileWidth;
        }
    }

    public void render(OrthographicCamera view){
        tileMapRenderer.setView(view);
        if(tileMapRenderer.getMap() != null) {
            tileMapRenderer.render();
        }

    }

    public String getMapFilename() {
        return mapFilePath;
    }

    public float getMapHeigth() {
        return mapHeigth;
    }

    public float getMapWidth() {
        return mapWidth;
    }
}
