package ca.etsmtl.pfe.retrocops;

import com.badlogic.gdx.Game;

import ca.etsmtl.pfe.helper.AssetLoader;
import ca.etsmtl.pfe.helper.LevelLoader;
import ca.etsmtl.pfe.screens.GameScreen;
import ca.etsmtl.pfe.screens.LevelSelectScreen;
import ca.etsmtl.pfe.screens.MainMenu;
import ca.etsmtl.pfe.ui.levelselect.LevelSelectMenu;

public class RetroCops extends Game {

	private MainMenu mainMenuScreen;
	private GameScreen gameScreen;
	private LevelSelectScreen levelSelectScreen;
	
	@Override
	public void create () {
		AssetLoader.load();
		LevelLoader.loadInfo();
		mainMenuScreen = new MainMenu(1920, 1080, this);
		gameScreen =  new GameScreen(1920, 1080, this);
		levelSelectScreen = new LevelSelectScreen(1920, 1080, this);
		changeScreenToMainMenu();
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
		mainMenuScreen.dispose();
		gameScreen.dispose();
		levelSelectScreen.dispose();
	}

	public void changeScreenToMainMenu(){
		mainMenuScreen.changeInputToMainMenuScreen();
		setScreen(mainMenuScreen);
	}

	public void changeScreenToGameScreen(){
		gameScreen.changeInputToGameScreen();
		setScreen(gameScreen);
	}

	public void changeScrenToLevelSelectScreen(){
		levelSelectScreen.changeInputToLevelSelectScreen();
		setScreen(levelSelectScreen);
	}

	public MainMenu getMainMenuScreen() {
		return mainMenuScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public LevelSelectScreen getLevelSelectScreen() {
		return levelSelectScreen;
	}
}
