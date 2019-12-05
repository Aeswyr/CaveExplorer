package interactables;

import crafting.Tag;
import entities.Player;
import entity.Hitbox;
import entity.Interactable;
import gfx.DrawGraphics;
import runtime.Handler;
import world.Tile;

public class ForgeInteractable extends Interactable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6933996831059830447L;

	public ForgeInteractable(Handler handler) {
		super(handler);
		hitbox = new Hitbox(0, 0, Tile.tileSize * 2, this, handler);
	}

	Player p;
	boolean interacted = false;

	@Override
	public void interact(Object interactor) {
		if (interactor instanceof Player) {
			if (interacted) {
				interacted = false;
				p.setStationFlag(Tag.STATION_WORKTABLE, false);
				p.closeCraft();
				p = null;
			} else {
				p = (Player) interactor;
				interacted = true;
				if (p.getCraftingShown())
					p.closeCraft();
				p.setStationFlag(Tag.STATION_FORGE, true);
				p.showCraft();
			}
		}
	}

	@Override
	public void update() {
		super.update();
		if (interacted) {
			if (!this.hitbox.collidingAll().contains(p)) {
				interacted = false;
				p.setStationFlag(Tag.STATION_FORGE, false);
				p.closeCraft();
				p = null;
			}
		}
		int t = handler.getWorld().getOverlayID(getX(), getY());
		if (t != 3 && t != -2) {
			this.die();
		}
	}

	@Override
	public void render(DrawGraphics g) {
		//hitbox.render(g);

	}

	@Override
	public void die() {
		super.die();
		if (p != null && p.getCraftingShown())
			p.closeCraft();
	}

}
