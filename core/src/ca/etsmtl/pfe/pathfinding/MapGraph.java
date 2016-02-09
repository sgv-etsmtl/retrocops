package ca.etsmtl.pfe.pathfinding;

import com.badlogic.gdx.ai.pfa.indexed.DefaultIndexedGraph;

import com.badlogic.gdx.utils.Array;

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
}
/* FIN DU CODE EMPRUNTÉ */
