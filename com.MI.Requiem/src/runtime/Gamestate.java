package runtime;

import core.Engine;
import gfx.DrawGraphics;
import input.Controller;
import item.Inventory;
import world.World;

/**
 * state which handles the game at runtime
 * 
 * @author Pascal
 *
 */
public class Gamestate extends Scene {

	World w;
	
	public Gamestate() {
		w = new World();
	}

	/**
	 * initializes the world
	 * 
	 * @param name - name of the world folder
	 */@Override
	public void init(String name) {
		 w.init(name);
	}

	/**
	 * updates the world, GUI, particles, camera, and lighting
	 * 
	 * @Override
	 */
	public void update() {	
		if (Controller.getKeyTyped(Controller.ALT)) {
			Handler.devMode = !Handler.devMode;
			System.out.println("state updated");
		}
		if (Controller.getKeyTyped(Controller.ESC))
			Engine.forceClose();
		Inventory.tickMouseInteraction();
			
	}

	/**
	 * draws the world, particles, and GUI
	 * 
	 * @param g - the DrawGraphics component associated with the renderer
	 * @Override
	 */
	public void render(DrawGraphics g) {
	}


	@Override
	public void start() {
		Handler.setLoadedWorld(w);
		
	}

	@Override
	public void stop() {
		if (((World)Handler.getLoadedWorld()).getPlayer().getCraftingShown())
			((World)Handler.getLoadedWorld()).getPlayer().closeCraft();
		Handler.getUI().flushObjects();
		Handler.getLightManager().flushLights();
		((World)Handler.getLoadedWorld()).save();
		Handler.getEntityManager().flushEntities();
		((World)Handler.getLoadedWorld()).unloadWorld();
		
		Handler.setLoadedWorld(null);
	}

}
