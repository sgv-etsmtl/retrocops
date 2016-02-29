package ca.etsmtl.pfe.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.DefaultIndexedGraph;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/*
   CODE EMPRUNTÉ :
   Les lignes suivantes sont basées sur une classe
   provenant du site :
      https://github.com/LetsMakeAnIndieGame/PhysicsShmup/blob/master/core/src/com/mygdx/game/pathfinding/GraphImp.java
   J'ai la classe qui sert à représenter le graph de connections des différentes tuiles du jeu pour l'api de pathfinding de libgdx
   La classe ajoute une méthode pour avoir le noued selon la tuile voulue par rapport à son parent
*/
public class MapGraph extends DefaultIndexedGraph<Node> {

    public MapGraph(Array<Node> nodes){
        super(nodes);
    }

    public Node getNodeByTileXY(float tileX, float tileY, float mapWidth){
        return nodes.get((int)(mapWidth * tileY + tileX));
    }
    /* FIN DU CODE EMPRUNTÉ */

    public void blockCell(float tileX, float tileY,float mapWidth) {
        Node nodeToBlock = getNodeByTileXY(tileX, tileY, mapWidth);

        Array<Connection<Node>> top = getNodeByTileXY(tileX, tileY+1, mapWidth).getConnections();
        Array<Connection<Node>> bottom = getNodeByTileXY(tileX, tileY-1, mapWidth).getConnections();
        Array<Connection<Node>> left = getNodeByTileXY(tileX-1, tileY, mapWidth).getConnections();
        Array<Connection<Node>> right = getNodeByTileXY(tileX+1, tileY, mapWidth).getConnections();

        for (int i = 0; i < 4; i++) {
            if (i < top.size) {
                if (top.get(i).getToNode().equals(nodeToBlock)) {
                    top.removeIndex(i);
                }
            } if (i < bottom.size) {
                if (bottom.get(i).getToNode().equals(nodeToBlock)) {
                    bottom.removeIndex(i);
                }
            } if (i < left.size) {
                if (left.get(i).getToNode().equals(nodeToBlock)) {
                    left.removeIndex(i);
                }
            } if (i < right.size) {
                if (right.get(i).getToNode().equals(nodeToBlock)) {
                    right.removeIndex(i);
                }
            }
        }
    }

    public void unblockCell(float tileX, float tileY, float mapWidth) {
        Node nodeToUnblock = getNodeByTileXY(tileX, tileY, mapWidth);

        getNodeByTileXY(tileX, tileY-1, mapWidth).createConnection(nodeToUnblock);
        getNodeByTileXY(tileX, tileY+1, mapWidth).createConnection(nodeToUnblock);
        getNodeByTileXY(tileX-1, tileY, mapWidth).createConnection(nodeToUnblock);
        getNodeByTileXY(tileX+1, tileY, mapWidth).createConnection(nodeToUnblock);
    }

}

