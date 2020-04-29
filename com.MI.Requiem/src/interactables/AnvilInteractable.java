package interactables;

import core.Assets;

import crafting.Tag;
import entities.Player;
import entity.Hitbox;
import entity.Interactable;
import gfx.DrawGraphics;
import gfx.SpriteData;
import gui.Frame;
import item.Inventory;
import runtime.Handler;
import world.Tile;
import world.World;

public class AnvilInteractable extends Interactable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -576823628239523475L;

	public AnvilInteractable() {
		hitbox = new Hitbox(0, 0, Tile.TILE_SIZE + 2, Tile.TILE_SIZE * 2, this);
	}

	transient Player p;
	transient boolean interacted = false;
	transient static Inventory i;
	static Frame frame = new Frame(192, 430, 576, 64, Assets.ns_grey, SpriteData.TYPE_GUI_BACKGROUND_SHAPE);

	@Override
	public void interact(Object interactor) {
		if (interactor instanceof Player) {
			if (interacted) {
				i.dropAll();
				interacted = false;
				i = null;
				p.setStationFlag(Tag.STATION_ANVIL, false);
				p.closeCraft();
				Handler.getUI().removeObject(frame);
				p = null;
			} else {
				p = (Player) interactor;
				i = new Inventory(336, 434, 3, 3, this);
				interacted = true;
				if (p.getCraftingShown())
					p.closeCraft();
				p.setStationFlag(Tag.STATION_ANVIL, true);
				p.showCraft(i);
				Handler.getUI().addObject(frame);
			}
		}
	}

	@Override
	public void update() {
		if (interacted) {
			i.update();
			p.refreshCraft(i);
			if (!((Hitbox) this.hitbox).collidingAll().contains(p)) {
				interacted = false;
				p.setStationFlag(Tag.STATION_ANVIL, false);
				p.closeCraft();
				p = null;
			}
		}

		int t = ((World)Handler.getLoadedWorld()).getTileID(getX(), getY(), World.MAP_OVERLAY);
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
