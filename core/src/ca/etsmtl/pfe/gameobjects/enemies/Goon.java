package ca.etsmtl.pfe.gameobjects.enemies;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.gameworld.GameMap;
import ca.etsmtl.pfe.helper.AssetLoader;
import ca.etsmtl.pfe.pathfinding.MapGraph;
import ca.etsmtl.pfe.pathfinding.Node;

public class Goon extends BaseCharacter{


    private StateMachine<Goon, GoonState> stateMachine;
    private GameMap gameMap;
    private boolean enemyInSight;
    private boolean isDone;
    private int nbIdleTurns;
    private ArrayList<BaseCharacter> targetList;
    private ArrayList<Vector2> lastKnownEnemyPosition;

    public Goon(int positionX, int positionY, GameMap gameMap) {
        stateMachine = new DefaultStateMachine(this, GoonState.CALM);
        initializeVariable();
        setCharacterSprite(new Sprite(AssetLoader.testSprite, 0, 160, 160, 160));

        this.nbIdleTurns = 0;
        this.enemyInSight = false;
        this.isDone = false;
        this.targetList = new ArrayList<BaseCharacter>();
        this.lastKnownEnemyPosition = new ArrayList<Vector2>();
        this.gameMap = gameMap;
        setPosition(positionX*160, positionY*160);
    }

    @Override
    public void update(float delta) {
        this.isDone = false;
        stateMachine.update();
    }

    public void patrol(){
        int nbOfNeighbours = 0;
        Node currentNode = gameMap.getMapGraph().getNodeByTileXY(this.getPosition().x, this.getPosition().y + 1, this.gameMap.getMapPixelWidth());
        nbOfNeighbours = currentNode.getConnections().size;

        if (nbOfNeighbours > 0) {
            int i = ThreadLocalRandom.current().nextInt(0, nbOfNeighbours -1);
            Node targetNode = currentNode.getConnections().get(i).getToNode();

            this.setPathToWalk(gameMap.getPath(currentNode.getTilePixelX(), currentNode.getTilePixelY(), targetNode.getTilePixelX(), targetNode.getTilePixelY()g));


            gameMap.getMapGraph().unblockCell(targetNode.getTileX(), targetNode.getTileY(), gameMap.getMapPixelWidth());
            gameMap.getMapGraph().blockCell(targetNode.getTileX(), targetNode.getTileY(), gameMap.getMapPixelWidth());

            Gdx.app.log("info", "PATROL PLACEHOLDER *******" + this);
        }
    }



// AI States, adapted from the exemples on this page https://github.com/libgdx/gdx-ai/wiki/State-Machine
    public enum GoonState implements State<Goon> {

        CALM() {
            @Override
            public void update(Goon goon) {

                goon.patrol();

                if (goon.enemyInSight) {
                    goon.stateMachine.changeState(ALERT);
                }
            }
        },

        ALERT() {
            @Override
            public void update(Goon goon) {

                Gdx.app.log("info", "alert PLACEHOLDER *******" + this);

                if (!goon.targetList.isEmpty()) {
                    //shoot weakest target
                } else if (!goon.lastKnownEnemyPosition.isEmpty()) {

                    //moveToward closest position
                }
            }
        };



        @Override
        public void enter(Goon goon) {
        }

        @Override
        public void exit(Goon goon) {
        }

        @Override
        public boolean onMessage(Goon goon, Telegram telegram) {
            // We don't use messaging in this game
            return false;
        }
    }

}
