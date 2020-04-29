package items;

import core.Assets;
import entity.Mob;
import input.Controller;
import item.Item;
import runtime.Handler;
import world.World;

public class TileBlock extends Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1944189099146756296L;
	int id;
	int floorID;

	public TileBlock(int x, int y, int id) {
		super(x, y);
		this.id = id;
		setup();
	}

	public TileBlock(Mob holder, int id) {
		super(holder);
		this.id = id;
		setup();
	}

	@Override
	protected void setup() {

		tags = "hand";
		ID = "0:" + id;

		statPackage[ITEM_WEIGHT] = 1;

		useTime = 30;
		timer = useTime;

		switch (id) {
		case 1:
			this.sprite = Assets.dirt;
			this.invSprite = Assets.dirt_inv;
			tags += " soil";
			name = "Dirt";

			break;
		case 6:
			this.sprite = Assets.limestone;
			this.invSprite = Assets.limestone_inv;
			tags += " mineral";
			name = "Limestone";

			break;
		case 9:
			this.sprite = Assets.clay;
			this.invSprite = Assets.clay_inv;
			tags += " soil";
			name = "Clay";

			break;
		default:
			break;
		}
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
				((World) Handler.getLoadedWorld()).setTile(mouseX, mouseY, id, World.MAP_BASE);
				timer = 0;
				consumed = true;
			}
		}
	}

}
