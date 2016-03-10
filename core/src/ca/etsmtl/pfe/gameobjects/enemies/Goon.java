package ca.etsmtl.pfe.gameobjects.enemies;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;


import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.gameobjects.PlayerCharacter;
import ca.etsmtl.pfe.gameworld.GameWorld;
import ca.etsmtl.pfe.helper.AssetLoader;
import ca.etsmtl.pfe.pathfinding.Node;

public class Goon extends BaseCharacter{


    private static final float AI_SIGHT_RANGE = 6 ;
    private StateMachine<Goon, GoonState> stateMachine;
    private boolean enemyInSight;
    private int nbIdleTurns;
    private ArrayList<Node> lastKnownEnemyPosition;

    public Goon(int positionX, int positionY, GameWorld gameWorld) {
        super(gameWorld);
        stateMachine = new DefaultStateMachine(this, GoonState.CALM);
        setCharacterSprite(new Sprite(AssetLoader.testSprite, 0, 160, gameWorld.DEFAULT_TILE_SIZE, gameWorld.DEFAULT_TILE_SIZE));

        this.nbIdleTurns = 0;
        this.currentActionPoints = ACTION_POINTS_LIMIT;
        this.enemyInSight = false;
        this.isDone = false;
        this.targetList = new ArrayList<BaseCharacter>();
        this.lastKnownEnemyPosition = new ArrayList<Node>();
        setPosition(positionX * gameWorld.DEFAULT_TILE_SIZE, positionY * gameWorld.DEFAULT_TILE_SIZE);
    }

    @Override
    public void update(float delta) {

        if(!this.isDone) {
            if (this.getCurrentActionPoints() >= 0) {
                super.update(delta);
                stateMachine.update();
            }

            if (this.currentActionPoints <= 0) {
                this.setIsDone(true);
            }
            if(this.getCurrentActionPoints() <= 0) {
                this.setIsDone(true);
            }
        }
    }

    @Override
    public void updateTargetList() {
        this.targetList.clear();
        ArrayList<PlayerCharacter> pcs = this.gameWorld.getPlayerCharacters();

        Gdx.app.log("info", "Goon " + this + " is at position " + this.position);

        for (PlayerCharacter pc : pcs) {

           Gdx.app.log("info", "PC is at " + pc.getPosition());


            if (this.getPosition().dst(pc.getPosition().x, pc.getPosition().y) < AI_SIGHT_RANGE * gameWorld.DEFAULT_TILE_SIZE
                    && this.canRaytrace(pc)) {
                this.targetList.add(pc);
            }
        }
    }

    private void updateState() {

        updateTargetList();

        if (this.stateMachine.getCurrentState().equals(GoonState.CALM)
                && this.baseCharacterState == BaseCharacterState.waiting
                && (!this.targetList.isEmpty() || !this.lastKnownEnemyPosition.isEmpty() ) ) {

            this.stateMachine.changeState(GoonState.ALERT);
            Gdx.app.log("info", "Switching into ALERT Mode" + this);
        }
    }



    public void patrol() {
        if(this.baseCharacterState == BaseCharacterState.waiting) {

            int nbOfNeighbours = 0;
            Node currentNode = gameWorld.getGameMap().getMapGraph().getNodeByTileXY(this.getPosition().x / gameWorld.DEFAULT_TILE_SIZE,
                    this.getPosition().y / gameWorld.DEFAULT_TILE_SIZE,
                    this.gameWorld.getGameMap().getNumberOfTileWidth());
            nbOfNeighbours = currentNode.getConnections().size;

            if (nbOfNeighbours > 0) {
                int i = MathUtils.random(0, nbOfNeighbours - 1);
                Node targetNode = currentNode.getConnections().get(i).getToNode();

                gameWorld.changeCharacterTilePosition(currentNode, targetNode, this);

                //    Gdx.app.log("info", "PATROL from x: " + currentNode.getTileX() + " | y: " + currentNode.getTileY());
                //    Gdx.app.log("info", "PATROL to x: " + targetNode.getTileX() + " | y: " + targetNode.getTileY());
            }
            else {
                //stuck or cornered
                this.setBaseCharacterState(BaseCharacterState.overwatch);
            }
        }
    }

    @Override
    public void attack(BaseCharacter target) {
        this.itemCharacter.attack(target);
        super.attack(target);
    }


// AI States, adapted from the exemples on this page https://github.com/libgdx/gdx-ai/wiki/State-Machine
    public enum GoonState implements State<Goon> {

        CALM() {
            @Override
            public void update(Goon goon) {

                goon.updateState();
                if(goon.stateMachine.getCurrentState() == GoonState.CALM) {
                    goon.patrol();
                }

            }
        },

        ALERT() {
            @Override
            public void update(Goon goon) {

                //Gdx.app.log("info", "alert PLACEHOLDER *******" + this);
                Gdx.app.log("info", "ALERTED Goon target list *******" + goon.targetList);

                if (!goon.targetList.isEmpty()) {

                    BaseCharacter chosenTarget = goon.targetList.get(0);
                    for (BaseCharacter target : goon.targetList) {
                        if (chosenTarget != null && target.getCurrentHitPoints() < chosenTarget.getCurrentHitPoints()) {
                            chosenTarget = target;
                        }
                    }
                    Gdx.app.log("info", goon + " is attacking " + chosenTarget);
                    goon.attack(chosenTarget);

                } else if (!goon.lastKnownEnemyPosition.isEmpty()) {

                    //moveToward closest position
                } else {
                    goon.useOverwatch();
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
