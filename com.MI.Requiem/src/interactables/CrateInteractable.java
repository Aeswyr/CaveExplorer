package interactables;

import entities.Player;
import entity.Hitbox;
import entity.Interactable;
import gfx.DrawGraphics;
import item.Inventory;
import runtime.Handler;
import world.Tile;

public class CrateInteractable extends Interactable{




	/**
	 * 
	 */
	private static final long serialVersionUID = 8656110588947147937L;

	public CrateInteractable(Handler handler) {
		super(handler);
		hitbox = new Hitbox(0, 0, Tile.tileSize + 2, Tile.tileSize * 2, this, handler);
	}

	transient boolean interacted = false;
	Inventory i = new Inventory(336, 300, 16, 4, handler, this);
	transient Player p;
	
	@Override
	public void interact(Object interactor) {
		if (interactor instanceof Player) {
			if (interacted) {
				interacted = false;
				 p = null;
			} else {
				p = (Player) interactor;
				interacted = true;

			}
		}
	}

	@Override
	public void update() {
		super.update();
		i.update();
		if (interacted) {
			if (!this.hitbox.collidingAll().contains(p)) {
				interacted = false;
			}
		}

		int t = handler.getWorld().getOverlayID(getX(), getY());
		if (t != 24 && t != -2) {
			this.die();
		}
	}

	@Override
	public void render(DrawGraphics g) {
		if (interacted) i.render(g);
	}
	
	@Override
	public void die() {
		super.die();
		i.dropAll();
	}
	
	@Override
	public void load(Handler h) {
		super.load(h);
		i.load(h, this);
	}
	
}
