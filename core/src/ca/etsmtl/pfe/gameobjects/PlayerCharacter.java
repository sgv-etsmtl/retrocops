package ca.etsmtl.pfe.gameobjects;

import java.util.ArrayList;

import ca.etsmtl.pfe.gameworld.GameWorld;

public class PlayerCharacter extends BaseCharacter{

    private final int PLAYER_AIM_RANGE = 6;
    public PlayerCharacter(GameWorld gameWorld) {
        super(gameWorld);
    }

    public void updateTargetList() {
        this.targetList.clear();
        ArrayList<BaseCharacter> ennemies = this.gameWorld.getEnnemies();

        //   Gdx.app.log("info", "Goon " + this + " is at position " + this.position);

        for (BaseCharacter ennemy : ennemies) {

        //    Gdx.app.log("info", "PC is at " + ennemy.getPosition());


            if (this.getPosition().dst(ennemy.getPosition().x, ennemy.getPosition().y) < PLAYER_AIM_RANGE * gameWorld.DEFAULT_TILE_SIZE
                    && this.canRaytrace(ennemy)) {
                this.targetList.add(ennemy);
            }
        }


    }

    public void attack(BaseCharacter target) {

        if(this.getItemCharacter().attack(target)) {

            gameWorld.gameLog.addMessage("You attack Goon for 1");
            super.attack(target);

        } else {
            this.setCurrentActionPoints(0);
        }

    }

    @Override
    public void useOverwatch() {
        super.useOverwatch();
        gameWorld.gameLog.addMessage("You used overwatch");
    }

    @Override
    public void updateMovementPossibilities(){
        this.movementPossibilities.clear();


        //DefaultGraphPath<Node> path = gameMap.getPath(start.x, start.y, end.x, end.y);

    }

}
