package runtime;

import gfx.DrawGraphics;

public class Gamestate extends State {

	public Gamestate(Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
	}
	
	public void init(String name) {
		handler.init(name);
	}

	@Override
	public void update() {
		handler.getWorld().update();
		handler.getUI().update();
		handler.getParticles().update();
		handler.getCamera().update();
		handler.getLights().update();
	}

	@Override
	public void render(DrawGraphics g) {
		handler.getWorld().render(g);
		handler.getUI().render(g);
		handler.getParticles().render(g);
		if (handler.devMode)
			handler.getWorld().getEntities().renderDevMode(g);
	}

}
