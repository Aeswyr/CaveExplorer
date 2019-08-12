package utility;

import geometry.Square;
import gfx.DrawGraphics;
import gfx.Sprite;
import runtime.Handler;

public class LoadingScreen {

	public static Handler handler;
	
	private int loadProgress;
	private final int MAXLOAD;
	private String displayText;
	private String tip;
	Square back;
	Square bar;
	Square load;
	boolean primeNewTip = false;
	
	public LoadingScreen(int MAXLOAD) {
		this.MAXLOAD = MAXLOAD;
		displayText = "";
		tip = Data.getLoadTip();
		handler.setFPSCap(false);
		handler.setLoadingScreen(this);
		
		back = new Square(handler.getWidth(), handler.getHeight(), 0xff333333, Sprite.TYPE_GUI_BACKGROUND_SHAPE);
		bar = new Square(300, 20, 0xff000000, Sprite.TYPE_GUI_FOREGROUND_SHAPE);
		load = new Square(1, 16, 0xff990000, Sprite.TYPE_GUI_FOREGROUND_SHAPE);
	}
	

	public void render(DrawGraphics g) {
		back.render(0, 0, g);
		bar.render(40, 40, g);
		load.render(42, 42, g);
		g.write(displayText, 60, 60, 0xff009900);
		g.write(tip, 20, 120, 0xffffffff);
		if (primeNewTip) {
			primeNewTip = false;
			tip = Data.getLoadTip();
		}
	}
	
	public void close() {
		handler.closeLoadingScreen();
		handler.setFPSCap(true);
	}
	
	public void increment(int value) {
		this.loadProgress += value;
		double loadFactor = 1.0 * loadProgress / MAXLOAD;
		load.resize((int)(295 * loadFactor) + 1, 16);
		if (Math.random() < (2.0 / MAXLOAD)) primeNewTip = true;
	}
	
	public void displayText(String text) {
		this.displayText = text;
	}
	
}
