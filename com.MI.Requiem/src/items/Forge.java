package items;

import core.Assets;
import entity.Mob;
import input.Controller;
import interactables.ForgeInteractable;
import item.Item;
import runtime.Handler;
import world.Tile;
import world.World;

public class Forge extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8095652832011942557L;
	private ForgeInteractable interact;

	public Forge(int x, int y) {
		super(x, y);

	}

	public Forge(Mob holder) {
		super(holder);

	}

	@Override
	protected void setup() {
		tags = "hand";
		ID = "0:3";
		name = "Forge";
		
		statPackage[ITEM_WEIGHT] = 20;

		useTime = 30;
		timer = useTime;

		this.sprite = Assets.forge_inv;
		this.invSprite = Assets.forge_inv;

		interact = new ForgeInteractable();
		
		stackable = false;
	}



	@Override
	public void use() {
		if (!consumed && timer >= useTime) {
			int holderX = holder.getCenteredX();
			int holderY = holder.getY() - 8;

			int mouseX = Handler.getCamera().xOffsetAdj() + Controller.getAdjX() - Handler.getWidth() / 2;
			int mouseY = Handler.getCamera().yOffsetAdj() + Controller.getAdjY() - Handler.getHeight() / 2;

			int dx = mouseX - holderX;
			int dy = mouseY - holderY;

			if (dx * dx + dy * dy < 4096 && !Handler.getLoadedWorld().getTile(mouseX, mouseY, World.MAP_BASE).getCollidable()
					&& !Handler.getLoadedWorld().getTile(mouseX + Tile.TILE_SIZE, mouseY, World.MAP_BASE).getCollidable()) {
				((World) Handler.getLoadedWorld()).setTile(mouseX, mouseY, 3, World.MAP_OVERLAY);
				((World) Handler.getLoadedWorld()).setTile(mouseX + Tile.TILE_SIZE, mouseY, 4, World.MAP_OVERLAY);
				interact.setX((mouseX / Tile.TILE_SIZE) * Tile.TILE_SIZE);
				interact.setY((mouseY / Tile.TILE_SIZE) * Tile.TILE_SIZE);
				Handler.getEntityManager().addEntity(interact);
				timer = 0;
				consumed = true;
			}

		}
	}
	
	@Override
	public Item strip() {
		this.interact = null;
		return this;
	}

}
