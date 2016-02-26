package ca.etsmtl.pfe.gameobjects;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import ca.etsmtl.pfe.pathfinding.Node;

public abstract class BaseCharacter {

    /*
    CODE EMPRUNTÉ :
       Les lignes suivantes sont basées sur la classe
       provenant du site :
      https://bitbucket.org/dermetfan/somelibgdxtests/src/8f2d5d953c42c9c1f52ce588a4abfa450995480e/src/net/dermetfan/someLibgdxTests/entities/AISprite.java?at=default&fileviewer=file-view-default
      J'ai pris les variable suivant de la clasee du site web pour les utilisées dans la méthode update
      et isWaypointReached qui sont aussi pris tiré du même site web
    */

    private Vector2 position;
    private Vector2 velocity;
    private float speed;
    private int waypoint = 0;
    /* FIN DU CODE EMPRUNTÉ */

    private boolean isAlive;
    Sprite spriteCharacter;

    private DefaultGraphPath<Node> pathToWalk;

    private Node nextPosition;

    BaseCharacterStat stat;

    public BaseCharacter(){
       initializeVariable();
    }


    public BaseCharacter(int positionX, int positionY){
        initializeVariable();
        setPosition(positionX, positionY);
    }

    protected void initializeVariable(){
        position = new Vector2(0,0);
        velocity = new Vector2(0,0);
        isAlive = true;
        speed = 900;
        stat = BaseCharacterStat.waiting;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float positionX, float positionY) {
        this.position.x = positionX;
        this.position.y = positionY;
        if(spriteCharacter != null) {
            spriteCharacter.setPosition(positionX, positionY);
        }
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
        this.spriteCharacter.setPosition(position.x, position.y);
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
        stat = BaseCharacterStat.moving;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public BaseCharacterStat getStat() {
        return stat;
    }

    public void draw(SpriteBatch batch){
        if(spriteCharacter != null && isAlive){
            spriteCharacter.draw(batch);
        }
    }

    /*
    CODE EMPRUNTÉ :
       Les lignes suivantes sont basées sur la classe
       provenant du site :
      https://bitbucket.org/dermetfan/somelibgdxtests/src/8f2d5d953c42c9c1f52ce588a4abfa450995480e/src/net/dermetfan/someLibgdxTests/entities/AISprite.java?at=default&fileviewer=file-view-default
      J'ai pris la méthode update qui calcule la prochaine position du personnage entre sa position actuel et la position qu'il doit atteindre
      dépendement du nombre de seconde qui se sont écoulé depuis le dernier frame.
      Je l'ai modifier pour utiliser une liste de noeud (fait par le path finding) au lieu du liste de vecteur
    */
    public void update(float delta){
        if(pathToWalk != null && stat == BaseCharacterStat.moving) {
            nextPosition = pathToWalk.get(waypoint);
            float distanceY = nextPosition.getTilePixelY() - position.y;
            float distanceX = nextPosition.getTilePixelX() - position.x;
            if(distanceX != 0 || distanceY != 0) {
                float angle = (float) Math.atan2(distanceY, distanceX);
                velocity.set((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);

                setPosition(position.x + velocity.x * delta, position.y + velocity.y * delta);

                if (isWaypointReached(delta)) {
                    setPosition(nextPosition.getTilePixelX(), nextPosition.getTilePixelY());
                    if (waypoint + 1 < pathToWalk.getCount()) {
                        waypoint++;
                    }
                    else{
                        stat = BaseCharacterStat.waiting;
                    }
                }
            }
        }
    }
    /* FIN DU CODE EMPRUNTÉ */

    /*
    CODE EMPRUNTÉ :
       Les lignes suivantes sont basées sur la classe
       provenant du site :
      https://bitbucket.org/dermetfan/somelibgdxtests/src/db75d09d7c23a714da4d5847b3bcb2493de160fc/src/net/dermetfan/someLibgdxTests/entities/AISprite.java?at=default&fileviewer=file-view-default
      J'ai pris la méthode isWaypointReached qui décide si la position actuel du personnage devrait être la position à atteindre
    */
    private boolean isWaypointReached(float delta) {
        return Math.abs(nextPosition.getTilePixelX() - position.x) <= speed  * delta
                && Math.abs(nextPosition.getTilePixelY() - position.y) <= speed  * delta;
    }
    /* FIN DU CODE EMPRUNTÉ */

    /*
    CODE EMPRUNTÉ :
       Les lignes suivantes sont basées sur la classe
       provenant du site :
      https://bitbucket.org/dermetfan/somelibgdxtests/src/db75d09d7c23a714da4d5847b3bcb2493de160fc/src/net/dermetfan/someLibgdxTests/screens/WaypointsTutorial.java?at=default&fileviewer=file-view-default
      J'ai pris un bout de la méthode render pour être capable de dessiner le chemin parcouru par le personnage.
      On dessine des cercles les différents points à atteindre et on dessine des lignes entre chaque point.
      On dessine une ligne noire entre les deux points que le personnage parcoure à ce moment-là
    */

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
    /* FIN DU CODE EMPRUNTÉ */
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
                && !(nextPosition != null ? !nextPosition.equals(that.nextPosition) : that.nextPosition != null) &&
                stat == that.stat;
    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (velocity != null ? velocity.hashCode() : 0);
        result = 31 * result + (speed != +0.0f ? Float.floatToIntBits(speed) : 0);
        result = 31 * result + waypoint;
        result = 31 * result + (isAlive ? 1 : 0);
        result = 31 * result + (spriteCharacter != null ? spriteCharacter.hashCode() : 0);
        result = 31 * result + (pathToWalk != null ? pathToWalk.hashCode() : 0);
        result = 31 * result + (nextPosition != null ? nextPosition.hashCode() : 0);
        result = 31 * result + (stat != null ? stat.hashCode() : 0);
        return result;
    }

    public enum BaseCharacterStat{
        moving,waiting
    }

}
