package runtime;

import core.Driver;
import core.Screen;
import entities.Player;
import gfx.DrawGraphics;
import gui.InterfaceManager;
import input.KeyManager;
import input.MouseManager;
import particle.ParticleManager;
import utility.LoadingScreen;
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
	private ParticleManager particles;
	
	private InterfaceManager uiManager;

	private LoadingScreen loadingScreen;
	
	public Handler(Driver d) {
		driver = d;
		TileSet.handler = this;
		LoadingScreen.handler = this;
		
		lightManager = new LightManager(this);
		particles = new ParticleManager();
		uiManager = new InterfaceManager();
		
		
		camera = new Camera(this);
		player = new Player(this);
		camera.centerOnEntity(player);

		mouse = new MouseManager();
		keys = new KeyManager();
		d.setMouseListener(mouse);
		d.setKeyListener(keys);
		world = new World(this);
	}
	
	public void init() {
		world.init();
	}

	public void update() {
		keys.update();
		world.update();
		particles.update();
		lightManager.update();
		camera.update();
		uiManager.update();
		if (keys.getRBracketTyped()) {
			if (devMode) devMode = false;
			else devMode = true;
		}
		if (keys.getEscTyped()) {
			driver.close();
		}
	}

	public void render(DrawGraphics g) {
		if (loadingScreen != null)
			loadingScreen.render(g);
		else {
			world.render(g);
			particles.render(g);
			uiManager.render(g);

			if (devMode)
				world.getEntities().renderDevMode(g);
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
		return (int) (driver.getWidth() / Driver.scale);
	}

	public int getHeight() {
		return (int) (driver.getHeight() / Driver.scale);
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
	
	public ParticleManager getParticles() {
		return particles;
	}
	
	public InterfaceManager getUI() {
		return uiManager;
	}
	
	public void setFPSCap(boolean capped) {
		driver.setCapped(capped);
	}
	
	public void setLoadingScreen(LoadingScreen l) {
		this.loadingScreen = l;
	}
	
	public void closeLoadingScreen() {
		loadingScreen = null;
	}
}
