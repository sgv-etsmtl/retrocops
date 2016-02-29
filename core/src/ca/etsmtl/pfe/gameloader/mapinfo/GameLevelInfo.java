package ca.etsmtl.pfe.gameloader.mapinfo;

import java.io.Serializable;
import java.util.ArrayList;

public class GameLevelInfo implements Serializable {

    private ArrayList<String> levelsName;
    private ArrayList<String> pathsLevelInfo;
    private ArrayList<String> pathsLevelImage;

    public GameLevelInfo() {
    }

    public ArrayList<String> getLevelsName() {
        return levelsName;
    }

    public void setLevelsName(ArrayList<String> levelsName) {
        this.levelsName = levelsName;
    }

    public ArrayList<String> getPathsLevelInfo() {
        return pathsLevelInfo;
    }

    public void setPathsLevelInfo(ArrayList<String> pathsLevelInfo) {
        this.pathsLevelInfo = pathsLevelInfo;
    }

    public ArrayList<String> getPathsLevelImage() {
        return pathsLevelImage;
    }

    public void setPathsLevelImage(ArrayList<String> pathsLevelImage) {
        this.pathsLevelImage = pathsLevelImage;
    }
}
