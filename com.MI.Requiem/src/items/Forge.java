package items;

import core.Assets;
import entity.Mob;
import interactables.ForgeInteractable;
import item.Item;
import runtime.Handler;
import world.Tile;

public class Forge extends Item {

	private ForgeInteractable interact;

	public Forge(int x, int y, Handler handler) {
		super(x, y, handler);

	}

	public Forge(Handler handler, Mob holder) {
		super(handler, holder);

	}

	@Override
	protected void setup() {
		tags = "hand";
		ID = "0:3";
		name = "Forge";
		

		useTime = 30;
		timer = useTime;

		this.sprite = Assets.forge_inv;
		this.invSprite = Assets.forge_inv;

		interact = new ForgeInteractable(handler);
		
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

			if (dx * dx + dy * dy < 4096 && !handler.getWorld().getTile(mouseX, mouseY).isSolid()
					&& !handler.getWorld().getTile(mouseX + Tile.tileSize, mouseY).isSolid()) {
				handler.getWorld().setOverlay(mouseX, mouseY, 3);
				handler.getWorld().setOverlay(mouseX + Tile.tileSize, mouseY, 4);
				interact.setX((mouseX / Tile.tileSize) * Tile.tileSize);
				interact.setY((mouseY / Tile.tileSize) * Tile.tileSize);
				handler.getWorld().getEntities().addEntity(interact);
				timer = 0;
				consumed = true;
			}

		}
	}

}
