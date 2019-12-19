package interactables;

import crafting.Tag;
import entities.Player;
import entity.Hitbox;
import entity.Interactable;
import gfx.DrawGraphics;
import gfx.Sprite;
import gui.Frame;
import item.Inventory;
import runtime.Handler;
import world.Tile;

public class AnvilInteractable extends Interactable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -576823628239523475L;

	public AnvilInteractable(Handler handler) {
		super(handler);
		hitbox = new Hitbox(0, 0, Tile.tileSize + 2, Tile.tileSize * 2, this, handler);
	}

	transient Player p;
	transient boolean interacted = false;
	transient static Inventory i;
	static Frame frame = new Frame(192, 430, 576, 64, 0xff888888, 0xffffffff, Sprite.TYPE_GUI_BACKGROUND_SHAPE);

	@Override
	public void interact(Object interactor) {
		if (interactor instanceof Player) {
			if (interacted) {
				i.dropAll();
				interacted = false;
				i = null;
				p.setStationFlag(Tag.STATION_ANVIL, false);
				p.closeCraft();
				handler.getUI().removeObject(frame);
				p = null;
			} else {
				p = (Player) interactor;
				i = new Inventory(336, 434, 3, 3, handler, this);
				interacted = true;
				if (p.getCraftingShown())
					p.closeCraft();
				p.setStationFlag(Tag.STATION_ANVIL, true);
				p.showCraft(i);
				handler.getUI().addObject(frame);
			}
		}
	}

	@Override
	public void update() {
		super.update();
		if (interacted) {
			i.update();
			p.refreshCraft(i);
			if (!this.hitbox.collidingAll().contains(p)) {
				interacted = false;
				p.setStationFlag(Tag.STATION_ANVIL, false);
				p.closeCraft();
				p = null;
			}
		}

		int t = handler.getWorld().getOverlayID(getX(), getY());
		if (t != 2 && t != -2) {
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
		if (p != null && p.getCraftingShown())
			p.closeCraft();
	}

}
