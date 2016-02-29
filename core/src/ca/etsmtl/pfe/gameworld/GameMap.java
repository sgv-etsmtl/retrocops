package ca.etsmtl.pfe.gameworld;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import ca.etsmtl.pfe.pathfinding.MapGraph;
import ca.etsmtl.pfe.pathfinding.MapGraphGenerator;
import ca.etsmtl.pfe.pathfinding.Node;
import ca.etsmtl.pfe.pathfinding.WalkingHeuristic;

public class GameMap {

    private String mapFilePath;
    private OrthogonalTiledMapRenderer tileMapRenderer;
    private TiledMap currentMap;
    private TmxMapLoader loader;
    private float mapPixelHeigth;
    private float mapPixelWidth;
    private float maptilePixelHeigth;
    private float mapTilePixelWidth;

    public MapGraph getMapGraph() {
        return mapGraph;
    }

    private MapGraph mapGraph;
    private MapGraphGenerator mapGraphGenerator;
    private int numberOfTileWidth;
    private int numberOfTileHeigth;

    private TiledMapTileLayer colisionLayer;
    private TiledMapTileLayer groundLayer;

    private IndexedAStarPathFinder<Node> pathFinder;
    private WalkingHeuristic walkingHeuristic;

    public GameMap(){
        initializeVariable();
    }

    public GameMap(String mapFilePath){
        initializeVariable();
        loadMap(mapFilePath);
    }

    private void initializeVariable(){
        loader = new TmxMapLoader();
        tileMapRenderer = new OrthogonalTiledMapRenderer(null);
        mapGraphGenerator = new MapGraphGenerator();
        walkingHeuristic = new WalkingHeuristic();
    }

    public void loadMap(String mapFilePath){
        this.mapFilePath = mapFilePath;
        currentMap = loader.load(this.mapFilePath);
        tileMapRenderer.setMap(currentMap);
        if(currentMap != null) {
            MapProperties prop = currentMap.getProperties();

            numberOfTileWidth = prop.get("width", Integer.class);
            numberOfTileHeigth = prop.get("height", Integer.class);
            mapTilePixelWidth = prop.get("tilewidth", Integer.class);
            maptilePixelHeigth = prop.get("tileheight", Integer.class);
            mapPixelHeigth = numberOfTileHeigth * maptilePixelHeigth;
            mapPixelWidth = numberOfTileHeigth * mapTilePixelWidth;
            colisionLayer = (TiledMapTileLayer)currentMap.getLayers().get("collision");
            groundLayer = (TiledMapTileLayer)currentMap.getLayers().get("ground");
            if(colisionLayer != null){
                colisionLayer.setVisible(false);
            }
        }
        mapGraph = mapGraphGenerator.generateGraph(this);
        pathFinder = new IndexedAStarPathFinder<Node>(mapGraph);
    }

    public void render(OrthographicCamera view){
        tileMapRenderer.setView(view);
        if(tileMapRenderer.getMap() != null) {
            tileMapRenderer.render();
        }

    }

    public float getMapPixelHeigth() {
        return mapPixelHeigth;
    }

    public float getMapPixelWidth() {
        return mapPixelWidth;
    }

    public float getMapTilePixelHeigth() {
        return maptilePixelHeigth;
    }

    public float getMapTilePixelWidth() {
        return mapTilePixelWidth;
    }

    public int getNumberOfTileWidth() {
        return numberOfTileWidth;
    }

    public int getNumberOfTileHeigth() {
        return numberOfTileHeigth;
    }

    public TiledMapTileLayer getLayer(String name){
        if(currentMap == null)
            return null;
        return (TiledMapTileLayer)currentMap.getLayers().get(name);
    }

    public boolean isCellBlocked(int tileX, int tileY){
        return groundLayer.getCell(tileX,tileY) == null ||
                colisionLayer != null && colisionLayer.getCell(tileX,tileY) != null;
    }

    public DefaultGraphPath<Node> getPath(float tilePixelStartX, float tilePixelStartY, float tilePixelEndX, float tilePixelEndY){
        DefaultGraphPath<Node> path = new DefaultGraphPath<Node>();

        int tileStartX = (int)(tilePixelStartX / mapTilePixelWidth);
        int tileStartY = (int)(tilePixelStartY / maptilePixelHeigth);
        int tileEndX = (int)(tilePixelEndX / mapTilePixelWidth);
        int tileEndY = (int)(tilePixelEndY / maptilePixelHeigth);

        pathFinder.searchNodePath(mapGraph.getNodeByTileXY(tileStartX, tileStartY, numberOfTileWidth),
                mapGraph.getNodeByTileXY(tileEndX, tileEndY, numberOfTileWidth),walkingHeuristic,path);

        return path;
    }

    public DefaultGraphPath<Node> getPathFromNodes(Node fromNode, Node toNode){
        DefaultGraphPath<Node> path = new DefaultGraphPath<Node>();

        pathFinder.searchNodePath(fromNode, toNode, walkingHeuristic,path);

        return path;
    }

    public void dispose(){
        tileMapRenderer.dispose();
        currentMap.dispose();
    }

    public void blockCell(float tilePixelX, float tilePixelY){

        int tileX = (int)(tilePixelX / mapTilePixelWidth);
        int tileY = (int)(tilePixelY / maptilePixelHeigth);

        mapGraph.blockCell(tileX, tileY, numberOfTileWidth);
    }

    public void unblockCell(float tilePixelX, float tilePixelY){

        int tileX = (int)(tilePixelX / mapTilePixelWidth);
        int tileY = (int)(tilePixelY / maptilePixelHeigth);

        mapGraph.unblockCell(tileX, tileY, numberOfTileWidth);
    }

}
