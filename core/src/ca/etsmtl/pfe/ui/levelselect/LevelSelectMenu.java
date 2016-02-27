package ca.etsmtl.pfe.ui.levelselect;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;

import ca.etsmtl.pfe.gameloader.mapinfo.GameLevelInfo;
import ca.etsmtl.pfe.helper.AssetLoader;
import ca.etsmtl.pfe.helper.LevelLoader;
import ca.etsmtl.pfe.retrocops.RetroCops;


public class LevelSelectMenu {

    private Table container;
    private PagedScrollPane levelSelectPages;
    private Stage levelSelectStage;

    private float flingTime;
    private int pageSpacing;
    private GameLevelInfo gameLevelInfo;
    private int numberOfLevelByRow;
    private int numberOfRowByPage;
    private LevelClickListenerHandler levelClickListener;
    private Label.LabelStyle levelLabelStyle;
    private Button.ButtonStyle buttonLevelSelectStyle;

    public LevelSelectMenu(float width, float heigth, RetroCops currentGame) {
        levelSelectStage = new Stage(new FillViewport(width, heigth));
        levelSelectPages = new PagedScrollPane();
        flingTime = 0.1f;
        pageSpacing = 25;
        numberOfLevelByRow = 4;
        numberOfRowByPage = numberOfLevelByRow * 2;
        levelSelectPages.setFlingTime(flingTime);
        levelSelectPages.setPageSpacing(pageSpacing);
        gameLevelInfo = LevelLoader.getGameLevelInfo();
        levelClickListener = new LevelClickListenerHandler(currentGame);
        levelLabelStyle = new Label.LabelStyle();
        levelLabelStyle.font = AssetLoader.gameFont;
        buttonLevelSelectStyle = new Button.ButtonStyle();
        buttonLevelSelectStyle.up = null;
        buttonLevelSelectStyle.down = null;
        container = new Table();
        levelSelectStage.addActor(container);
        container.setFillParent(true);
        addLevelInMenu();
        container.add(levelSelectPages).expand().fill();
    }

    public Stage getStage(){
        return levelSelectStage;
    }

    private void addLevelInMenu() {
        int numberOfMenu = gameLevelInfo.getLevelsName().size();
        Table levels = new Table().pad(50);
        levels.defaults().pad(20, 40, 20, 40);
        int maxDebug = 50;
        //TODO change condition for normal condition
        for (int i = 0; i < maxDebug; ++i) {
            if (i != 0 && i % numberOfLevelByRow == 0) {
                levels.row();
            }
            if (i != 0 && i % numberOfRowByPage == 0 || i == maxDebug - 1) {
                levelSelectPages.addPage(levels);
                levels = new Table().pad(50);
                levels.defaults().pad(20, 40, 20, 40);
            }
            levels.add(getLevelButton(i, gameLevelInfo.getPathsLevelImage().get(
                    i % gameLevelInfo.getPathsLevelImage().size()))).expand().fill();
        }
    }

    private Button getLevelButton(int levelIndex, String levelImagePath){
        Button button = new Button(buttonLevelSelectStyle);
        button.setSize(200,200);
        Label label = new Label(Integer.toString(levelIndex), levelLabelStyle);
        label.setFontScale(2f);
        label.setAlignment(Align.center);
        button.stack(new Image(new Texture(levelImagePath)), label).expand().fill();
        //for star support
        //http://nexsoftware.net/wp/2013/05/09/libgdx-making-a-paged-level-selection-screen/
        button.setName(Integer.toString(levelIndex));
        button.addListener(levelClickListener);
        return button;
    }

    public void draw(float delta){
        levelSelectStage.act(delta);
        levelSelectStage.draw();
    }

}
