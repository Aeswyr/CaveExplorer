package core;

import input.KeyManager;
import input.MouseManager;
import runtime.Handler;

public class Driver  implements Runnable{

	private boolean running = false;
	private Renderer render;
	Handler handler;
	Screen screen;
	
	public Driver(){
		screen = new Screen(-1, -1);
		handler = new Handler(this);
		render = new Renderer(handler, screen);
	}

	@Override
	public void run() {
		long currentTime;
		long lastTime = System.nanoTime();
		long delta = 1000000000 / 60;
		long lastReport = System.nanoTime();
		long deltaR = 1000000000;
		
		
		while (running) {
			currentTime = System.nanoTime();
			if (currentTime - lastTime > delta) {
				update();
				render.tick();
				if (currentTime - lastReport > deltaR) {
					System.out.println("FPS: " + render.getFrames() + " Update Time (ns): " + (System.nanoTime() - currentTime));
					lastReport = System.nanoTime();
				}
				currentTime = System.nanoTime();
				lastTime = System.nanoTime();
			}
			
		}
		
	}
	
	public void start() {
		running = true;
		Thread t = new Thread(this);
		t.start();
		render.start();
	}
	
	public void update() {
		handler.update();
	}
	
	public int getWidth() {
		return screen.getWidth();
	}
	
	public int getHeight() {
		return screen.getHeight();
	}
	
	public void setCapped(boolean capped) {
		render.setCapped(capped);
	}
	
	public void setKeyListener(KeyManager k) {
		screen.addKeyListener(k);
	}
	
	public void setMouseListener(MouseManager m) {
		screen.addMouseListener(m);
		screen.addMouseMotionListener(m);
	}
}
