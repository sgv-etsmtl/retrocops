package ca.etsmtl.pfe.gameworld;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.helper.AssetLoader;
import ca.etsmtl.pfe.ui.Menu;

public class GameRenderer {

    private Menu menu;
    private OrthographicCamera cam;
    private GameMap currentMap;
    private float cameraViewWidth;
    private float cameraViewHeight;
    private BitmapFont writeFont;
    private SpriteBatch batcher;
    private SpriteBatch characterBatcher;
    private Vector3 worldPosition;

    private float leftboundary;
    private float rigthboundary;
    private float upperboundary;
    private float bottomboundary;

    private ShapeRenderer shapeRenderer;
    private Vector3 ajustedCamPos;
    private Vector2 lastScreenPositionClik;

    private Array<BaseCharacter> characterToDraw;

    public GameRenderer(float viewportWidth, float viewportHeight){
        currentMap = new GameMap();
        cam = new OrthographicCamera();
        this.cameraViewWidth = viewportWidth;
        this.cameraViewHeight = viewportHeight;
        cam.setToOrtho(false, viewportWidth, viewportHeight);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        worldPosition = new Vector3(0,0,0);
        ajustedCamPos = new Vector3(0,0,0);
        lastScreenPositionClik = new Vector2(0,0);
        calculateBoundary();
        writeFont = AssetLoader.whiteFont;
        characterToDraw = new Array<BaseCharacter>();
        characterBatcher = new SpriteBatch();
    }

    public void setCurrentMap(GameMap currentMap) {
        this.currentMap = currentMap;
        calculateBoundary();
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void addCharacterToDraw(BaseCharacter baseCharacter){
        characterToDraw.add(baseCharacter);
    }

    public void removeCharacterToDraw(BaseCharacter baseCharacter){
        characterToDraw.removeValue(baseCharacter, false);
    }

    public void removeCharacterToDraw(int index){
        characterToDraw.removeIndex(index);
    }

    public void deleteAllCharacterToDraw(){
        characterToDraw.clear();
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        shapeRenderer.setProjectionMatrix(cam.combined);
        currentMap.render(cam);

        drawCharacter(true);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0, 1.0f, 0, 1);
        shapeRenderer.rect(cam.position.x, cam.position.y, 100 * cam.zoom, 100 * cam.zoom);

        shapeRenderer.end();

        menu.drawMenu();

        drawDebugInfo(true);


    }

    private void drawDebugInfo(boolean drawDebug){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0, 1.0f, 0, 1);
        shapeRenderer.rect(cam.position.x, cam.position.y, 100 * cam.zoom, 100 * cam.zoom);

        shapeRenderer.end();

        //draw last clicked cell
        batcher.begin();

        //in the menu
        if(menu.isMenuClicked(lastScreenPositionClik.x,lastScreenPositionClik.y)) {
            writeFont.draw(batcher, "Click in menu", 1200, 1050);
        }
        else {
            int cellX = (int) (worldPosition.x / 160);
            int cellY = (int) (worldPosition.y / 160);

            writeFont.draw(batcher, "Cell click[" + cellX + "," + cellY + "]", 1200, 1050);
        }
        writeFont.draw(batcher, "pos cam " + cam.position.x + ", " + cam.position.y, 1200, 800);
        writeFont.draw(batcher, "world click " + worldPosition.x + ", " + worldPosition.y, 1200, 600);
        batcher.end();
    }

    private void drawCharacter(boolean drawDebug){
        characterBatcher.setProjectionMatrix(cam.combined);
        characterBatcher.begin();
        for(BaseCharacter character : characterToDraw){
            character.draw(characterBatcher);
        }
        characterBatcher.end();
        if(drawDebug){
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            for(BaseCharacter character : characterToDraw){
                character.drawPathDebug(shapeRenderer);
            }
            shapeRenderer.end();
        }
    }

    public void tranlateCamera(float x, float y) {
        if(cam.position.x + x < leftboundary ||
                cam.position.x + x > rigthboundary){
            x = 0;
        }

        if(cam.position.y + y < bottomboundary ||
                cam.position.y + y > upperboundary){
            y = 0;
        }
        if(x != 0 || y != 0){
            cam.translate(x, y);
            cam.update();
        }

    }

    public Vector3 transformScreenLocationToWorldLocation(float x, float y){
        Vector3 position = new Vector3();
        lastScreenPositionClik.x = x;
        lastScreenPositionClik.y = y;
        worldPosition.x = x;
        worldPosition.y = y;
        cam.unproject(worldPosition);
        position.set(worldPosition);
        return position;
    }

    public void setCameraZoom(float newZoom){
        cameraViewHeight = cam.viewportHeight * newZoom;
        cameraViewWidth = cam.viewportWidth * newZoom;
        cam.zoom = newZoom;
        calculateBoundary();
        ajusttCameraAfterZoomOrLookAt();
    }

    public float getCameraZoom(){
        return cam.zoom;
    }

    public void recalculateBoundary(){
        calculateBoundary();
    }

    private void calculateBoundary(){
        leftboundary = cameraViewWidth / 2 - (320 * cam.zoom);
        //for an unknow reason we must a 60 factor to the width we can view to view the last right tiled
        rigthboundary = currentMap.getMapPixelWidth() - (cameraViewWidth / 2 - 60 * cam.zoom);
        bottomboundary = cameraViewHeight / 2;
        upperboundary = currentMap.getMapPixelHeigth() - (cameraViewHeight / 2);
    }

    private void ajusttCameraAfterZoomOrLookAt(){
        ajustedCamPos.x = cam.position.x;
        ajustedCamPos.y = cam.position.y;
        ajustedCamPos.z = 0;

        if(ajustedCamPos.x < leftboundary){
            ajustedCamPos.x = leftboundary;
        }
        else if(ajustedCamPos.x > rigthboundary){
            ajustedCamPos.x = rigthboundary;
        }

        if(ajustedCamPos.y < bottomboundary){
            ajustedCamPos.y = bottomboundary;
        }
        else if(ajustedCamPos.y > upperboundary){
            ajustedCamPos.y = upperboundary;
        }

        cam.position.set(ajustedCamPos);
    }

    public void dispose(){
        characterBatcher.dispose();
        writeFont.dispose();
        batcher.dispose();
        characterBatcher.dispose();
        shapeRenderer.dispose();
    }

    public void lookAtPlayer(float playerPosX, float playerPosY){
        cam.position.set(playerPosX, playerPosY, 0);
        ajusttCameraAfterZoomOrLookAt();
        cam.update();
    }

    public void lookAtPlayer(Vector2 positionPlayer){
        lookAtPlayer(positionPlayer.x, positionPlayer.y);
    }

    public void resetCamera(){
        cam.position.set(cameraViewWidth / 2, cameraViewHeight / 2,0);
        setCameraZoom(1);
    }

}
