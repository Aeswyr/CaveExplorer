package runtime;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import core.Driver;
import gfx.DrawGraphics;
import gui.Button;
import gui.ClickListener;
import gui.Frame;
import gui.UIObject;

/**
 * Gamestate for the main menu of the game
 * @author Pascal
 *
 */
public class Menustate extends State {

	/**
	 * initializes the main menu and displays it
	 * @param handler
	 */
	public Menustate(Handler handler) {
		super(handler);

		Gamestate game = new Gamestate(handler);

		Frame background = new Frame(0, 0, handler.getWidth(), handler.getHeight(), 0xffff0000, 0xffff0000);
		handler.getUI().addObject(background);

		Button world = new Button("Worlds", new ClickListener() {

			@Override
			public void onClick(UIObject source) {
				handler.getUI().removeObject(source);
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
					if (i == directories.length) name = "new";
					else name = directories[i];
					worlds.add(new Button(name, new ClickListener() {

						@Override
						public void onClick(UIObject source) {
							if (loc != directories.length) {
								for (int i = 0; i < worlds.size(); i++) {
									handler.getUI().removeObject(worlds.get(i));
								}
								handler.getUI().removeObject(background);
								handler.setState(game);
								game.init(directories[loc]);
							} else {
								for (int i = 0; i < worlds.size(); i++) {
									handler.getUI().removeObject(worlds.get(i));
								}
								handler.getUI().removeObject(background);
								handler.setState(game);
								game.init("world" + loc);
							}
						}

					}, 80, 50 + 40 * i, 100, 30, 0xff00ff00, 0xff0000ff, handler));
				}

				for (int i = 0; i < worlds.size(); i++) {
					handler.getUI().addObject(worlds.get(i));
				}
				
			}

		}, 80, 80, 60, 30, 0xff00ff00, 0xff0000ff, handler);
		handler.getUI().addObject(world);
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
