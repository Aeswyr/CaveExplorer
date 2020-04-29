package items;

import core.Assets;
import entity.Mob;
import input.Controller;
import interactables.AnvilInteractable;
import item.Item;
import runtime.Handler;
import world.Tile;
import world.World;

public class Anvil extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 140997561230695818L;
	private AnvilInteractable interact;
	
	public Anvil(int x, int y) {
		super(x, y);
	}
	
	public Anvil(Mob holder) {
		super(holder);
	}
	
	@Override
	protected void setup() {
		tags = "hand";
		ID = "0:2";
		name = "Anvil";
		
		statPackage[ITEM_WEIGHT] = 20;
		
		useTime = 30;
		timer = useTime;
		
		this.sprite = Assets.anvil_inv;
		this.invSprite = Assets.anvil_inv;
		
		interact = new AnvilInteractable();
		
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

			if (dx * dx + dy * dy < 4096 && !Handler.getLoadedWorld().getTile(mouseX, mouseY, World.MAP_BASE).getCollidable()) {
				((World)Handler.getLoadedWorld()).setTile(mouseX, mouseY, 2, World.MAP_OVERLAY);
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
