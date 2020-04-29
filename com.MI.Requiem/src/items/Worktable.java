package items;

import core.Assets;
import entity.Mob;
import input.Controller;
import interactables.WorktableInteractable;
import item.Item;
import runtime.Handler;
import world.Tile;
import world.World;

public class Worktable extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3481808040371499458L;
	private WorktableInteractable interact;

	public Worktable(int x, int y) {
		super(x, y);
	}

	public Worktable(Mob holder) {
		super(holder);
	}

	@Override
	protected void setup() {
		tags = "hand";
		ID = "0:10";
		name = "Worktable";

		statPackage[ITEM_WEIGHT] = 20;

		useTime = 30;
		timer = useTime;

		this.sprite = Assets.worktable;
		this.invSprite = Assets.worktable_inv;

		interact = new WorktableInteractable();

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

			if (dx * dx + dy * dy < 4096
					&& !Handler.getLoadedWorld().getTile(mouseX, mouseY, World.MAP_BASE).getCollidable()) {
				((World) Handler.getLoadedWorld()).setTile(mouseX, mouseY, 10, World.MAP_OVERLAY);
				interact.setX((mouseX / Tile.TILE_SIZE) * Tile.TILE_SIZE);
				interact.setY((mouseY / Tile.TILE_SIZE) * Tile.TILE_SIZE);
				Handler.getEntityManager().addEntity(interact);
				timer = 0;
				consumed = true;
			}

		}
	}

	public Item strip() {
		this.interact = null;
		return this;
	}
}
