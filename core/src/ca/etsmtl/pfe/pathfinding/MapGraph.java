package ca.etsmtl.pfe.pathfinding;

import com.badlogic.gdx.ai.pfa.indexed.DefaultIndexedGraph;

import com.badlogic.gdx.utils.Array;
public class MapGraph extends DefaultIndexedGraph<Node> {

    public MapGraph(Array<Node> nodes){
        super(nodes);
    }

    public Node getNodeByTileXY(float tileX, float tileY, float mapWidth){
        return nodes.get((int)(mapWidth * tileY + tileX));
    }
}

