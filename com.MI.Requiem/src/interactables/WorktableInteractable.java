package interactables;

import crafting.Tag;
import entities.Player;
import entity.Hitbox;
import entity.Interactable;
import gfx.DrawGraphics;
import runtime.Handler;
import world.Tile;

public class WorktableInteractable extends Interactable{
	
	public WorktableInteractable(Handler handler) {
		super(handler);
		hitbox = new Hitbox(0, 0, Tile.tileSize + 2, Tile.tileSize * 2, this, handler);
	}

	Player p;
	boolean interacted = false;

	@Override
	public void interact(Object interactor) {
		if (interactor instanceof Player) {
			p = (Player) interactor;
			interacted = true;
			if (p.getCraftingShown())
				p.closeCraft();
			p.setStationFlag(Tag.STATION_WORKTABLE, true);
			p.showCraft();
		}
	}

	@Override
	public void update() {
		hitbox.updatePos(getX(), getY());
		if (interacted) {
			if (!this.hitbox.collidingAll().contains(p)) {
				interacted = false;
				p.setStationFlag(Tag.STATION_WORKTABLE, false);
				p.closeCraft();
				p = null;
			}
		}

		if (handler.getWorld().getOverlayID(getX(), getY()) != 10)
			this.die();
	}

	@Override
	public void render(DrawGraphics g) {
		hitbox.render(g);

	}
}
