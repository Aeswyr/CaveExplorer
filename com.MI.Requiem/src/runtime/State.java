package runtime;

import gfx.DrawGraphics;

public abstract class State {
	Handler handler;
	public State(Handler handler) {
		this.handler = handler;
	}
	
	public abstract void update();

	public abstract void render(DrawGraphics g);
}
