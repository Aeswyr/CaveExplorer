package runtime;

import java.awt.Graphics;

import core.Driver;
import input.KeyManager;
import input.MouseManager;

public class Handler {

	Driver driver;
	MouseManager mouse;
	KeyManager keys;
	public Handler(Driver d) {
		driver = d;
		mouse = new MouseManager();
		keys = new KeyManager();
		d.setMouseListener(mouse);
		d.setKeyListener(keys);
	}
	
	public void update() {
		keys.update();
	}

	public void render(Graphics g) {

	}
	
	
	// Getters and Setters
	
	
	public MouseManager getMouse() {
		return mouse;
	}
	
	public KeyManager getKeys() {
		return keys;
	}
}
