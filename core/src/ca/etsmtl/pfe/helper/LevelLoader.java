package ca.etsmtl.pfe.helper;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;

import ca.etsmtl.pfe.gameloader.gameobjectinfo.GoonInfo;
import ca.etsmtl.pfe.gameloader.gameobjectinfo.PlayerInfo;
import ca.etsmtl.pfe.gameloader.mapinfo.GameLevelInfo;
import ca.etsmtl.pfe.gameloader.mapinfo.LevelInfo;
import ca.etsmtl.pfe.gameworld.GameWorld;

public class LevelLoader {
    private static String gameLevelsInfoPath = "levelsInfo/gameLevelsInfo.json";
    private static GameLevelInfo gameLevelInfo;
    private static Gson gson;
    private static boolean loaded = false;

    public static void loadInfo(){
        gson = new Gson();
        FileHandle file = Gdx.files.internal(gameLevelsInfoPath);
        gameLevelInfo = gson.fromJson(file.readString(), GameLevelInfo.class);
        loaded = true;
    }

    public static GameLevelInfo getGameLevelInfo() {
        return gameLevelInfo;
    }

    public static LevelInfo getLevelInfo(int indexLeve){
        if(!loaded ||
           indexLeve >= gameLevelInfo.getLevelsName().size() ||
           indexLeve >= gameLevelInfo.getPathsLevelImage().size() ||
           indexLeve >= gameLevelInfo.getPathsLevelInfo().size()){
            return null;
        }
        FileHandle file = Gdx.files.internal(gameLevelInfo.getPathsLevelInfo().get(indexLeve));
        return gson.fromJson(file.readString(), LevelInfo.class);
    }

    public static void loadLever(GameWorld gameWorld, int indexLevel){
        LevelInfo levelInfo = getLevelInfo(indexLevel);
        if(levelInfo != null){
            gameWorld.clearWorld();
            gameWorld.changeMap(levelInfo.getMapFilePath());

            for(PlayerInfo players : levelInfo.getPlayers()){
                gameWorld.addCharacterPlayer(players.getPlayerFromInfo());
            }

            for(GoonInfo ennemie : levelInfo.getGoonEnnemies()){
                gameWorld.addEnnemie(ennemie.getGoonFromInfo());
            }
        }
    }
}
