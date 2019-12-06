package items;

import core.Assets;
import entity.Mob;
import interactables.WorktableInteractable;
import item.Item;
import runtime.Handler;
import world.Tile;

public class Worktable extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3481808040371499458L;
	private WorktableInteractable interact;

	public Worktable(int x, int y, Handler handler) {
		super(x, y, handler);
	}

	public Worktable(Handler handler, Mob holder) {
		super(handler, holder);
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

		interact = new WorktableInteractable(handler);

		stackable = false;
	}

	@Override
	public void use() {
		if (!consumed && timer >= useTime) {
			int holderX = holder.getCenteredX();
			int holderY = holder.getY() - 8;

			int mouseX = handler.getCamera().xOffsetAdj() + handler.getMouse().getAdjX() - handler.getWidth() / 2;
			int mouseY = handler.getCamera().yOffsetAdj() + handler.getMouse().getAdjY() - handler.getHeight() / 2;

			int dx = mouseX - holderX;
			int dy = mouseY - holderY;

			if (dx * dx + dy * dy < 4096 && !handler.getWorld().getTile(mouseX, mouseY).isSolid()) {
				handler.getWorld().setOverlay(mouseX, mouseY, 10);
				interact.setX((mouseX / Tile.tileSize) * Tile.tileSize);
				interact.setY((mouseY / Tile.tileSize) * Tile.tileSize);
				handler.getWorld().getEntities().addEntity(interact);
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
