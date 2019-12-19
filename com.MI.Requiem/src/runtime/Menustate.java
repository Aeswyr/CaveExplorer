package runtime;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import core.Assets;
import core.Driver;
import gfx.DrawGraphics;
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
public class Menustate extends State {

	Gamestate game = new Gamestate(handler);

	
	
	/**
	 * initializes the main menu and displays it
	 * 
	 * @param handler
	 */
	public Menustate(Handler handler) {
		super(handler);
		init("");
	}

	public void init(String data) {
		Frame background = new Frame(0, 0, handler.getWidth(), handler.getHeight(), Assets.GUI_MainSplash);
		background.display();

		Assets.MUSIC_IntoDarkness.loop();
		
		Button world = new Button("Worlds", new ClickListener() {

			@Override
			public void onClick(UIObject source) {
				source.close();
				ArrayList<Button> worlds = new ArrayList<Button>();

				File file = new File(Driver.saveDir + "saves/");
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
								handler.getUI().flushObjects();
								handler.setState(game);
								game.init(directories[loc]);
							} else {
								handler.getUI().flushObjects();
								handler.setState(game);
								game.init("world" + loc);
								Assets.MUSIC_IntoDarkness.stopLoop();
							}
						}

					}, 80 + 120 * (i / 6), 270 + 40 * (i % 6), 100, 30, 0xff00ff00, 0xff0000ff));
				}

				for (int i = 0; i < worlds.size(); i++) {
					worlds.get(i).display();
				}

			}

		}, 80, 270, 60, 30, 0xff00ff00, 0xff0000ff);
		world.display();
	}

	/**
	 * updates the main menu and its components
	 */
	@Override
	public void update() {
		handler.getLights().update();
		handler.getUI().update();
		handler.getParticles().update();
	}

	/**
	 * draws the main menu and its components
	 */
	@Override
	public void render(DrawGraphics g) {
		handler.getUI().render(g);
		handler.getParticles().render(g);
	}

}
