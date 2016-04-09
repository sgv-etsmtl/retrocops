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

/*
   CODE EMPRUNTÉ :
   Les lignes suivantes sont basées sur une classe
   provenant du site :
      http://nexsoftware.net/wp/2013/05/09/libgdx-making-a-paged-level-selection-screen/
   J'ai prit classe PagedScrollPaneTest. Cette classe contient le stage qui affiche la tables
   des différent niveau. J'ai changé la methode pour ajouter les niveau à la table pour utiliser
   les information du JSON contenant tout les niveau du jeu et ainsi afficher tout les niveau
   du JSON dans le selecteur de niveau.
*/

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
        numberOfRowByPage = numberOfLevelByRow * 3;
        levelSelectPages.setFlingTime(flingTime);
        levelSelectPages.setPageSpacing(pageSpacing);
        gameLevelInfo = LevelLoader.getGameLevelInfo();
        levelClickListener = new LevelClickListenerHandler(currentGame);
        levelLabelStyle = new Label.LabelStyle();
        levelLabelStyle.font = AssetLoader.gameFont;
        buttonLevelSelectStyle = new Button.ButtonStyle();
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
        int nbOfLevels = gameLevelInfo.getLevelsName().size();
        Table levels = new Table().pad(50);
        levels.defaults().pad(20, 40, 20, 40);

        for (int i = 0; i < nbOfLevels; ++i) {
            levels.add(getLevelButton(i, gameLevelInfo.getPathsLevelImage().get(i))).expand().fill();
            if (i != 0 && i % (numberOfLevelByRow - 1) == 0) {
                levels.row();
            }
            if ((i != 0 && i % (numberOfRowByPage - 1) == 0) || i == nbOfLevels - 1) {

                levelSelectPages.addPage(levels);
                levels = new Table().pad(50);
                levels.defaults().pad(20, 40, 20, 40);
            }
        }
    }

    private LevelSelectButton getLevelButton(int levelIndex, String levelImagePath){
        Label label = new Label(Integer.toString(levelIndex), levelLabelStyle);
        label.setFontScale(2f);
        label.setAlignment(Align.center);
        LevelSelectButton button = new LevelSelectButton(label, new Image(new Texture(levelImagePath)),
                                                         buttonLevelSelectStyle);
        button.setSize(200,200);
        button.setName(Integer.toString(levelIndex));
        button.addListener(levelClickListener);
        return button;
    }

    public void draw(float delta){
        levelSelectStage.act(delta);
        levelSelectStage.draw();
    }

}
/* FIN DU CODE EMPRUNTÉ */