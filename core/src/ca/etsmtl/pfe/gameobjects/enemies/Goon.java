package ca.etsmtl.pfe.gameobjects.enemies;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import ca.etsmtl.pfe.gameobjects.BaseCharacter;
import ca.etsmtl.pfe.helper.AssetLoader;

public class Goon extends BaseCharacter{


    private StateMachine<Goon, GoonState> stateMachine;
    private boolean enemyInSight;
    private int nbIdleTurns;
    private ArrayList<BaseCharacter> targetList;
    private ArrayList<Vector2> lastKnownEnemyPosition;

    public Goon(int positionX, int positionY) {
        stateMachine = new DefaultStateMachine(this, GoonState.CALM);
        initializeVariable();
        setCharacterSprite(new Sprite(AssetLoader.testSprite, 0, 160, 160, 160));

        this.nbIdleTurns = 0;
        this.enemyInSight = false;
        this.targetList = new ArrayList<BaseCharacter>();
        this.lastKnownEnemyPosition = new ArrayList<Vector2>();
        setPosition(positionX*160, positionY*160);
    }

    @Override
    public void update(float delta) {
        stateMachine.update();
    }

    public void patrol(){
       // Gdx.app.log("info", "PATROL PLACEHOLDER *******" + this);
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
                if (!goon.targetList.isEmpty()) {
                    //shoot
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
