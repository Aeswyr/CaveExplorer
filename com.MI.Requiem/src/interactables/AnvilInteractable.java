package interactables;

import entity.Hitbox;
import entity.Interactable;
import gfx.DrawGraphics;
import runtime.Handler;
import world.Tile;

public class AnvilInteractable extends Interactable{

	public AnvilInteractable(Handler handler) {
		super(handler);
		hitbox = new Hitbox(0, 0, Tile.tileSize + 2, Tile.tileSize * 2, this, handler);
	}

	@Override
	public void interact(Object interactor) {
		System.out.println("*andre intensifies*");
		
	}

	@Override
	public void update() {
		hitbox.updatePos(getX(), getY());
		if (handler.getWorld().getOverlayID(getX(), getY()) != 2) this.die();
		
	}

	@Override
	public void render(DrawGraphics g) {
		hitbox.render(g);
		
	}

}
