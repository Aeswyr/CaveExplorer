package core;

import java.io.File;

import java.util.Locale;

import core.Driver;
import sfx.Sound;

public class Initialize {

	private static final String GAMENAME = ".CavernSouls";

	public static void main(String[] args) {
		Assets.init();
		Sound.initSound();
		Driver game = new Driver();
		String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
		if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
			Driver.saveDir = System.getProperty("user.home") + "/Documents/Saved Games/" + GAMENAME + "/";
		} else if (OS.indexOf("win") >= 0) {
			Driver.saveDir = System.getenv("APPDATA") + "/." + GAMENAME + "/";
		} else if (OS.indexOf("nux") >= 0) {
			Driver.saveDir = System.getProperty("user.home") + "/Saved Games/" + GAMENAME + "/";
		} else {
			Driver.saveDir = System.getProperty("user.home");
		}
		Driver.saveDir = Driver.saveDir.replace('\\', '/');
		File file = new File(Driver.saveDir);
		if (file.mkdir()) {
			System.out.println("Created directory at: " + Driver.saveDir);
			new File(Driver.saveDir + "saves/world/").mkdirs();
			new File(Driver.saveDir + "saves/character/").mkdirs();
			new File(Driver.saveDir + "saves/data/").mkdirs();
		} else {
			System.out.println("Directory already exists");
		}

		game.start();
	}

}
