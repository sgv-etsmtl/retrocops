package ca.etsmtl.pfe.gameworld;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameRenderer {


    private OrthographicCamera cam;
    private GameMap currentMap;
    private static float HEIGTH_VIEW = 580;
    private static float WIDTH_VIEW = 980;
    private float camera_Start_Y;
    private float getCamera_Start_X;
    private float viewportWidth;
    private float viewportHeight;


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
    }

    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        currentMap.render(cam);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(1.0f, 0, 0, 1);

        shapeRenderer.rect(rectangle.x, rectangle.y,rectangle.width, rectangle.height);

        shapeRenderer.end();
    }

    public void tranlateCamera(float x, float y) {
        if(cam.position.x + x < getCamera_Start_X - rectangle.width ||
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
