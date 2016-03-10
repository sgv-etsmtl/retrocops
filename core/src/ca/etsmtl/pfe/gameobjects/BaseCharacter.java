package ca.etsmtl.pfe.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ca.etsmtl.pfe.gameobjects.items.Item;
import ca.etsmtl.pfe.gameobjects.items.Pistol;
import ca.etsmtl.pfe.gameworld.GameWorld;
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

    protected Vector2 position;
    protected Vector2 velocity;
    protected float speed;
    protected int waypoint = 0;
    protected boolean isDone;
    protected GameWorld gameWorld;
    /* FIN DU CODE EMPRUNTÉ */
    protected Item itemCharacter;
    
    public int getACTION_POINTS_LIMIT() {
        return ACTION_POINTS_LIMIT;
    }

    protected final int ACTION_POINTS_LIMIT = 2;
    protected int currentActionPoints = 0;
    protected final int HIT_POINTS_LIMIT = 2;
    protected int currentHitPoints;

    protected boolean isAlive;
    Sprite spriteCharacter;

    protected DefaultGraphPath<Node> pathToWalk;

    protected Node nextPosition;

    protected BaseCharacterState baseCharacterState;
    
    protected boolean isHighlight;

    protected boolean isHighlightForAttack;

    protected Color colorOfHighlightForSelected;
    protected Color colorOfHighlightForAttack;

    protected List<BaseCharacter> targetList;

    public BaseCharacter(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        initializeVariable();
    }


    public BaseCharacter(int positionX, int positionY, GameWorld gameWorld){
        this.gameWorld = gameWorld;
        initializeVariable();
        setPosition(positionX, positionY);
    }

    protected void initializeVariable(){
        position = new Vector2(0,0);
        velocity = new Vector2(0,0);
        isAlive = true;
        speed = 900;
        baseCharacterState = BaseCharacterState.waiting;
        currentHitPoints = HIT_POINTS_LIMIT;
        itemCharacter = new Pistol(50,10,20);
        itemCharacter.setOwner(this);
        colorOfHighlightForSelected = new Color(0,1,0,0.5f);
        colorOfHighlightForAttack = new Color(1,0,0,0.5f);
        targetList = new ArrayList<BaseCharacter>();
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
        baseCharacterState = BaseCharacterState.moving;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public BaseCharacterState getBaseCharacterState() {
        return baseCharacterState;
    }

    public Item getItemCharacter() {
        return itemCharacter;
    }

    public void setItemCharacter(Item itemCharacter) {
        this.itemCharacter = itemCharacter;
        if(itemCharacter != null){
            this.itemCharacter.setOwner(this);
        }
    }

    public void draw(SpriteBatch batch){
        if(spriteCharacter != null && isAlive){
            spriteCharacter.draw(batch);
        }
    }

    public void drawHighlight(ShapeRenderer shapeRenderer){
        if(isHighlight && spriteCharacter != null && isAlive){
            Color currentColor = shapeRenderer.getColor();
            if(isHighlightForAttack){
                shapeRenderer.setColor(colorOfHighlightForAttack);
            }
            else {
                shapeRenderer.setColor(colorOfHighlightForSelected);
            }
            shapeRenderer.setColor(currentColor);
            shapeRenderer.rect(position.x, position.y, spriteCharacter.getWidth(), spriteCharacter.getHeight());
        }
    }

    public List<BaseCharacter> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<BaseCharacter> targetList) {
        this.targetList = targetList;
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
        if(pathToWalk != null && baseCharacterState == BaseCharacterState.moving) {
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
                    } else {
                        baseCharacterState = BaseCharacterState.waiting;
                        this.currentActionPoints--;
                        updateTargetList();
                    }
                }
            }
        } else if (this.getBaseCharacterState() == BaseCharacterState.overwatch) {
            useOverwatch();
            this.baseCharacterState = BaseCharacterState.waiting;
        }

        if(this.getCurrentActionPoints() <= 0) {
            this.setIsDone(true);
        }
    }
    /* FIN DU CODE EMPRUNTÉ */

    public abstract void updateTargetList();

    //Code emprunté et adapté de la 3eme fonction de cette page.
    // http://playtechs.blogspot.ca/2007/03/raytracing-on-grid.html
    public boolean canRaytrace(BaseCharacter targetCharacter)
    {
        int x0 = (int) (this.getPosition().x / this.gameWorld.DEFAULT_TILE_SIZE) ;
        int y0 = (int) (this.getPosition().y / this.gameWorld.DEFAULT_TILE_SIZE) ;
        int x1 = (int) (targetCharacter.getPosition().x / this.gameWorld.DEFAULT_TILE_SIZE) ;
        int y1 = (int) (targetCharacter.getPosition().y / this.gameWorld.DEFAULT_TILE_SIZE) ;

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int x = x0;
        int y = y0;
        int n = 1 + dx + dy;
        int x_inc = (x1 > x0) ? 1 : -1;
        int y_inc = (y1 > y0) ? 1 : -1;
        int error = dx - dy;
        dx *= 2;
        dy *= 2;

        for (; n > 0; --n)
        {
            // TILE IS CROSSED BY RAY; CHECK IF WALL,
            // IF ANY TILE IS WALL: BREAK OUT;
            Gdx.app.log("info", "raytracing on " + x + " : " + y);


            if( this.gameWorld.getGameMap().isCellBlocked(x,y)) {
                Gdx.app.log("info", "wall found at " + x + " : " + y);

                return false;
            }

            if (error > 0)
            {
                x += x_inc;
                error -= dy;
            } else {
                y += y_inc;
                error += dx;
            }
        }
        return true;
    }
    //fin du code emprunté

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
            int halfTile =  + gameWorld.DEFAULT_TILE_SIZE / 2;
            for (Node node : pathToWalk) {
                if (previous != null) {
                    shapeRenderer.line(previous.getTilePixelX() + halfTile, previous.getTilePixelY() + halfTile,
                            node.getTilePixelX() + halfTile, node.getTilePixelY() + halfTile);
                }
                shapeRenderer.circle(node.getTilePixelX() + halfTile, node.getTilePixelY() + halfTile, 10, 10);
                previous = node;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseCharacter that = (BaseCharacter) o;

        return Float.compare(that.speed, speed)==0 && isAlive==that.isAlive && waypoint==that.waypoint
                && isDone == that.isDone && currentActionPoints == that.currentActionPoints
                && currentHitPoints == that.currentHitPoints && isHighlight == that.isHighlight
                && isHighlightForAttack == that.isHighlightForAttack
                && !(itemCharacter != null ? !itemCharacter.equals(that.itemCharacter) : that.itemCharacter != null)
                && !(position != null ? !position.equals(that.position) : that.position != null) &&
                !(velocity != null ? !velocity.equals(that.velocity) : that.velocity != null) &&
                !(spriteCharacter != null ? !spriteCharacter.equals(that.spriteCharacter) : that.spriteCharacter != null)
                && !(pathToWalk != null ? !pathToWalk.equals(that.pathToWalk) : that.pathToWalk != null)
                && !(nextPosition != null ? !nextPosition.equals(that.nextPosition) : that.nextPosition != null)
                && ! (colorOfHighlightForSelected != null ? !colorOfHighlightForSelected.equals(that.colorOfHighlightForSelected) : that.colorOfHighlightForSelected != null)
                && !(colorOfHighlightForAttack != null ? !colorOfHighlightForAttack.equals(that.colorOfHighlightForAttack) : that.colorOfHighlightForAttack != null)
                && baseCharacterState == that.baseCharacterState
                && !(targetList != null ? !targetList.equals(that.targetList) : that.targetList != null);
    }


    public int getCurrentActionPoints() {
        return currentActionPoints;
    }

    public void setCurrentActionPoints(int currentActionPoints) {
        this.currentActionPoints = currentActionPoints;
    }

    public int getCurrentHitPoints() {
        return currentHitPoints;
    }

    public void setCurrentHitPoints(int currentHitPoints) {
        this.currentHitPoints = currentHitPoints;
    }


    public void setBaseCharacterState(BaseCharacterState baseCharacterState) {
        this.baseCharacterState = baseCharacterState;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void useOverwatch() {
        this.baseCharacterState = baseCharacterState.overwatch;
        setCurrentActionPoints(0);
        Gdx.app.log("info", this + "has used overwatch");
    }

    public void characterGetHit(int damagePoint){
        currentHitPoints -= damagePoint;
        if (currentHitPoints <= 0){
            isAlive = false;
        }
    }

    public void reloadItem(){
        if(currentActionPoints > 0 && itemCharacter.isReloadable()){
            if(itemCharacter.reload()){
                this.currentActionPoints--;
            }
        }
    }

    public void highlightSelectedCharacter(){
        isHighlight = true;
        isHighlightForAttack = false;
    }

    public void highlightSelectedCharacter(Color colorOfHighlight){
        this.colorOfHighlightForSelected = colorOfHighlight;
        highlightSelectedCharacter();
    }

    public void highlightCharacterForAttack(){
        isHighlight = true;
        isHighlightForAttack = true;
    }

    public void highlightCharacterForAttack(Color colorOfHighlight){
        this.colorOfHighlightForAttack = colorOfHighlight;
        highlightCharacterForAttack();
    }

    public void unHighlightCharacter(){
        isHighlight = false;
        isHighlightForAttack = false;
    }

    public Node getCurrentNode() {
        //return this.currentNode;
        return this.gameWorld.getGameMap().getMapGraph()
            .getNodeByTileXY(this.getPosition().x / this.gameWorld.DEFAULT_TILE_SIZE,
                    this.getPosition().x / this.gameWorld.DEFAULT_TILE_SIZE,
                    this.gameWorld.getGameMap().getNumberOfTileWidth());
    }

    public void attack(BaseCharacter chosenTarget) {

        this.currentActionPoints = 0;

        if(chosenTarget.currentHitPoints <= 0) {
            chosenTarget.isAlive = false;
        }
        //Gdx.app.log("info", this + " is attacking " + chosenTarget);
        //Gdx.app.log("info", chosenTarget + " has " + chosenTarget.getCurrentHitPoints() + " HP left");
        updateTargetList();
    }

    public enum BaseCharacterState {
        moving,waiting,overwatch
    }

}
