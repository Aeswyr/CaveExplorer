package core;

import runtime.Handler;
import runtime.Menustate;
import utility.Event;
import world.Tile;
import world.World;

/**
 * starts the game and sets up any necessary filepaths
 * 
 * @author Pascal
 *
 */
public class Initialize {

	private static final String GAMENAME = "IntoDarkness";

	public static void main(String[] args) {
		Assets.init();
		Tile.initTile();

		
		
		Engine.start(0, 0, GAMENAME, "Into Darkness", 0);
		Engine.getGraphics().setFont(Assets.font);
		Engine.attachCloseEvent(new Event() {

			@Override
			public void event() {
				if (Handler.getLoadedWorld() != null) {
					((World) Handler.getLoadedWorld()).save();
					((World) Handler.getLoadedWorld()).unloadWorld();
				}
			}

		});

		Engine.getGraphics().setLightingEnabled(true);
		Handler.startScene(new Menustate());
	}

}
