package interactables;

import entity.Hitbox;
import entity.Interactable;
import gfx.DrawGraphics;
import runtime.Handler;
import world.Tile;

public class ForgeInteractable extends Interactable{

	public ForgeInteractable(Handler handler) {
		super(handler);
		hitbox = new Hitbox(0, 0, Tile.tileSize * 2, this, handler);
	}

	@Override
	public void interact(Object interactor) {
		
		System.out.println("it's gettin hot in here");
		
	}

	@Override
	public void update() {
		hitbox.updatePos(getX(), getY());
		if (handler.getWorld().getOverlayID(getX(), getY()) != 3) this.die();
		
	}

	@Override
	public void render(DrawGraphics g) {
		hitbox.render(g);
		
	}

}
