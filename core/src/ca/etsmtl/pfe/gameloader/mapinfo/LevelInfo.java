package ca.etsmtl.pfe.gameloader.mapinfo;


import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.etsmtl.pfe.gameloader.gameobjectinfo.GoonInfo;
import ca.etsmtl.pfe.gameloader.gameobjectinfo.PlayerInfo;

public class LevelInfo implements Serializable {

    private String mapFilePath;
    private ArrayList<PlayerInfo> players;
    private ArrayList<GoonInfo> goonEnnemies;

    public String getMapFilePath() {
        return mapFilePath;
    }

    public void setMapFilePath(String mapFilePath) {
        this.mapFilePath = mapFilePath;
    }

    public ArrayList<PlayerInfo> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerInfo> players) {
        this.players = players;
    }

    public ArrayList<GoonInfo> getGoonEnnemies() {
        return goonEnnemies;
    }

    public void setGoonEnnemies(ArrayList<GoonInfo> goonEnnemies) {
        this.goonEnnemies = goonEnnemies;
    }
}
