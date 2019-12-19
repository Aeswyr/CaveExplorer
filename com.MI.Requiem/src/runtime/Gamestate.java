package runtime;

import gfx.DrawGraphics;

/**
 * state which handles the game at runtime
 * 
 * @author Pascal
 *
 */
public class Gamestate extends State {

	public Gamestate(Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
	}

	/**
	 * initializes the world
	 * 
	 * @param name - name of the world folder
	 */@Override
	public void init(String name) {
		handler.init(name);
	}

	/**
	 * updates the world, GUI, particles, camera, and lighting
	 * 
	 * @Override
	 */
	public void update() {
		handler.getWorld().update();
		handler.getUI().update();
		handler.getParticles().update();
		handler.getCamera().update();
		handler.getLights().update();
	}

	/**
	 * draws the world, particles, and GUI
	 * 
	 * @param g - the DrawGraphics component associated with the renderer
	 * @Override
	 */
	public void render(DrawGraphics g) {
		handler.getWorld().render(g);
		handler.getUI().render(g);
		handler.getParticles().render(g);
		if (handler.devMode)
			handler.getWorld().getEntities().renderDevMode(g);
	}

	public void stop(State newState) {
		if (handler.getPlayer().getCraftingShown())
			handler.getPlayer().closeCraft();
		handler.getUI().flushObjects();
		handler.getLights().flushLights();
		handler.getWorld().save();
		handler.getWorld().getEntities().flushEntities();
		handler.getWorld().unloadWorld();
		
		newState.init("");
		handler.setState(newState);
		
	}

}
