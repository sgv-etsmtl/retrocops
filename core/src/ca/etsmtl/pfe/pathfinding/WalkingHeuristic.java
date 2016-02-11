package ca.etsmtl.pfe.pathfinding;


import com.badlogic.gdx.ai.pfa.Heuristic;

public class WalkingHeuristic implements Heuristic<Node> {

    @Override
    public float estimate(Node node, Node endNode) {

        return (float)(Math.pow(endNode.getTileX() - node.getTileX(), 2) +
                       Math.pow(endNode.getTileY() - node.getTileY(), 2));
    }
}
