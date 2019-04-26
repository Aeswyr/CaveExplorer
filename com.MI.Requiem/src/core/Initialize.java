package core;

import sfx.Sound;

public class Initialize {

	public static void main(String[] args) {
		Assets.Init();
		Sound.InitSound();
		Driver game = new Driver();
		game.start();
	}
	
}
