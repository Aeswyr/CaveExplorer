package runtime;

import java.awt.Graphics;

import core.Driver;
import entity.Player;
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

	public Handler(Driver d) {
		driver = d;
		TileSet.handler = this;

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
		synchronized (camera) {
			camera.update();
		}
	}

	public void render(Graphics g) {
		synchronized (camera) {
			world.render(g);
		}
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
}
