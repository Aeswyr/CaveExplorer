package items;

import core.Assets;
import entity.Mob;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;
import world.Tile;

public class Pickaxe extends Item {

	public Pickaxe(Handler handler, Mob holder) {
		super(handler, holder);

		ID = "u2";
		tags = "mainhand";

		useMax = 512;
		use = useMax;
		useTime = 20;
		timer = useTime;

		sprite = null;
		invSprite = Assets.pickaxe_inv;
	}

	@Override
	public void renderTextBox(int x, int y, DrawGraphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void use() { //TODO raycast mining rather than selection. cast a ray out to a range in the direction of the mouse. if  it collides with a block, mine it
		if (equipped && timer >= 20) {
			int mouseX = holder.getX() + handler.getMouse().getAdjX() - handler.getWidth() / 2;
			int mouseY = holder.getY() + handler.getMouse().getAdjY() - handler.getHeight() / 2;

			if (Math.sqrt((holder.getX() - mouseX) * (holder.getX() - mouseX)
					+ (holder.getY() - mouseY) * (holder.getY() - mouseY)) < 64) {
				Tile t = handler.getWorld().getTile(mouseX / Tile.tileSize, mouseY / Tile.tileSize);
				if (t.isBreakable()) {
					System.out.println(mouseX / Tile.tileSize + ", " + mouseY / Tile.tileSize);
					handler.getWorld().setTile(mouseX / Tile.tileSize, mouseY / Tile.tileSize,
							handler.getWorld().getTileID(mouseX / Tile.tileSize, mouseY / Tile.tileSize) - 1);
					use--;
					timer = 0;
				}
			}
		}

	}

	@Override
	public void update() {
		if (timer < 20) timer++;
		if (use <= 0)
			this.consumed = true;

	}

}
