package interactables;

import entities.Player;
import entity.Hitbox;
import entity.Interactable;
import gfx.DrawGraphics;
import item.Inventory;
import runtime.Handler;
import world.Tile;
import world.World;

public class CrateInteractable extends Interactable{




	/**
	 * 
	 */
	private static final long serialVersionUID = 8656110588947147937L;

	public CrateInteractable() {
		super();
		hitbox = new Hitbox(0, 0, Tile.TILE_SIZE + 2, Tile.TILE_SIZE * 2, this);
	}

	transient boolean interacted = false;
	Inventory i = new Inventory(336, 300, 16, 4, this);
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
		i.update();
		if (interacted) {
			if (!((Hitbox) this.hitbox).collidingAll().contains(p)) {
				interacted = false;
			}
		}

		int t = ((World) Handler.getLoadedWorld()).getTileID(getX(), getY(), World.MAP_OVERLAY);
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
	public void load() {
		i.load(this);
	}
	
}
