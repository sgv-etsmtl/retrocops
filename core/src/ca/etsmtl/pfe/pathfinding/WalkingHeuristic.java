package ca.etsmtl.pfe.pathfinding;


import com.badlogic.gdx.ai.pfa.Heuristic;

public class WalkingHeuristic implements Heuristic<Node> {

    @Override
    public float estimate(Node startNode, Node endNode) {

        return (float)(Math.pow(endNode.getTileX() - startNode.getTileX(), 2) +
                       Math.pow(endNode.getTileY() - startNode.getTileY(), 2));
    }
}
