package ca.etsmtl.pfe.retrocops;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import ca.etsmtl.pfe.helper.AssetLoader;
import ca.etsmtl.pfe.helper.LevelLoader;
import ca.etsmtl.pfe.screens.GameOverScreen;
import ca.etsmtl.pfe.screens.GameScreen;
import ca.etsmtl.pfe.screens.LevelSelectScreen;
import ca.etsmtl.pfe.screens.MainMenu;
import ca.etsmtl.pfe.ui.levelselect.LevelSelectMenu;

public class RetroCops extends Game {

	private MainMenu mainMenuScreen;
	private GameScreen gameScreen;
	private LevelSelectScreen levelSelectScreen;
	private GameOverScreen gameOverScreen;

	@Override
	public void create () {
		AssetLoader.load();
		LevelLoader.loadInfo();
		mainMenuScreen = new MainMenu(1920, 1080, this);
		gameScreen =  new GameScreen(1920, 1080, this);
		levelSelectScreen = new LevelSelectScreen(1920, 1080, this);
		gameOverScreen = new GameOverScreen(1920, 1080, this);
		changeScreenToMainMenu();
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
		mainMenuScreen.dispose();
		gameScreen.dispose();
		levelSelectScreen.dispose();
		gameOverScreen.dispose();
	}

	public void changeScreenToMainMenu(){
		mainMenuScreen.changeInputToMainMenuScreen();
		setScreen(mainMenuScreen);
	}

	public void changeScreenToGameOverScreen() {
		AssetLoader.gameMusic.setLooping(false);
		AssetLoader.gameMusic.stop();
		gameOverScreen.changeInputToGameOverScreen();
		setScreen(gameOverScreen);
	}

	public void changeScreenToGameScreen(){
		AssetLoader.gameMusic.play();
		AssetLoader.gameMusic.setLooping(true);
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
