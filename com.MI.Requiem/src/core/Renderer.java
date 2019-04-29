package core;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import runtime.Handler;

public class Renderer implements Runnable {

	private boolean running = false;
	private int ticks;
	private boolean capped = true;
	private int frames;
	protected Screen screen;
	private BufferStrategy bs;
	
	private Handler handler;
	
	public Renderer(Handler handler, Screen screen) {
		this.screen = screen;
		this.handler = handler;
	}

	@Override
	public void run() {
		long currentTime;
		long lastTime = System.nanoTime();
		long delta = 100;

		while (running) {
			currentTime = System.nanoTime();
			if (currentTime - lastTime > delta) {
				if (ticks > 0 || !capped) {
					render();
					frames++;
					if (capped) ticks--;
				}
				System.out.print("");
				lastTime = System.nanoTime();
			}

		}

	}

	public synchronized void start() {
		running = true;
		Thread t = new Thread(this);
		t.start();
	}

	Graphics g;
	public void render() {
		bs = screen.getBufferStrategy();
		if (bs == null) {
			screen.createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, screen.getWidth(), screen.getHeight());
		handler.render(g);
		
		bs.show();
		g.dispose();
	}

	public void tick() {
		ticks++;
	}
	
	protected void setCapped(boolean capped) {
		this.capped = capped;
	}
	
	protected int getFrames() {
		int hold = frames;
		frames = 0;
		return hold;
	}

}
