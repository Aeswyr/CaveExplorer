package runtime;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import core.Assets;
import core.Engine;
import gfx.DrawGraphics;
import gfx.SpriteData;
import gui.Button;
import gui.ClickListener;
import gui.Frame;
import gui.UIObject;

/**
 * Gamestate for the main menu of the game
 * 
 * @author Pascal
 *
 */
public class Menustate extends Scene {

	Gamestate game = new Gamestate();

	
	
	/**
	 * initializes the main menu and displays it
	 * 
	 * @param handler
	 */
	public Menustate() {
		init("");
	}

	public void init(String data) {
		Frame background = new Frame(0, 0, Handler.getWidth(), Handler.getHeight(), Assets.GUI_MainSplash);
		background.display();

		Assets.MUSIC_IntoDarkness.loop();
		
		Button world = new Button("Worlds", new ClickListener() {

			@Override
			public void onClick(UIObject source) {
				source.close();
				ArrayList<Button> worlds = new ArrayList<Button>();

				File file = new File(Engine.ROOT_DIRECTORY + "saves/");
				String[] directories = file.list(new FilenameFilter() {
					@Override
					public boolean accept(File current, String name) {
						return new File(current, name).isDirectory();
					}
				});
				for (int i = 0; i <= directories.length; i++) {
					int loc = i;
					String name = null;
					if (i == directories.length)
						name = "new";
					else
						name = directories[i];
					worlds.add(new Button(name, new ClickListener() {

						@Override
						public void onClick(UIObject source) {
							if (loc != directories.length) {
								Handler.getUI().flushObjects();
								game.init(directories[loc]);
								Handler.setScene(game);
							} else {
								Handler.getUI().flushObjects();
								game.init("world" + loc);
								Handler.setScene(game);
							}
							Assets.MUSIC_IntoDarkness.stopLoop();
						}

					}, 80 + 120 * (i / 6), 270 + 40 * (i % 6), 100, 30, Assets.ns_grey, Assets.ns_grey_dep, SpriteData.TYPE_GUI_COMPONENT)); //TODO 1
				}

				for (int i = 0; i < worlds.size(); i++) {
					worlds.get(i).display();
				}

			}

		}, 80, 270, 60, 30, Assets.ns_grey, Assets.ns_grey_dep, SpriteData.TYPE_GUI_COMPONENT); // TODO 1
		world.display();
	}

	/**
	 * updates the main menu and its components
	 */
	@Override
	public void update() {

	}

	/**
	 * draws the main menu and its components
	 */
	@Override
	public void render(DrawGraphics g) {

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
