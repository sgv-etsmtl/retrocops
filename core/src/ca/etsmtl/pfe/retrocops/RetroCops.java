package ca.etsmtl.pfe.retrocops;

import com.badlogic.gdx.Game;

import ca.etsmtl.pfe.screens.GameScreen;

public class RetroCops extends Game {
	
	@Override
	public void create () {
		setScreen(new GameScreen(1960,1080));
	}
}
