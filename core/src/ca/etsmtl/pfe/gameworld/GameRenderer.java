package ca.etsmtl.pfe.gameworld;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import ca.etsmtl.pfe.ui.Menu;

public class GameRenderer {

    private Menu menu;
    private OrthographicCamera cam;
    private GameMap currentMap;
    //private static float HEIGTH_VIEW = 580;
    //private static float WIDTH_VIEW = 980;
    private float camera_Start_Y;
    private float getCamera_Start_X;
    private float viewportWidth;
    private float viewportHeight;
    private BitmapFont writeFont;
    private SpriteBatch batcher;
    private Vector3 worldPosition;



    private ShapeRenderer shapeRenderer;
    private Rectangle rectangle;

    public GameRenderer(float viewportWidth, float viewportHeight){
        currentMap = new GameMap();
        cam = new OrthographicCamera();
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        cam.setToOrtho(false, viewportWidth, viewportHeight);
        camera_Start_Y = cam.position.y;
        getCamera_Start_X = cam.position.x;
        rectangle = new Rectangle(0,0,320,viewportHeight);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        writeFont = new BitmapFont(Gdx.files.internal("font/white.fnt"));
        writeFont.getData().setScale(1.2f);
        worldPosition = new Vector3(0,0,0);
    }


    public GameMap getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(GameMap currentMap) {
        this.currentMap = currentMap;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public boolean isPositionPixelIsInMenu(float x, float y){
        transformScreenLocationToWorldLocation(x,y);
        return worldPosition.x < cam.position.x - viewportWidth/2 + 320;
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();

        shapeRenderer.setProjectionMatrix(cam.combined);
        currentMap.render(cam);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(1.0f, 0, 0, 1);

        shapeRenderer.rect(cam.position.x - viewportWidth/2, cam.position.y - viewportHeight/2, rectangle.width, rectangle.height);

        shapeRenderer.setColor(0, 1.0f, 0, 1);
        shapeRenderer.rect(cam.position.x,cam.position.y, 20,20);

        shapeRenderer.setColor(0, 0, 1.0f, 1);
        shapeRenderer.rect(cam.position.x - viewportWidth/2 + 320,cam.position.y, 20,20);

        shapeRenderer.end();

        //draw last clicked cell
        batcher.begin();

        //in the menu
        if(worldPosition.x < cam.position.x - viewportWidth/2 + 320) {
            writeFont.draw(batcher, "Click in menu", 60, 1050);
        }
        else {
            int cellX = (int) (worldPosition.x / 160);
            int cellY = (int) (worldPosition.y / 160);

            writeFont.draw(batcher, "Cell click[" + cellX + "," + cellY + "]", 60, 1050);
        }
        writeFont.draw(batcher, "pos cam " + cam.position.x + ", " + cam.position.y, 60, 800);
        writeFont.draw(batcher, "world click " + worldPosition.x + ", " + worldPosition.y, 60, 600);
        batcher.end();

        menu.drawMenu();
    }

    public void tranlateCamera(float x, float y) {
        if(cam.position.x + x < getCamera_Start_X - rectangle.width ||
                cam.position.x + x > currentMap.getMapWidth() - viewportWidth / 2){
            x = 0;
        }

        if(cam.position.y + y < camera_Start_Y ||
                cam.position.y + y > currentMap.getMapHeigth() - viewportHeight / 2){
            y = 0;
        }
        if(x != 0 || y != 0){
            cam.translate(x, y);
        }

    }

    public Vector3 transformScreenLocationToWorldLocation(float x, float y){
        worldPosition.x = x;
        worldPosition.y = y;
        cam.unproject(worldPosition);
        return worldPosition;
    }

}
