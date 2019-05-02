package core;

import gfx.DrawGraphics;
import input.KeyManager;
import input.MouseManager;
import runtime.Handler;

public class Driver implements Runnable {

	public static double scale = 2.0;
	public static String saveDir;

	private boolean running = false;
	private Renderer render;
	volatile Handler handler;
	Screen screen;
	DrawGraphics canvas;

	public Driver() {

		screen = new Screen(-1, -1);
		canvas = new DrawGraphics(this);

	}

	@Override
	public void run() {
		long currentTime;
		long lastTime = System.nanoTime();
		long delta = 1000000000 / 60;
		long lastReport = System.nanoTime();
		long deltaR = 1000000000;

		long updateTime = 0;

		while (running) {
			currentTime = System.nanoTime();
			if (currentTime - lastTime > delta) {
				update();
				render.tick();
				updateTime += System.nanoTime() - currentTime;
				if (currentTime - lastReport > deltaR) {
					int fps = render.getFrames();
					System.out.println("FPS: " + fps + " Avg update time (ns): " + (updateTime / (fps + 1)));
					updateTime = 0;
					lastReport = System.nanoTime();
				}
				currentTime = System.nanoTime();
				lastTime = System.nanoTime();
			}

		}

	}

	public synchronized void start() {
		handler = new Handler(this);
		render = new Renderer(handler, screen, canvas);

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

	public Screen getScreen() {
		return screen;
	}

	public DrawGraphics getCanvas() {
		return canvas;
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
