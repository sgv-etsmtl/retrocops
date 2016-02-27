package ca.etsmtl.pfe.ui.levelselect;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ca.etsmtl.pfe.gameworld.GameWorld;
import ca.etsmtl.pfe.helper.LevelLoader;
import ca.etsmtl.pfe.retrocops.RetroCops;

public class LevelClickListenerHandler  extends ClickListener {

    RetroCops currentGame;

    public LevelClickListenerHandler() {
    }

    public LevelClickListenerHandler(RetroCops currentGame) {
        this.currentGame = currentGame;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        int levelIndex = Integer.valueOf(event.getListenerActor().getName());
        LevelLoader.loadLever(currentGame.getGameScreen().getGameWorld(),levelIndex);
        currentGame.changeScreenToGameScreen();
    }

}
