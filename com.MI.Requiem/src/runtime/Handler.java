package runtime;

import core.Driver;
import core.Screen;
import entity.Player;
import gfx.DrawGraphics;
import input.KeyManager;
import input.MouseManager;
import world.TileSet;
import world.World;

public class Handler {

	public boolean devMode = false;

	private Driver driver;
	private MouseManager mouse;
	private KeyManager keys;
	private World world;

	private Camera camera;
	private Player player;

	private LightManager lightManager;

	public Handler(Driver d) {
		driver = d;
		TileSet.handler = this;

		lightManager = new LightManager(this);

		camera = new Camera(this);
		player = new Player(this);
		camera.centerOnEntity(player);

		mouse = new MouseManager();
		keys = new KeyManager();
		d.setMouseListener(mouse);
		d.setKeyListener(keys);
		world = new World(this);

	}

	public void update() {
		keys.update();
		world.update();
		lightManager.update();
		camera.update();

	}

	public void render(DrawGraphics g) {
		world.render(g);
	}

	// Getters and Setters

	public MouseManager getMouse() {
		return mouse;
	}

	public KeyManager getKeys() {
		return keys;
	}

	public Player getPlayer() {
		return player;
	}

	public Camera getCamera() {
		return camera;
	}

	public World getWorld() {
		return world;
	}

	public int getWidth() {
		return driver.getWidth();
	}

	public int getHeight() {
		return driver.getHeight();
	}

	public Screen getScreen() {
		return driver.getScreen();
	}

	public DrawGraphics getCanvas() {
		return driver.getCanvas();
	}

	public LightManager getLights() {
		return lightManager;
	}
}
