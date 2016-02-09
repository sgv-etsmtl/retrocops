package ca.etsmtl.pfe.pathfinding;


import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.utils.Array;


/*
   CODE EMPRUNTÉ :
   Les lignes suivantes la classe
   provenant du site :
      https://github.com/LetsMakeAnIndieGame/PhysicsShmup/blob/master/core/src/com/mygdx/game/pathfinding/Node.java
   J'ai la classe sert à une tuile dans le graph et ces connection avec les autres tuiles et je l'ai adapté a mon problème
   en lui ajoutant des attribut sur les infos de la tuile.
*/
public class Node implements IndexedNode<Node> {
    private Array<Connection<Node>> connections = new Array<Connection<Node>>();
    private int index;
    private int tileX;
    private int tileY;
    private int tilePixelX;
    private int tilePixelY;

    public Node(){
        this(0,0,0);
    }

    public Node(int index){
        this(index,0,0);
    }

    public Node(int index, int tileX, int tileY){
       this(index,tileX,tileY,0,0);
    }

    public Node(int index, int tileX, int tileY, int mapTilePixelWidth, int mapTilePixelHeigth){
        connections = new Array<Connection<Node>>();
        this.index = index;
        this.tileX = tileX;
        this.tileY = tileY;
        this.tilePixelX = tileX * mapTilePixelWidth;
        this.tilePixelY = tileY * mapTilePixelHeigth;
    }


    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTilePixelX() {
        return tilePixelX;
    }

    public void setTilePixelX(int tilePixelX) {
        this.tilePixelX = tilePixelX;
    }

    public int getTilePixelY() {
        return tilePixelY;
    }

    public void setTilePixelY(int tilePixelY) {
        this.tilePixelY = tilePixelY;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Array<Connection<Node>> getConnections() {
        return connections;
    }

    public void createConnection(Node toNode) {
        connections.add(new DefaultConnection<Node>(this, toNode));
    }

}
/* FIN DU CODE EMPRUNTÉ */
