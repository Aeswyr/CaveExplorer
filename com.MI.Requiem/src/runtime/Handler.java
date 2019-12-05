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

/**
 * the core of the game functions, updates and renders all things, as well as
 * holds connections between parts of the game
 * 
 * @author Pascal
 *
 */
public class Handler {

	private State activeState;

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

	/**
	 * initialies the driver and all its components
	 * @param d - the main game driver
	 */
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

		activeState = new Menustate(this);
	}

	/**
	 * initializes a new world
	 * @param name - name of the world folder
	 */
	public void init(String name) {
		world.init(name);
	}

	public void update() {
		keys.update();
		activeState.update();
		if (keys.getRBracketTyped()) {
			if (devMode)
				devMode = false;
			else
				devMode = true;
		}
		if (keys.getEscTyped()) {
			driver.close();
		}
	}

	/**
	 * draws all components of the currently active state
	 * @param g - the DrawGraphics component associated with the renderer
	 */
	public void render(DrawGraphics g) {
		if (loadingScreen != null)
			loadingScreen.render(g);
		else
			activeState.render(g);

	}

	// Getters and Setters

	/**
	 * @returns the mouse controls
	 */
	public MouseManager getMouse() {
		return mouse;
	}

	/**
	 * @returns the keyboard controls
	 */
	public KeyManager getKeys() {
		return keys;
	}

	/**
	 * @returns the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @returns the camera
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * @returns the currently loaded world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * @returns the width of the game square (not the screen width)
	 */
	public int getWidth() {
		return 960;
	}

	/**
	 * @returns the height of the game square (not the screen height)
	 */
	public int getHeight() {
		return 540;
	}

	/**
	 * @returns the game window
	 */
	public Screen getScreen() {
		return driver.getScreen();
	}

	/**
	 * @returns the DrawGraphics component of the renderer
	 */
	public DrawGraphics getCanvas() {
		return driver.getCanvas();
	}

	/**
	 * @returns the LightManager
	 */
	public LightManager getLights() {
		return lightManager;
	}

	/**
	 * @returns the ParticleManager
	 */
	public ParticleManager getParticles() {
		return particles;
	}

	/**
	 * @returns the GUI manager
	 */
	public InterfaceManager getUI() {
		return uiManager;
	}

	/**
	 * sets the capped condition of the renderer 
	 * @param capped - true to cap the fps at 60, false to uncap fps
	 */
	public void setFPSCap(boolean capped) {
		driver.setCapped(capped);
	}

	/**
	 * sets the currently active loading screen
	 * @param l - the new loading screen
	 */
	public void setLoadingScreen(LoadingScreen l) {
		this.loadingScreen = l;
	}

	/**
	 * closes a currently active loading screen
	 */
	public void closeLoadingScreen() {
		loadingScreen = null;
	}

	/**
	 * sets the currently active state
	 * @param state - the new state
	 */
	public void setState(State state) {
		this.activeState = state;
	}
	
	/**
	 * sets the main player
	 * @param player - player to set as
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
}
