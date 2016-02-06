package ca.etsmtl.pfe.gameobjects;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import ca.etsmtl.pfe.pathfinding.Node;

public class BaseCharacter {

    private Vector2 position;
    private Vector2 velocity;
    private float speed;

    private boolean isAlive;
    Sprite spriteCharacter;

    private DefaultGraphPath<Node> pathToWalk;
    private int waypoint = 0;
    private Node nextPosition;

    public BaseCharacter(){
       initializeVariable();
    }


    public BaseCharacter(int positionX, int positionY){
        initializeVariable();
        setPosition(positionX, positionY);
    }

    private void initializeVariable(){
        position = new Vector2(0,0);
        velocity = new Vector2(0,0);
        isAlive = true;
        speed = 900;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float positionX, float positionY) {
        this.position.x = positionX;
        this.position.y = positionY;
        spriteCharacter.setPosition(positionX,positionY);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocityX, float velocityY) {
        this.velocity.x = velocityX;
        this.velocity.y = velocityY;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public Sprite getCharacterSprit() {
        return spriteCharacter;
    }

    public void setCharacterSprite(Sprite spriteCharacter) {
        this.spriteCharacter = spriteCharacter;
    }

    public void setPathToWalk(DefaultGraphPath<Node> pathToWalk) {
        //there is a way to go there
        if(pathToWalk.getCount() > 0) {
            this.pathToWalk = pathToWalk;
            //first node is the starting node (the first position of the character)
            if (pathToWalk.getCount() > 1) {
                this.waypoint = 1;
            }
            else{
                this.waypoint = 0;
            }
        }
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void draw(SpriteBatch batch){
        if(spriteCharacter != null && isAlive){
            spriteCharacter.draw(batch);
        }
    }

    //https://bitbucket.org/dermetfan/somelibgdxtests/src/8f2d5d953c42c9c1f52ce588a4abfa450995480e/src/net/dermetfan/someLibgdxTests/entities/AISprite.java?at=default&fileviewer=file-view-default
    public void update(float delta){
        if(pathToWalk != null) {
            nextPosition = pathToWalk.get(waypoint);
            float distanceY = nextPosition.getTilePixelY() - position.y;
            float distanceX = nextPosition.getTilePixelX() - position.x;
            if(distanceX != 0 || distanceY != 0) {
                float angle = (float) Math.atan2(distanceY, distanceX);
                velocity.set((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);

                setPosition(position.x + velocity.x * delta, position.y + velocity.y * delta);
                //spriteCharacter.setRotation(angle * MathUtils.radiansToDegrees);

                if (isWaypointReached(delta)) {
                    setPosition(nextPosition.getTilePixelX(), nextPosition.getTilePixelY());
                    if (waypoint + 1 < pathToWalk.getCount()) {
                        waypoint++;
                    }
                }
            }
        }
    }
    //https://www.youtube.com/watch?v=c-X7yvM1kOs
    private boolean isWaypointReached(float delta) {
        return Math.abs(nextPosition.getTilePixelX() - position.x) <= speed  * delta
                && Math.abs(nextPosition.getTilePixelY() - position.y) <= speed  * delta;
    }

    public void drawPathDebug(ShapeRenderer shapeRenderer){
        if(pathToWalk != null) {
            shapeRenderer.setColor(Color.WHITE);
            Node previous = null;
            for (Node node : pathToWalk) {
                if (previous != null) {
                    shapeRenderer.line(previous.getTilePixelX(), previous.getTilePixelY(),
                            node.getTilePixelX(), node.getTilePixelY());
                }
                shapeRenderer.circle(node.getTilePixelX(), node.getTilePixelY(), 10, 10);
                previous = node;
            }
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.line(position.x, position.y,
                    nextPosition.getTilePixelX(), nextPosition.getTilePixelY());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseCharacter that = (BaseCharacter) o;

        return Float.compare(that.speed, speed)==0 && isAlive==that.isAlive && waypoint==that.waypoint
                && !(position != null ? !position.equals(that.position) : that.position != null) &&
                !(velocity != null ? !velocity.equals(that.velocity) : that.velocity != null) &&
                !(spriteCharacter != null ? !spriteCharacter.equals(that.spriteCharacter) : that.spriteCharacter != null)
                && !(pathToWalk != null ? !pathToWalk.equals(that.pathToWalk) : that.pathToWalk != null)
                && !(nextPosition != null ? !nextPosition.equals(that.nextPosition) : that.nextPosition != null);
    }

}
