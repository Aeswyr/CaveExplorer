package items;

import core.Assets;
import entity.Mob;
import interactables.CrateInteractable;
import item.Item;
import runtime.Handler;
import world.Tile;

public class Crate extends Item{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2714434568710840602L;
	private CrateInteractable interact;

	public Crate(int x, int y, Handler handler) {
		super(x, y, handler);
	}

	public Crate(Handler handler, Mob holder) {
		super(handler, holder);
	}

	@Override
	protected void setup() {
		tags = "hand";
		ID = "0:24";
		name = "Crate";

		statPackage[ITEM_WEIGHT] = 20;
		
		useTime = 30;
		timer = useTime;

		this.sprite = Assets.crate;
		this.invSprite = Assets.crate_inv;

		interact = new CrateInteractable(handler);

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
				handler.getWorld().setOverlay(mouseX, mouseY, 24);
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
