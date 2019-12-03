package core;

import java.io.File;
import javax.swing.JFileChooser;
import core.Driver;
import sfx.Sound;

/**
 * starts the game and sets up any necessary filepaths
 * @author Pascal
 *
 */
public class Initialize {

	private static final String GAMENAME = "UntoTheAbyss";

	public static void main(String[] args) {
		Assets.init();
		Sound.initSound();
		Driver game = new Driver();
		Driver.saveDir = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "/" + GAMENAME + "/";
		Driver.saveDir = Driver.saveDir.replace('\\', '/');
		File file = new File(Driver.saveDir);
		if (file.mkdir()) {
			System.out.println("Created directory at: " + Driver.saveDir);
			new File(Driver.saveDir + "saves/").mkdirs();
		} else {
			System.out.println("Directory at " + Driver.saveDir + " already exists");
		}

		game.start();
	}

}
