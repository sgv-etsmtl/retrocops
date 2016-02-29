package ca.etsmtl.pfe.retrocops;

import com.badlogic.gdx.Game;

import ca.etsmtl.pfe.helper.AssetLoader;
import ca.etsmtl.pfe.helper.LevelLoader;
import ca.etsmtl.pfe.screens.MainMenu;

public class RetroCops extends Game {
	
	@Override
	public void create () {
		AssetLoader.load();
		LevelLoader.loadInfo();
		setScreen(new MainMenu(1920, 1080, this));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
