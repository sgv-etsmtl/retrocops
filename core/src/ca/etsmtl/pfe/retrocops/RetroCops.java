package ca.etsmtl.pfe.retrocops;

import com.badlogic.gdx.Game;

import ca.etsmtl.pfe.screens.MainMenu;

public class RetroCops extends Game {
	
	@Override
	public void create () {
		setScreen(new MainMenu(1920,1080,this));
	}
}
